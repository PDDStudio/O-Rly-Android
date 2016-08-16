package com.pddstudio.orlyandroid;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_about)
public class AboutActivity extends AppCompatActivity {

	public static void open(Context context) {
		AboutActivity_.intent(context).start();
	}

	@ViewById(R.id.toolbar)
	Toolbar toolbar;

	@AfterViews
	void prepareLayout() {
		toolbar.setTitle(R.string.about_activity_title);
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		Fragment fragment = new LibsBuilder().withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
											 .withFields(R.string.class.getFields())
											 .withExcludedLibraries("AndroidIconics", "fastadapter")
											 .supportFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, fragment).commit();
	}

	@OptionsItem(android.R.id.home)
	void onHomePressed() {
		onBackPressed();
	}

}
