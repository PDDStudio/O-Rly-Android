package com.pddstudio.orly.book.generator.enums;

import java8.util.J8Arrays;

/**
 * Created by pddstudio on 15/08/16.
 */
public enum CoverImage {
	IMAGE_1(1),
	IMAGE_2(2),
	IMAGE_3(3),
	IMAGE_4(4),
	IMAGE_5(5),
	IMAGE_6(6),
	IMAGE_7(7),
	IMAGE_8(8),
	IMAGE_9(9),
	IMAGE_10(10),
	IMAGE_11(11),
	IMAGE_12(12),
	IMAGE_13(13),
	IMAGE_14(14),
	IMAGE_15(15),
	IMAGE_16(16),
	IMAGE_17(17),
	IMAGE_18(18),
	IMAGE_19(19),
	IMAGE_20(20),
	IMAGE_21(21),
	IMAGE_22(22),
	IMAGE_23(23),
	IMAGE_24(24),
	IMAGE_25(25),
	IMAGE_26(26),
	IMAGE_27(27),
	IMAGE_28(28),
	IMAGE_29(29),
	IMAGE_30(30),
	IMAGE_31(31),
	IMAGE_32(32),
	IMAGE_33(33),
	IMAGE_34(34),
	IMAGE_35(35),
	IMAGE_36(36),
	IMAGE_37(37),
	IMAGE_38(38),
	IMAGE_39(39),
	IMAGE_40(40);

	private final int imageCode;

	CoverImage(int imageCode) {
		this.imageCode = imageCode;
	}

	public int getImageCode() {
		return imageCode;
	}

	public static CoverImage DEFAULT = CoverImage.IMAGE_1;

	public static CoverImage getImageForCode(int imageCode) {
		return J8Arrays.stream(CoverImage.values()).filter(coverImage -> coverImage.imageCode == imageCode).findAny().orElse(DEFAULT);
	}
}
