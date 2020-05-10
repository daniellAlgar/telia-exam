package com.algar.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher

class RecyclerViewChildActions {
    companion object {

        /**
         * Checks that the matcher childMatcher matches a view having a given id inside a
         * RecyclerView's item (given its position)
         */
        fun childOfViewAtPositionWithMatcher(childId: Int, position: Int, childMatcher: Matcher<View>) : Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText("Checks that the matcher childMatcher matches" +
                            " with a view having a given id inside a RecyclerView's item (given its position)")
                }

                override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
                    val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
                    val matcher = hasDescendant(allOf(withId(childId), childMatcher))
                    return viewHolder != null && matcher.matches(viewHolder.itemView)
                }
            }
        }
    }
}