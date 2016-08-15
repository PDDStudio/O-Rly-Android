package com.pddstudio.orly.book.generator.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pddstudio.orly.book.generator.Book;
import com.pddstudio.orly.book.generator.enums.CoverImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import java8.util.J8Arrays;

/**
 * Created by pddstudio on 15/08/16.
 */
public class BookUtils {

	private static final String BASE_URL = "https://orly-appstore.herokuapp" + "" +
			".com/generate?title=%1$s&top_text=%2$s&author=%3$s&image_code=%4$s&theme=%5$s&guide_text=%6$s&guide_text_placement=%7$s";

	public static <T> T getRandomItem(T... values) {
		return values[new Random().nextInt(values.length)];
	}

	public static boolean isNullOrEmpty(String s) {
		return (s == null || s.isEmpty() || s.replaceAll(" ", "").isEmpty());
	}

	public static String encodeString(String text) {
		Log.d("BookUtils", "Normal text: " + text);
		String encodedText = text.replaceAll("%", "%25")
								 .replaceAll(" ", "%20")
								 .replaceAll("#", "%23")
								 .replaceAll("$", "%24")
								 .replaceAll("&", "%26")
								 .replaceAll("'", "%27")
								 .replaceAll("\\+", "%2B")
								 .replaceAll(",", "%2C")
								 .replaceAll("/", "%2F")
								 .replaceAll(":", "%3A")
								 .replaceAll(";", "%3B")
								 .replaceAll("=", "%3D")
								 .replaceAll("\\?", "%3F")
								 .replaceAll("@", "%40")
								 .replaceAll("\\[", "%5B")
								 .replaceAll("\\\\", "%5C")
								 .replaceAll("\\]", "%5C")
								 .replaceAll("^", "%5D")
								 .replaceAll("_", "%5E")
								 .replaceAll("`", "%60")
								 .replaceAll("\\{", "%7B")
								 .replaceAll("\\|", "%7C")
								 .replaceAll("\\}", "%7D");

		Log.d("BookUtils", "Encoded text: " + encodedText);
		//TODO: This temporary workaround needs to be fixed.
		return encodedText.substring(3, encodedText.length() - 3);
	}

	public static String buildCoverUrl(Book book) {
		String imageCode = book.getCoverImage().getImageCode() + "";
		String coverTheme = book.getCoverColor().getColorCode() + "";
		String coverUrl = String.format(BASE_URL,
										encodeString(book.getTitle()),
										encodeString(book.getTopText()),
										encodeString(book.getAuthor()),
										imageCode,
										coverTheme,
										encodeString(book.getGuideText()),
										book.getGuideTextPosition().getPositionName());
		Log.d("BookUtils", "Generated Url: " + coverUrl);
		return coverUrl;
	}

	public static List<Bitmap> getImageResources(Context context) {
		AssetManager assetManager = context.getAssets();
		List<Bitmap> images = new LinkedList<>();
		J8Arrays.stream(CoverImage.values()).forEach(coverImage -> {
			try {
				InputStream inputStream = assetManager.open("images/" + coverImage.getImageCode() + ".png");
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				images.add(bitmap);
				inputStream.close();
			} catch (IOException io) {
				io.printStackTrace();
			}
		});
		return images;
	}

	public static Bitmap getImageResource(Context context, int imageCode) {
		AssetManager assetManager = context.getAssets();
		CoverImage coverImg = J8Arrays.stream(CoverImage.values()).filter(coverImage -> coverImage.getImageCode() == imageCode).findAny().orElse(null);
		if (coverImg == null) {
			return null;
		}
		Bitmap bitmap = null;
		try {
			InputStream inputStream = assetManager.open("images/" + coverImg.getImageCode() + ".png");
			bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return bitmap;
	}

}
