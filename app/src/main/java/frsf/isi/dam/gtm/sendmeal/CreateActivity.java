package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText idDishEdit, dishNameEdit, dishDescriptionEdit, dishPriceEdit, dishCaloriesEdit;
    private Button saveDishBtn;
    private boolean[] validations;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        validations = new boolean[5];
        idDishEdit = findViewById(R.id.idDishEdit);
        dishNameEdit = findViewById(R.id.dishNameSearchEdit);
        dishDescriptionEdit = findViewById(R.id.dishDescriptionEdit);
        dishPriceEdit = findViewById(R.id.dishPriceEdit);
        dishCaloriesEdit = findViewById(R.id.dishCaloriesEdit);
        saveDishBtn = findViewById(R.id.saveDishBtn);

        id = getIntent().getIntExtra("platoId", -1);

        if(id >= 0){
            Call<Plato> c = RetrofitRepository.getInstance().getPlatoById(id);
            c.enqueue(new Callback<Plato>() {
                @Override
                public void onResponse(Call<Plato> call, Response<Plato> response) {
                    if(response.isSuccessful()){
                        Plato plato = response.body();
                        idDishEdit.setText(plato.getId().toString());
                        dishNameEdit.setText(plato.getTitulo());
                        dishDescriptionEdit.setText(plato.getDescripcion());
                        dishPriceEdit.setText(plato.getPrecio().toString());
                        dishCaloriesEdit.setText(plato.getCalorias().toString());
                        for(int i=0 ; i<validations.length; i++){
                            validations[i] = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Plato> call, Throwable t) {
                    System.out.println("Se produjo un error al cargar el plato: "+t.toString());
                }
            });
        }


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
                final Context context = getApplicationContext();
                final Toast toast;

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
                    toast.show();
                }else {
                    toast = Toast.makeText(context,getString(R.string.successToast),Toast.LENGTH_SHORT);
                    if(id >=0){
                        Call<Plato> c = RetrofitRepository.getInstance().getPlatoById(id);
                        c.enqueue(new Callback<Plato>() {
                            @Override
                            public void onResponse(Call<Plato> call, Response<Plato> response) {
                                if(response.isSuccessful()){
                                    Plato plato = response.body();
                                    plato.setId(Integer.parseInt(idDishEdit.getText().toString()));
                                    plato.setTitulo(dishNameEdit.getText().toString());
                                    plato.setDescripcion(dishDescriptionEdit.getText().toString());
                                    plato.setPrecio(Double.parseDouble(dishPriceEdit.getText().toString()));
                                    plato.setCalorias(Integer.parseInt(dishCaloriesEdit.getText().toString()));
                                    Call<Plato> c = RetrofitRepository.getInstance().updatePlato(plato);
                                    c.enqueue(new Callback<Plato>() {
                                        @Override
                                        public void onResponse(Call<Plato> call, Response<Plato> response) {
                                            if(response.isSuccessful()) {
                                                Intent res = new Intent();
                                                System.out.println("Se setea el resultado de la actividad editar");
                                                toast.show();
                                                CreateActivity.this.setResult(Activity.RESULT_OK, res);
                                                CreateActivity.this.finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Plato> call, Throwable thro) {
                                            Toast t = Toast.makeText(context,"Se produjo un error", Toast.LENGTH_LONG);
                                            t.show();
                                        }
                                    });
                                    //res.putExtra("platos", Plato.platos);

                                }
                            }
                            @Override
                            public void onFailure(Call<Plato> call, Throwable t) {

                            }
                        });
                    }else{
                        final Plato plato = new Plato(
                                Integer.parseInt(idDishEdit.getText().toString()),
                                dishNameEdit.getText().toString(),
                                dishDescriptionEdit.getText().toString(),
                                Double.parseDouble(dishPriceEdit.getText().toString()),
                                Integer.parseInt(dishCaloriesEdit.getText().toString())
                        );
                        //Plato.platos.add(plato);
                        RetrofitRepository.getInstance().savePlato(plato);
                        toast.show();
                        CreateActivity.this.finish();
                    }

                }
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
