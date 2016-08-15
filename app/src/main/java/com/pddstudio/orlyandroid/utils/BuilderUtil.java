package com.pddstudio.orlyandroid.utils;

import com.pddstudio.orly.book.generator.Book;
import com.pddstudio.orly.book.generator.BookGenerator;
import com.pddstudio.orly.book.generator.enums.CoverColor;
import com.pddstudio.orly.book.generator.enums.CoverImage;
import com.pddstudio.orly.book.generator.enums.GuideTextPosition;

import org.androidannotations.annotations.EBean;

/**
 * Created by pddstudio on 15/08/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class BuilderUtil {

	private String            title;
	private String            topText;
	private String            author;
	private String            guideText;
	private CoverColor        coverColor;
	private CoverImage        coverImage;
	private GuideTextPosition guideTextPosition;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTopText(String topText) {
		this.topText = topText;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setGuideText(String guideText) {
		this.guideText = guideText;
	}

	public void setCoverColor(CoverColor coverColor) {
		this.coverColor = coverColor;
	}

	public void setCoverImage(CoverImage coverImage) {
		this.coverImage = coverImage;
	}

	public void setGuideTextPosition(GuideTextPosition guideTextPosition) {
		this.guideTextPosition = guideTextPosition;
	}

	public void clean() {
		title = null;
		topText = null;
		author = null;
		guideText = null;
		coverColor = null;
		coverImage = null;
		guideTextPosition = null;
	}

	public Book build() {
		return BookGenerator.createBook()
							.withTitle(title)
							.withTopText(topText)
							.withAuthor(author)
							.withGuideText(guideText)
							.withCoverColor(coverColor)
							.withCoverImage(coverImage)
							.withGuideTextPosition(guideTextPosition)
							.generate();
	}

}
