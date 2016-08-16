package com.pddstudio.orlyandroid;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.pddstudio.orly.book.generator.enums.CoverColor;
import com.pddstudio.orlyandroid.enums.Type;
import com.pddstudio.orlyandroid.enums.UrlType;
import com.pddstudio.orlyandroid.fragments.ColorPickerFragment;
import com.pddstudio.orlyandroid.fragments.CoverImagePickerFragment;
import com.pddstudio.orlyandroid.fragments.SingleTextFragment;
import com.pddstudio.orlyandroid.utils.BuilderUtil;
import com.pddstudio.orlyandroid.utils.GenerationUtils;
import com.pddstudio.orlyandroid.utils.NetworkUtils;
import com.pddstudio.orlyandroid.utils.StorageManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity implements OnFinishAction, OnCancelAction, ColorChooserDialog.ColorCallback, ColorPickerFragment.Callback {

	@Bean
	GenerationUtils generationUtils;

	@Bean
	BuilderUtil builderUtil;

	@Bean
	StorageManager storageManager;

	@Bean
	NetworkUtils networkUtils;

	@ViewById(R.id.toolbar)
	Toolbar toolbar;

	@ViewById(R.id.steppers_view)
	SteppersView steppersView;

	SteppersView.Config steppersViewConfig;
	MaterialDialog      loadingDialog;

	@AfterInject
	void prepareApplication() {
		storageManager.cleanCacheDir();
	}

	@AfterViews
	void prepareLayout() {
		setSupportActionBar(toolbar);
		steppersViewConfig = new SteppersView.Config();
		steppersViewConfig.setOnFinishAction(this);
		steppersViewConfig.setOnCancelAction(this);
		steppersViewConfig.setFragmentManager(getSupportFragmentManager());
		steppersView.setConfig(steppersViewConfig);
		steppersView.setItems(prepareSteps());
		steppersView.build();
	}

	private List<SteppersItem> prepareSteps() {
		List<SteppersItem> items = new ArrayList<>();
		//Title item
		SteppersItem titleItem = new SteppersItem();
		titleItem.setLabel(getString(R.string.step_title_title));
		titleItem.setSubLabel(getString(R.string.step_title_summary));
		titleItem.setFragment(SingleTextFragment.create(this, R.string.fragment_title_text, Type.TITLE));
		items.add(titleItem);
		//SubTitle item
		SteppersItem subTitleItem = new SteppersItem();
		subTitleItem.setLabel(getString(R.string.step_guide_text_title));
		subTitleItem.setSubLabel(getString(R.string.step_guide_text_summary));
		subTitleItem.setFragment(SingleTextFragment.create(this, R.string.fragment_sub_title_text, Type.GUIDE));
		items.add(subTitleItem);
		//Top Title item
		SteppersItem topTitleItem = new SteppersItem();
		topTitleItem.setLabel(getString(R.string.step_top_title_title));
		topTitleItem.setSubLabel(getString(R.string.step_top_title_summary));
		topTitleItem.setFragment(SingleTextFragment.create(this, R.string.fragment_top_text, Type.TOP_TITLE));
		items.add(topTitleItem);
		//Author item
		SteppersItem authorItem = new SteppersItem();
		authorItem.setLabel(getString(R.string.step_author_title));
		authorItem.setSubLabel(getString(R.string.step_author_summary));
		authorItem.setFragment(SingleTextFragment.create(this, R.string.fragment_author_text, Type.AUTHOR));
		items.add(authorItem);
		//Color item
		SteppersItem colorItem = new SteppersItem();
		colorItem.setLabel(getString(R.string.step_color_title));
		colorItem.setSubLabel(getString(R.string.step_color_summary));
		colorItem.setFragment(ColorPickerFragment.create());
		items.add(colorItem);
		//Image item
		SteppersItem imageItem = new SteppersItem();
		imageItem.setLabel(getString(R.string.step_image_title));
		imageItem.setSubLabel(getString(R.string.step_image_summary));
		imageItem.setFragment(CoverImagePickerFragment.create());
		items.add(imageItem);

		return items;
	}

	private void restartActivity() {
		MainActivity_.intent(this).start();
		this.finish();
	}

	private void showNotConnectedDialog() {
		new MaterialDialog.Builder(this).title(R.string.dialog_no_connection_title)
										.content(R.string.dialog_no_connection_content)
										.positiveText(android.R.string.ok)
										.show();
	}

	@Override
	public void onFinish() {
		if (networkUtils.isConnectionAvailable()) {
			loadingDialog = new MaterialDialog.Builder(this).title(R.string.dialog_generate_image_title)
															.content(R.string.dialog_generate_image_content)
															.progress(true, -1)
															.cancelable(false)
															.canceledOnTouchOutside(false)
															.autoDismiss(false)
															.show();
			downloadImage();
		} else {
			showNotConnectedDialog();
		}

	}

	@UiThread
	void onImageDownloaded(File downloadedFile) {

		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}

		if (downloadedFile != null && downloadedFile.exists()) {
			ResultActivity.open(this, downloadedFile);
			builderUtil.clean();
		} else {
			//TODO: handle case if saving failed!
		}
	}

	@Background
	void downloadImage() {
		File downloadedFile = storageManager.saveBook(builderUtil.build());
		onImageDownloaded(downloadedFile);
	}

	@Override
	@OptionsItem(R.id.menu_reset_content)
	public void onCancel() {
		new MaterialDialog.Builder(this).title(R.string.dialog_cancel_title)
										.content(R.string.dialog_cancel_content)
										.positiveText(android.R.string.yes)
										.negativeText(android.R.string.no)
										.onPositive((dialog, which) -> restartActivity())
										.show();
	}

	@OptionsItem(R.id.menu_github)
	void openGithubPage() {
		networkUtils.openUrl(this, UrlType.GITHUB);
	}

	@OptionsItem(R.id.menu_about)
	void openAboutPage() {
		AboutActivity.open(this);
	}

	@OptionsItem(R.id.menu_settings)
	void openSettingsPage() {
		//TODO: Add settings
	}

	@Override
	public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
		String strColor = String.format("#%06X", 0xFFFFFF & selectedColor);
		builderUtil.setCoverColor(CoverColor.getForHex(strColor));
	}

	@Override
	public void onColorDialogClicked(int[] colors) {
		new ColorChooserDialog.Builder(this, R.string.dialog_pick_color_title).customColors(colors, null)
																			  .allowUserColorInput(false)
																			  .allowUserColorInputAlpha(false)
																			  .show();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			View view = getCurrentFocus();
			if (view instanceof EditText) {
				Rect outRect = new Rect();
				view.getGlobalVisibleRect(outRect);
				if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
					view.clearFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}
			}
		}
		return super.dispatchTouchEvent(event);
	}

}
