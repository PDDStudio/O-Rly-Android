package com.pddstudio.orlyandroid.fragments;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.pddstudio.orlyandroid.R;
import com.pddstudio.orlyandroid.enums.Type;
import com.pddstudio.orlyandroid.utils.BuilderUtil;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

/**
 * Created by pddstudio on 15/08/16.
 */
@EFragment(R.layout.fragment_single_text)
public class SingleTextFragment extends Fragment {

	public static SingleTextFragment create(Context context, @StringRes int titleText, Type type) {
		return SingleTextFragment_.builder().textTitle(context.getString(titleText)).type(type).build();
	}

	@InstanceState
	@FragmentArg
	String textTitle;

	@InstanceState
	@FragmentArg
	Type type;

	@Bean
	BuilderUtil builderUtil;

	@ViewById(R.id.text_title)
	TextView titleTextView;

	@ViewById(R.id.text_content)
	EditText contentEditText;

	@AfterViews
	void prepareFragment() {
		titleTextView.setText(textTitle);
	}

	@AfterTextChange(R.id.text_content)
	void afterTextChanged() {
		String text = contentEditText.getText().toString();
		Log.d("SingleTextFragment", "Text Changed: " + text);
		updateTextForType(text);
	}

	private void updateTextForType(String text) {
		switch (type) {
			case TITLE:
				builderUtil.setTitle(text);
				break;
			case TOP_TITLE:
				builderUtil.setTopText(text);
				break;
			case AUTHOR:
				builderUtil.setAuthor(text);
				break;
			case GUIDE:
				builderUtil.setGuideText(text);
				break;
		}
	}

}
