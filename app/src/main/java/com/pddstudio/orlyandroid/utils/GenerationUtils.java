package com.pddstudio.orlyandroid.utils;

import android.content.Context;
import android.util.Log;

import com.pddstudio.orly.book.generator.Book;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pddstudio on 15/08/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class GenerationUtils {

	private static final String TAG = GenerationUtils.class.getSimpleName();

	@RootContext
	Context context;

	private OkHttpClient okHttpClient = new OkHttpClient();

	@Background
	public void requestBook(Book book) {
		try {
			Request request = new Request.Builder().url(book.getGeneratedUrl()).build();
			Response response = okHttpClient.newCall(request).execute();
			Log.d(TAG, "Response code: " + response.code());
			Log.d(TAG, "Response body:\n" + response.body().string());
		} catch (IOException io) {
			io.printStackTrace();
		}
	}



}
