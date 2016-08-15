package com.pddstudio.orly.book.generator;

import com.pddstudio.orly.book.generator.enums.CoverColor;
import com.pddstudio.orly.book.generator.enums.CoverImage;
import com.pddstudio.orly.book.generator.enums.GuideTextPosition;
import com.pddstudio.orly.book.generator.utils.BookUtils;

/**
 * Created by pddstudio on 15/08/16.
 */
public class Book {

	private String            title;
	private String            topText;
	private String            author;
	private String            guideText;
	private CoverColor        coverColor;
	private CoverImage        coverImage;
	private GuideTextPosition guideTextPosition;

	protected Book() {

	}

	public String getTitle() {
		return title;
	}

	protected void setTitle(String title) {
		this.title = title;
	}

	public String getTopText() {
		return topText;
	}

	protected void setTopText(String topText) {
		this.topText = topText;
	}

	public String getAuthor() {
		return author;
	}

	protected void setAuthor(String author) {
		this.author = author;
	}

	public String getGuideText() {
		return guideText;
	}

	protected void setGuideText(String guideText) {
		this.guideText = guideText;
	}

	public CoverColor getCoverColor() {
		return coverColor;
	}

	protected void setCoverColor(CoverColor coverColor) {
		this.coverColor = coverColor;
	}

	public CoverImage getCoverImage() {
		return coverImage;
	}

	protected void setCoverImage(CoverImage coverImage) {
		this.coverImage = coverImage;
	}

	public GuideTextPosition getGuideTextPosition() {
		return guideTextPosition;
	}

	protected void setGuideTextPosition(GuideTextPosition guideTextPosition) {
		this.guideTextPosition = guideTextPosition;
	}

	public String getGeneratedUrl() {
		invalidate();
		return BookUtils.buildCoverUrl(this);
	}

	protected void invalidate() {
		title = validateString(title);
		topText = validateString(topText);
		author = validateString(author);
		if (guideText == null) {
			guideText = "";
		}
		if (coverColor == null) {
			coverColor = CoverColor.DEFAULT;
		}
		if (coverImage == null) {
			coverImage = CoverImage.DEFAULT;
		}
		if (guideTextPosition == null) {
			guideTextPosition = GuideTextPosition.DEFAULT;
		}
	}

	private String validateString(String s) {
		if (BookUtils.isNullOrEmpty(s)) {
			s = "???";
		}
		return s;
	}

}
