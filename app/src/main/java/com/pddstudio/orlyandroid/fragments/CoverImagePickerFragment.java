package com.pddstudio.orlyandroid.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pddstudio.orly.book.generator.enums.CoverImage;
import com.pddstudio.orlyandroid.R;
import com.pddstudio.orlyandroid.adapters.CoverImageAdapter;
import com.pddstudio.orlyandroid.utils.BuilderUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by pddstudio on 15/08/16.
 */
@EFragment(R.layout.fragment_dialog_picker)
public class CoverImagePickerFragment extends Fragment implements CoverImageAdapter.OnClickListener {

	public static CoverImagePickerFragment create() {
		return CoverImagePickerFragment_.builder().build();
	}

	@Bean
	BuilderUtil builderUtil;

	CoverImageAdapter coverImageAdapter;
	MaterialDialog    materialDialog;

	@ViewById(R.id.text_title)
	TextView titleTextView;

	@AfterViews
	void prepareFragment() {
		coverImageAdapter = new CoverImageAdapter(getContext(), this);
		titleTextView.setText(R.string.cover_image_picker_title);
	}

	@Click(R.id.open_button)
	void openDialog() {
		materialDialog = new MaterialDialog.Builder(getContext()).title(R.string.dialog_cover_picker_title)
																 .adapter(coverImageAdapter, new GridLayoutManager(getContext(), 2))
																 .show();
	}

	@Override
	public void onItemSelected(int itemPosition) {
		Log.d("CoverImagePicker", "Selected position: " + itemPosition);
		int animalId = ++itemPosition;
		CoverImage coverImage = CoverImage.getImageForCode(animalId);
		builderUtil.setCoverImage(coverImage);
		materialDialog.dismiss();
	}

}
