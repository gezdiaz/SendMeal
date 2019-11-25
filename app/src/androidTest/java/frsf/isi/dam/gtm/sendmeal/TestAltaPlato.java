package frsf.isi.dam.gtm.sendmeal;

import android.app.AlertDialog;
import android.os.SystemClock;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class TestAltaPlato {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void crearPlatoTest(){
        // Open the overflow menu OR open the options menu,
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //Cambia a la actividad de creación de plato
        onView(withText(R.string.createItemOption)).check(matches(isDisplayed())).perform(click());

        //En la actividad dec creación
        onView(withId(R.id.idDishEdit)).perform(scrollTo(), click(),typeText("1"));
        onView(withId(R.id.dishNameEdit)).perform(scrollTo(), click(),typeText("PlatoTest Espresso"));
        onView(withId(R.id.dishDescriptionEdit)).perform(scrollTo(), click(),typeText("Descripcion del plato de prueba con espresso"));
        onView(withId(R.id.dishPriceEdit)).perform(scrollTo(), click(),typeText("5.5"));
        onView(withId(R.id.dishCaloriesEdit)).perform(scrollTo(), click(),typeText("1000"));

        onView(withId(R.id.saveDishBtn)).perform(scrollTo(), click());
        onView(withId(android.R.id.button1)).check(matches(isDisplayed()))
                .check(matches(withText(R.string.ok)))
                .perform(click());

        SystemClock.sleep(2500);

        //De vuelta en home
        // Open the overflow menu OR open the options menu,
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //Cambia a la actividad de ver platos (recycler view)
        onView(withText(R.string.itemListOption)).perform(click());

        //En DishViewActivity


    }
}
