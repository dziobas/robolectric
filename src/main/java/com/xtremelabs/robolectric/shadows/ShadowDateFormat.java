package com.xtremelabs.robolectric.shadows;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.text.format.DateFormat;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

@Implements(DateFormat.class)
public class ShadowDateFormat {
	
	@Implementation
	public static final CharSequence format (CharSequence inFormat, Calendar inDate) {
		return new SimpleDateFormat(inFormat.toString()).format(inDate.getTime());
	}
	

}
