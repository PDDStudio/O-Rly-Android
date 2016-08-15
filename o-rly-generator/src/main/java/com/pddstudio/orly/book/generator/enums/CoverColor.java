package com.pddstudio.orly.book.generator.enums;

import java8.util.J8Arrays;

/**
 * Created by pddstudio on 15/08/16.
 */
public enum CoverColor {
	MAGENTA(0, "#55135D"),
	IRONSIDE_GREY(1, "#71706E"),
	SCARLETT(2, "#801B2A"),
	VENETIAN_RED(3, "#B80721"),
	POHUTUKAWA(4, "#65161C"),
	PURPLE_HEART(5, "#503DBD"),
	RED(6, "#E11105"),
	CERULEAN(7, "#067BB0"),
	SELECTIVE_YELLOW(8, "#F7B500"),
	NAVY(9, "#000F76"),
	DARK_MAGENTA(10, "#A8009B"),
	SALEM(11, "#008445"),
	PERSIAN_GREEN(12, "#00999D"),
	DARK_CERULEAN(13, "#014284"),
	CARDINAL(14, "#B10034"),
	LA_PALMA(15, "#378E19"),
	OLIVE(16, "#859800");

	final int    colorCode;
	final String colorHex;

	CoverColor(int colorCode, String colorHex) {
		this.colorCode = colorCode;
		this.colorHex = colorHex;
	}

	public int getColorCode() {
		return colorCode;
	}

	public String getColorHex() {
		return colorHex;
	}

	public static CoverColor DEFAULT = MAGENTA;

	public static CoverColor getForColorCode(final int code) {
		CoverColor[] coverColors = CoverColor.values();
		return J8Arrays.stream(coverColors).filter(coverColor -> coverColor.colorCode == code).findAny().orElse(DEFAULT);
	}

	public static CoverColor getForHex(String colorHex) {
		return J8Arrays.stream(CoverColor.values()).filter(coverColor -> coverColor.colorHex.equals(colorHex)).findAny().orElse(DEFAULT);
	}

}
