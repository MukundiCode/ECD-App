package com.example.ecd_app


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
/**
 * @author Suvanth Ramruthen
 * Testing the articles activity UI
 */
class ArticleTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun articleTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnGuest), withText("Continue as guest"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.flexbox.FlexboxLayout")),
                        1
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.cardArticle),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.flexbox.FlexboxLayout")),
                        1
                    ),
                    1
                )
            )
        )
        cardView.perform(scrollTo(), click())

        val recyclerView = onView(
            allOf(
                withId(R.id.rvArticle),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tvArticleTitle), withText("Road To Health"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Road To Health")))

        val textView2 = onView(
            allOf(
                withId(R.id.tvArticleDescription), withText("Road to health government book"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Road to health government book")))

        val textView3 = onView(
            allOf(
                withId(R.id.tvArticleTitle), withText("RTHB Guide"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("RTHB Guide")))

        val textView4 = onView(
            allOf(
                withId(R.id.tvArticleDescription), withText("Guide for using the RTHB Booklet"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Guide for using the RTHB Booklet")))

        val textView5 = onView(
            allOf(
                withId(R.id.tvArticleTitle), withText("Side-By-Side Breastfeeding"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Side-By-Side Breastfeeding")))

        val textView6 = onView(
            allOf(
                withId(R.id.tvArticleDescription),
                withText("Outlines what you should know about breastfeeding"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Outlines what you should know about breastfeeding")))

        val textView7 = onView(
            allOf(
                withId(R.id.tvArticleTitle), withText("1000 Days Poster"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("1000 Days Poster")))

        val textView8 = onView(
            allOf(
                withId(R.id.tvArticleDescription), withText("1000 Day Poster graphic"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("1000 Day Poster graphic")))

        val textView9 = onView(
            allOf(
                withId(R.id.tvArticleTitle), withText("Importance Of Play"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("Importance Of Play")))

        val textView10 = onView(
            allOf(
                withId(R.id.tvArticleDescription),
                withText("Article describing the process of learning through play"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("Article describing the process of learning through play")))

        val textView11 = onView(
            allOf(
                withId(R.id.tvArticleDescription),
                withText("Article describing the process of learning through play"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView11.check(matches(withText("Article describing the process of learning through play")))


        val textView12 = onView(
            allOf(
                withText("Road To Health"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView12.check(matches(withText("Road To Health")))

        val relativeLayout = onView(
            allOf(
                withId(R.id.pdfView),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        relativeLayout.check(matches(isDisplayed()))

        val relativeLayout2 = onView(
            allOf(
                withId(R.id.pdfView),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        relativeLayout2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
