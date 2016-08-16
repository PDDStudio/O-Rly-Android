package com.pddstudio.orlyandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.pddstudio.orlyandroid.enums.UrlType;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;

/**
 * Created by pddstudio on 15/08/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class NetworkUtils {

	private static final String GITHUB_URL      = "https://github.com/PDDStudio";
	private static final String GOOGLE_PLUS_URL = "https://plus.google.com/+PatrickJung42";

	@RootContext
	Context context;

	@SystemService
	ConnectivityManager connectivityManager;

	public boolean isConnectionAvailable() {
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
	}

	public void openUrl(Context context, @UrlType int urlType) {
		String url = "";
		switch (urlType) {
			case UrlType.GITHUB:
				url = GITHUB_URL;
				break;
			case UrlType.GOOGLE_PLUS:
				url = GOOGLE_PLUS_URL;
				break;
		}
		Intent webPage = new Intent(Intent.ACTION_VIEW);
		webPage.setData(Uri.parse(url));
		context.startActivity(webPage);
	}

}
