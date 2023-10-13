package com.example.capstoneprojectgroup4;

import android.view.View;
import android.widget.TextView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.widget.TextView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class EllipsizedTextMatcher extends TypeSafeMatcher<TextView> {

    @Override
    public boolean matchesSafely(TextView textView) {
        return !textView.getLayout().getText().toString().endsWith("...");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("TextView with non-ellipsized text");
    }

    public static Matcher<? super TextView> isNonEllipsized() {
        return new EllipsizedTextMatcher();
    }
}
