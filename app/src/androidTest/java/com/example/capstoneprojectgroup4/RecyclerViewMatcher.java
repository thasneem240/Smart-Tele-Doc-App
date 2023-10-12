package com.example.capstoneprojectgroup4;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RecyclerViewMatcher {

    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view at position " + position);
                if (targetViewId != -1) {
                    description.appendText(" with id " + targetViewId);
                }
            }

            protected boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                if (targetViewId == -1) {
                    return false;
                }
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                if (targetView instanceof TextView) {
                    TextView textView = (TextView) targetView;
                    String expectedText = "Expected Text"; // Replace with the text you expect.
                    return expectedText.equals(textView.getText().toString());
                }
                return false;
            }

        };
    }

  
}

