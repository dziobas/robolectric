package com.xtremelabs.robolectric.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.widget.CompoundButton;

public class CompoundButtonCheckedMatcher<T extends CompoundButton> extends TypeSafeMatcher<T> {
    private boolean expected;

    public CompoundButtonCheckedMatcher(boolean expected) {
        this.expected = expected;
    }

    @Override protected boolean matchesSafely(T compoundButton) {
        return compoundButton != null && expected == compoundButton.isChecked();
    }

    @Override public void describeTo(Description description) {
        description.appendText("to be [" + (expected ? "checked" : "unchecked") + "]");
    }

    @Factory
    public static <T extends CompoundButton> Matcher<T> isChecked(boolean expectedChecked) {
        return new CompoundButtonCheckedMatcher<T>(expectedChecked);
    }
}
