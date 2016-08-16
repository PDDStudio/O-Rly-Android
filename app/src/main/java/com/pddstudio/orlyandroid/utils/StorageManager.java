package com.pddstudio.orlyandroid.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.pddstudio.orly.book.generator.Book;
import com.pddstudio.orlyandroid.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java8.util.J8Arrays;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by pddstudio on 15/08/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class StorageManager {

	public static final  int    STORAGE_REQUEST_CODE = 42;
	private static final String STORAGE_FOLDER_NAME  = "ORly-Android-Covers";

	@RootContext
	Context context;

	private OkHttpClient okHttpClient = new OkHttpClient();

	private Book tmpBook;
	private File tmpFile;
	private File destFile;

	private String generateName(Book book) {
		return book.getTitle() + "_" + book.getAuthor() + "_" + System.currentTimeMillis() + ".png";
	}

	public File saveBook(Book book) {
		try {

			Request request = new Request.Builder().url(book.getGeneratedUrl()).build();
			Response response = okHttpClient.newCall(request).execute();

			if (!response.isSuccessful()) {
				throw new IOException("Something went wrong!\n" + response);
			}

			String bookName = generateName(book);
			tmpBook = book;
			tmpFile = new File(context.getCacheDir(), bookName);
			BufferedSink bufferedSink = Okio.buffer(Okio.sink(tmpFile));
			bufferedSink.writeAll(response.body().source());
			bufferedSink.close();

			if (tmpFile.exists()) {
				return tmpFile;
			} else {
				throw new IOException("Unable to find file: " + tmpFile.getAbsolutePath());
			}

		} catch (IOException io) {
			io.printStackTrace();
			recycle();
			return null;
		}
	}

	public Book getTmpBook() {
		return tmpBook;
	}

	public File getTmpFile() {
		return tmpFile;
	}

	public File getDestFile() {
		return destFile;
	}

	public void recycle() {
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
		destFile = null;
		tmpBook = null;
		tmpFile = null;
	}

	public void shareCachedImage(Context context) {
		if (tmpFile != null && tmpFile.exists()) {
			Uri imageUri = FileProvider.getUriForFile(context, "com.pddstudio.orlyandroid.fileprovider", tmpFile);
			if (imageUri != null) {
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				shareIntent.setDataAndType(imageUri, context.getContentResolver().getType(imageUri));
				shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
				context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_image_text)));
			}
		}
	}

	public boolean canAccessStorage() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			return true;
		}
		return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
	}

	public void requestStoragePermission(Activity activity) {
		ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
	}

	private boolean checkIfDirectoryExistsAndCreateIfNot() {
		if (canAccessStorage()) {
			String directory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + STORAGE_FOLDER_NAME;
			File storageDir = new File(directory);
			if (!storageDir.exists()) {
				storageDir.mkdirs();
			}
			return storageDir.exists() && storageDir.isDirectory();
		} else {
			return false;
		}
	}

	private File getStorageDir() {
		String directory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + STORAGE_FOLDER_NAME;
		return new File(directory);
	}

	public boolean saveImageToStorage() {
		if (!checkIfDirectoryExistsAndCreateIfNot()) {
			return false;
		} else {
			destFile = new File(getStorageDir(), tmpFile.getName());
			copyFile(tmpFile, destFile);
			return destFile.exists();
		}
	}

	private void copyFile(File source, File dest) {
		try {
			InputStream inputStream = new FileInputStream(source);
			OutputStream outputStream = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int reader;
			while ((reader = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, reader);
			}
			inputStream.close();
			outputStream.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void cleanCacheDir() {
		File cache = context.getCacheDir();
		J8Arrays.stream(cache.listFiles()).filter(File::isFile).forEach(file -> {
			if (file.getName().endsWith(".png")) {
				file.delete();
			}
		});
	}

}
