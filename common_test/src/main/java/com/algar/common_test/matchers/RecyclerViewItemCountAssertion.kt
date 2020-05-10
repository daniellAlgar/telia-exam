package com.algar.common_test.matchers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Matcher
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

/**
 * Allows to check the number of items in a recycler view in instrumentation tests.
 */
class RecyclerViewItemCountAssertion private constructor(private val matcher: Matcher<Int>) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, matcher)
    }

    companion object {

        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return withItemCount(`is`(expectedCount))
        }

        fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(matcher)
        }
    }
}