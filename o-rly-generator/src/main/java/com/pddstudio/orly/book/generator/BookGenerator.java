package com.pddstudio.orly.book.generator;

import android.util.Log;

import com.pddstudio.orly.book.generator.enums.CoverColor;
import com.pddstudio.orly.book.generator.enums.CoverImage;
import com.pddstudio.orly.book.generator.enums.GuideTextPosition;

/**
 * Created by pddstudio on 15/08/16.
 */
public final class BookGenerator {

	private static final String TAG = BookGenerator.class.getSimpleName();

	private final Book book;

	private BookGenerator() {
		this.book = new Book();
	}

	public static BookGenerator createBook() {
		return new BookGenerator();
	}

	public BookGenerator withTitle(String title) {
		if (title == null || title.isEmpty() || title.replaceAll(" ", "").isEmpty()) {
			Log.e(TAG, "A title is required to generate a book cover. Make sure this value is set!");
			title = "Title";
		}
		book.setTitle(title);
		return this;
	}

	public BookGenerator withTopText(String topText) {
		if (topText == null || topText.isEmpty() || topText.replaceAll(" ", "").isEmpty()) {
			Log.e(TAG, "A top text is required to generate a book cover. Make sure this value is set!");
			topText = "Top Text";
		}
		book.setTopText(topText);
		return this;
	}

	public BookGenerator withAuthor(String author) {
		if (author == null || author.isEmpty() || author.replaceAll(" ", "").isEmpty()) {
			Log.e(TAG, "Author is required to generate a book cover. Make sure this value is set!");
			author = "Author";
		}
		book.setAuthor(author);
		return this;
	}

	public BookGenerator withGuideText(String guideText) {
		if (guideText == null) {
			guideText = "";
		}
		book.setGuideText(guideText);
		return this;
	}

	public BookGenerator withGuideTextPosition(GuideTextPosition guideTextPosition) {
		if (guideTextPosition == null) {
			guideTextPosition = GuideTextPosition.DEFAULT;
		}
		book.setGuideTextPosition(guideTextPosition);
		return this;
	}

	public BookGenerator withCoverColor(CoverColor coverColor) {
		if (coverColor == null) {
			Log.e(TAG, "Cover color can't be null! Setting default value.");
			coverColor = CoverColor.DEFAULT;
		}
		book.setCoverColor(coverColor);
		return this;
	}

	public BookGenerator withCoverImage(CoverImage coverImage) {
		if (coverImage == null) {
			Log.e(TAG, "Cover image can't be null! Setting default value.");
			coverImage = CoverImage.DEFAULT;
		}
		book.setCoverImage(coverImage);
		return this;
	}

	public Book generate() {
		book.invalidate();
		return book;
	}

}
