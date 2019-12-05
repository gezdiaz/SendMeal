package frsf.isi.dam.gtm.sendmeal;


import android.os.SystemClock;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class TestBuscarPlato {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void buscarPlatoTest() {
        // Open the overflow menu OR open the options menu,
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //Cambia a la actividad de lista de platos
        onView(withText(R.string.itemListOption)).check(matches(isDisplayed())).perform(click());
        SystemClock.sleep(4000);
        //En la actividad de lista de platos

        //Cambia a la actividad de lista de platos
        onView(withId(R.id.menu_search)).check(matches(isDisplayed())).perform(click());

        //Cambia al dialogo de buscar platos
        onView(withId(R.id.dishNameSearchEdit)).perform(click(),typeText("PlatoTest Espresso"));
        onView(withText(R.string.search)).check(matches(isDisplayed())).perform(click());

        SystemClock.sleep(2500);

        //Cambia a la actividad de lista de platos

         onView(allOf(withId(R.id.dishNameLbl),withText("PlatoTest Espresso"))).check(matches(isDisplayed()));
    }

}
