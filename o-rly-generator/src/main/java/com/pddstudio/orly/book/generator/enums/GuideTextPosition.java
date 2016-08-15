package com.pddstudio.orly.book.generator.enums;

/**
 * Created by pddstudio on 15/08/16.
 */
public enum GuideTextPosition {
	BOTTOM_RIGHT("bottom_right"),
	BOTTOM_LEFT("bottom_left"),
	TOP_RIGHT("top_right"),
	TOP_LEFT("top_left");

	private final String positionName;

	GuideTextPosition(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionName() {
		return positionName;
	}

	public static GuideTextPosition DEFAULT = BOTTOM_RIGHT;

}
