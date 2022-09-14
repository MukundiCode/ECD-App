package com.example.ecd_app


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomePageTEST {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun homePageTEST() {
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

        val textView = onView(
            allOf(
                withId(R.id.tvGreeting), withText("Public's Dashboard"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Public's Dashboard")))

        val textView2 = onView(
            allOf(
                withText("You and Your Baby"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("You and Your Baby")))

        val imageView = onView(
            allOf(
                withId(R.id.menu_help),
                withParent(withParent(withId(androidx.appcompat.R.id.action_bar))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withText("ECD Content"),
                withParent(withParent(withId(R.id.cardContent))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("ECD Content")))

        val textView4 = onView(
            allOf(
                withText("Public Articles"),
                withParent(withParent(withId(R.id.cardArticle))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Public Articles")))

        val textView5 = onView(
            allOf(
                withText("Facilities"),
                withParent(withParent(withId(R.id.cardMap))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Facilities")))

        val textView6 = onView(
            allOf(
                withText("Information"),
                withParent(withParent(withId(R.id.cardInfo))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Information")))

        val textView7 = onView(
            allOf(
                withText("Settings"),
                withParent(withParent(withId(R.id.cardSettings))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Settings")))

        val textView8 = onView(
            allOf(
                withText("Logout"),
                withParent(withParent(withId(R.id.cardLogout))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Logout")))

        val textView9 = onView(
            allOf(
                withText("Logout"),
                withParent(withParent(withId(R.id.cardLogout))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("Logout")))
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
