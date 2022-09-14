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
import androidx.test.rule.GrantPermissionRule
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
 * Testing Content Page UI
 */
class ContentTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @Test
    fun contentTest() {
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
                withId(R.id.cardContent),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.flexbox.FlexboxLayout")),
                        1
                    ),
                    0
                )
            )
        )
        cardView.perform(scrollTo(), click())

        val textView = onView(
            allOf(
                withText("ECD Content List"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("ECD Content List")))

        val imageView = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_button), withContentDescription("Search"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.search_bar),
                        withParent(withId(R.id.menu_search))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.menu_sync),
                withParent(withParent(withId(androidx.appcompat.R.id.action_bar))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val viewGroup = onView(
            allOf(
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.tvTitle), withText("BreastFeeding at work"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("BreastFeeding at work")))

        val button = onView(
            allOf(
                withId(R.id.categoryAll), withText("All"),
                withParent(withParent(withId(R.id.filterScrollView))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.categoryAssignedContent), withText("Assigned Content"),
                withParent(withParent(withId(R.id.filterScrollView))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                withId(R.id.categoryBabyHealth), withText("Baby Health"),
                withParent(withParent(withId(R.id.filterScrollView))),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerview),
                childAtPosition(
                    withId(R.id.flexboxLayout),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.videoImageButton),
                childAtPosition(
                    allOf(
                        withId(R.id.videoContainer),
                        childAtPosition(
                            withClassName(`is`("com.google.android.flexbox.FlexboxLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())
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
