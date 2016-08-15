package com.pddstudio.orlyandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_result)
public class ResultActivity extends AppCompatActivity {

	public static void open(Context context, String url) {
		ResultActivity_.intent(context).url(url).start();
	}

	@ViewById(R.id.toolbar)
	Toolbar toolbar;

	@ViewById(R.id.image)
	ImageView imageView;

	@InstanceState
	@Extra
	String url;

	@AfterViews
	void prepareActivity() {
		toolbar.setTitle(R.string.result_activity_title);
		toolbar.setNavigationIcon(R.drawable.ic_close_24dp);
		Picasso.with(this).load(url).into(imageView);
	}

	@OptionsItem(android.R.id.home)
	void onHomeClicked() {
		onBackPressed();
	}

}
