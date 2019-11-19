package frsf.isi.dam.gtm.sendmeal;


import android.app.AlertDialog;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class CreateActivityTest {

    CreateActivity activity;

    @Before
    public void setup(){
        activity = Robolectric.buildActivity(CreateActivity.class).create().resume().get();
    }

    @Test
    public void testSaveDish(){
        EditText idDishEdit = activity.findViewById(R.id.idDishEdit);
        EditText dishNameEdit = activity.findViewById(R.id.dishNameEdit);
        EditText dishDescriptionEdit = activity.findViewById(R.id.dishDescriptionEdit);
        EditText dishPriceEdit = activity.findViewById(R.id.dishPriceEdit);
        EditText dishCaloriesEdit = activity.findViewById(R.id.dishCaloriesEdit);
        Button saveDishBtn = activity.findViewById(R.id.saveDishBtn);

        idDishEdit.setText("1");
        dishNameEdit.setText("Plato Prueba");
        dishDescriptionEdit.setText("Este plato se guardo con el test instrumentado por robolectric");
        dishPriceEdit.setText("100.5");
        dishCaloriesEdit.setText("1500");


        idDishEdit.getOnFocusChangeListener().onFocusChange(idDishEdit, false);
        dishNameEdit.getOnFocusChangeListener().onFocusChange(dishNameEdit, false);
        dishDescriptionEdit.getOnFocusChangeListener().onFocusChange(dishDescriptionEdit, false);
        dishPriceEdit.getOnFocusChangeListener().onFocusChange(dishPriceEdit, false);
        dishCaloriesEdit.getOnFocusChangeListener().onFocusChange(dishCaloriesEdit, false);

        saveDishBtn.performClick();

        AlertDialog d = activity.getNoImageDialog();
        if(d != null){
            d.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Robolectric.flushForegroundThreadScheduler();

        List<Toast> shownToasts = Shadows.shadowOf(activity.getApplication()).getShownToasts();
        assertEquals(1, shownToasts.size());

        assertEquals(activity.getString(R.string.successToast), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testNotSaveDish(){
        EditText idDishEdit = activity.findViewById(R.id.idDishEdit);
        EditText dishNameEdit = activity.findViewById(R.id.dishNameEdit);
        EditText dishDescriptionEdit = activity.findViewById(R.id.dishDescriptionEdit);
        EditText dishPriceEdit = activity.findViewById(R.id.dishPriceEdit);
        EditText dishCaloriesEdit = activity.findViewById(R.id.dishCaloriesEdit);
        Button saveDishBtn = activity.findViewById(R.id.saveDishBtn);

        idDishEdit.setText("1");
        dishNameEdit.setText("Plato Prueba sin descripción");
        dishPriceEdit.setText("100.5");
        dishCaloriesEdit.setText("1500");


        idDishEdit.getOnFocusChangeListener().onFocusChange(idDishEdit, false);
        dishNameEdit.getOnFocusChangeListener().onFocusChange(dishNameEdit, false);
        dishDescriptionEdit.getOnFocusChangeListener().onFocusChange(dishDescriptionEdit, false);
        dishPriceEdit.getOnFocusChangeListener().onFocusChange(dishPriceEdit, false);
        dishCaloriesEdit.getOnFocusChangeListener().onFocusChange(dishCaloriesEdit, false);

        saveDishBtn.performClick();

        AlertDialog d = activity.getNoImageDialog();
        if(d != null){
            d.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Robolectric.flushForegroundThreadScheduler();

        List<Toast> shownToasts = Shadows.shadowOf(activity.getApplication()).getShownToasts();
        assertEquals(1, shownToasts.size());

        assertEquals(activity.getString(R.string.errorToast), ShadowToast.getTextOfLatestToast());
    }
}
