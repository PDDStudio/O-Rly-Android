package com.pddstudio.orlyandroid.fragments;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.pddstudio.orly.book.generator.enums.CoverColor;
import com.pddstudio.orlyandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

/**
 * Created by pddstudio on 15/08/16.
 */
@EFragment(R.layout.fragment_dialog_picker)
public class ColorPickerFragment extends Fragment {

	public interface Callback {
		void onColorDialogClicked(int[] colors);
	}

	public static ColorPickerFragment create() {
		return ColorPickerFragment_.builder().build();
	}

	@InstanceState
	int[] colors;

	@ViewById(R.id.text_title)
	TextView titleTextView;

	private Callback callback;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			callback = (Callback) context;
		} catch (ClassCastException c) {
			throw new RuntimeException("Calling Activity must implement " + Callback.class.getCanonicalName());
		}
	}

	@AfterViews
	void prepareFragment() {
		titleTextView.setText(R.string.color_picker_title_text);
		colors = new int[CoverColor.values().length];
		for (int i = 0; i < CoverColor.values().length; i++) {
			colors[i] = Color.parseColor(CoverColor.values()[i].getColorHex());
		}
	}

	@Click(R.id.open_button)
	void openDialog() {
		callback.onColorDialogClicked(colors);
	}

}
