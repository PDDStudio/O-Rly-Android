package com.pddstudio.orlyandroid;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.pddstudio.orlyandroid.utils.StorageManager;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.File;

@EActivity(R.layout.activity_result)
@OptionsMenu(R.menu.menu_result)
public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

	public static void open(Context context, File image) {
		ResultActivity_.intent(context).imageFile(image).start();
	}

	@ViewById(R.id.toolbar)
	Toolbar toolbar;

	@ViewById(R.id.image)
	ImageView imageView;

	@InstanceState
	@Extra
	File imageFile;

	@Bean
	StorageManager storageManager;

	@AfterViews
	void prepareActivity() {
		toolbar.setTitle(R.string.result_activity_title);
		toolbar.setNavigationIcon(R.drawable.ic_close_24dp);
		toolbar.setNavigationOnClickListener(this);
		setSupportActionBar(toolbar);
		Picasso.with(this).load(imageFile).into(imageView);
	}

	@Override
	@OptionsItem(android.R.id.home)
	public void onBackPressed() {
		//delete temporary stuff
		storageManager.recycle();
		super.onBackPressed();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == StorageManager.STORAGE_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				onSaveImage();
			} else {
				showPermissionDeniedSnackBar();
			}
		}
	}

	@OptionsItem(R.id.menu_save_image)
	void onSaveImage() {
		if (storageManager.canAccessStorage()) {
			boolean result = storageManager.saveImageToStorage();
			showSnackBarForResult(result);
		} else {
			storageManager.requestStoragePermission(this);
		}
	}

	@OptionsItem(R.id.menu_share_image)
	void onShareImage() {
		storageManager.shareCachedImage(this);
	}

	@OptionsItem(R.id.menu_delete_image)
	void onDeleteImage() {
		//TODO: Add a dialog for confirming deletion
		onBackPressed();
	}

	private void showSnackBarForResult(boolean result) {
		if (result) {
			Snackbar.make(imageView,
						  String.format(getString(R.string.snack_bar_save_success), storageManager.getDestFile().getAbsolutePath()),
						  Snackbar.LENGTH_LONG).setAction(android.R.string.ok, new View.OnClickListener() {
				@Override
				public void onClick(View view) {

				}
			}).show();
		} else {
			Snackbar.make(imageView, R.string.snack_bar_save_fail, Snackbar.LENGTH_LONG).setAction(android.R.string.ok, new View.OnClickListener() {
				@Override
				public void onClick(View view) {

				}
			}).show();
		}

	}

	private void showPermissionDeniedSnackBar() {
		Snackbar.make(imageView, R.string.snack_bar_permission_required, Snackbar.LENGTH_LONG).setAction(android.R.string.ok, new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		}).show();
	}

	@Override
	public void onClick(View view) {
		onBackPressed();
	}
}
