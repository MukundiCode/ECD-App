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
class ContactActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun contactActivityTest() {
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
                withId(R.id.cardInfo),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.flexbox.FlexboxLayout")),
                        1
                    ),
                    3
                )
            )
        )
        cardView.perform(scrollTo(), click())

        val textView = onView(
            allOf(
                withId(R.id.textView4), withText("Our Story"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Our Story")))

        val textView2 = onView(
            allOf(
                withId(R.id.textView5),
                withText("The Bhabhisana Baby Project is based in the Western Cape, South Africa, and was established in August 2015 by highly skilled therapists who recognised the need to help babies who come from underserved communities with early intervention during the critical 1 000-day period."),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("The Bhabhisana Baby Project is based in the Western Cape, South Africa, and was established in August 2015 by highly skilled therapists who recognised the need to help babies who come from underserved communities with early intervention during the critical 1 000-day period.")))



        val imageView2 = onView(
            allOf(
                withId(R.id.imageFacebook),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val imageView3 = onView(
            allOf(
                withId(R.id.imageFacebook),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val imageView4 = onView(
            allOf(
                withId(R.id.imageInstagram),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        imageView4.check(matches(isDisplayed()))

        val imageView5 = onView(
            allOf(
                withId(R.id.imageWhatsapp),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        imageView5.check(matches(isDisplayed()))

        val imageView6 = onView(
            allOf(
                withId(R.id.imageBbpPortal),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        imageView6.check(matches(isDisplayed()))

        val imageView7 = onView(
            allOf(
                withId(R.id.imageBbpPortal),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        imageView7.check(matches(isDisplayed()))
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
