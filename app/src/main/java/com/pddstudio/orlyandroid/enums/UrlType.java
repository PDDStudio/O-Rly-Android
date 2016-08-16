package com.pddstudio.orlyandroid.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pddstudio on 16/08/16.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({UrlType.GITHUB, UrlType.GOOGLE_PLUS})
public @interface UrlType {
	public static final int GITHUB      = 1;
	public static final int GOOGLE_PLUS = 2;
}
