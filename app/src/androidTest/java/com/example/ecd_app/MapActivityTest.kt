package com.example.ecd_app


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
/**
 * @author Suvanth Ramruthen
 * Testing map UI
 */
class MapActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun mapActivityTest() {
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
                withId(R.id.cardMap),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.flexbox.FlexboxLayout")),
                        1
                    ),
                    2
                )
            )
        )
        cardView.perform(scrollTo(), click())

        val textView = onView(
            allOf(
                withText("Facilities"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Facilities")))

        val textView2 = onView(
            allOf(
                withId(R.id.tvMapTitle), withText("Clinics"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Clinics")))

        val textView3 = onView(
            allOf(
                withId(R.id.tvMapTitle), withText("Hospitals"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Hospitals")))

        val textView4 = onView(
            allOf(
                withId(R.id.tvMapTitle), withText("Physical Health Resources"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Physical Health Resources")))

        val textView5 = onView(
            allOf(
                withId(R.id.tvMapTitle), withText("Mums Support"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Mums Support")))

        val textView6 = onView(
            allOf(
                withId(R.id.tvMapTitle), withText("Counselling"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Counselling")))

        val textView7 = onView(
            allOf(
                withId(R.id.tvMapTitle), withText("Counselling"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Counselling")))

        val recyclerView = onView(
            allOf(
                withId(R.id.rvMaps),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val view = onView(
            allOf(
                withContentDescription("Claremont Clinic. 021 444 6426."),
                withParent(
                    allOf(
                        withContentDescription("Google Map"),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))

        val view2 = onView(
            allOf(
                withContentDescription("Alphen Clinic. 021 444 9628."),
                withParent(
                    allOf(
                        withContentDescription("Google Map"),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view2.check(matches(isDisplayed()))

        val view3 = onView(
            allOf(
                withContentDescription("Wynberg Clinic. 021 444 6613."),
                withParent(
                    allOf(
                        withContentDescription("Google Map"),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view3.check(matches(isDisplayed()))

        val view4 = onView(
            allOf(
                withContentDescription("Eastridge Clinic. 021 444 6379."),
                withParent(
                    allOf(
                        withContentDescription("Google Map"),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view4.check(matches(isDisplayed()))

        val view5 = onView(
            allOf(
                withContentDescription("Simon's Town Satellite Clinic. 021 786 1555."),
                withParent(
                    allOf(
                        withContentDescription("Google Map"),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view5.check(matches(isDisplayed()))

        val view6 = onView(
            allOf(
                withContentDescription("Simon's Town Satellite Clinic. 021 786 1555."),
                withParent(
                    allOf(
                        withContentDescription("Google Map"),
                        withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view6.check(matches(isDisplayed()))
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
