package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CreateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText idDishEdit, dishNameEdit, dishDescriptionEdit, dishPriceEdit, dishCaloriesEdit;
    private Button saveDishBtn;
    private boolean[] validations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_24px);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validations = new boolean[5];
        idDishEdit = findViewById(R.id.idDishEdit);
        dishNameEdit = findViewById(R.id.dishNameEdit);
        dishDescriptionEdit = findViewById(R.id.dishDescriptionEdit);
        dishPriceEdit = findViewById(R.id.dishPriceEdit);
        dishCaloriesEdit = findViewById(R.id.dishCaloriesEdit);
        saveDishBtn = findViewById(R.id.saveDishBtn);

        idDishEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                   try {
                      Integer.valueOf(idDishEdit.getText().toString());
                      validations[0] = true;
                   }
                   catch (Exception e){
                       idDishEdit.setError(getString(R.string.invalidDishId));
                       validations[0] = false;
                   }
                }
            }
        });

        dishNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    if(!dishNameEdit.getText().toString().isEmpty()){
                        validations[1] = true;
                    }
                    else{
                        dishNameEdit.setError(getString(R.string.emptyFieldCreateActivity));
                        validations[1] = false;
                    }
                }
            }
        });

        dishDescriptionEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    if(!dishDescriptionEdit.getText().toString().isEmpty()){
                        validations[2] = true;
                    }
                    else{
                        dishDescriptionEdit.setError(getString(R.string.emptyFieldCreateActivity));
                        validations[2] = false;
                    }
                }
            }
        });

        dishPriceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    try {
                        Double.valueOf(dishPriceEdit.getText().toString());
                        validations[3] = true;
                    }
                    catch (Exception e){
                        dishPriceEdit.setError(getString(R.string.invalidDishPrice));
                        validations[3] = false;
                    }
                }
            }
        });

        dishCaloriesEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    try {
                        Integer.valueOf(dishCaloriesEdit.getText().toString());
                        validations[4] = true;
                    }
                    catch (Exception e){
                        dishCaloriesEdit.setError(getString(R.string.invalidDishCalories));
                        validations[4] = false;
                    }
                }
            }
        });

        saveDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;
                Context context = getApplicationContext();
                Toast toast;

                idDishEdit.clearFocus();
                dishCaloriesEdit.clearFocus();
                dishDescriptionEdit.clearFocus();
                dishNameEdit.clearFocus();
                dishPriceEdit.clearFocus();

                saveDishBtn.requestFocus();

                for (boolean b: validations){
                    if(!b){
                        valid = false;
                    }
                }

                if(!valid){
                    toast = Toast.makeText(context,getString(R.string.errorToast),Toast.LENGTH_SHORT);
                }else {
                    toast = Toast.makeText(context,getString(R.string.successToast),Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                Toast.makeText(this, "....", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
