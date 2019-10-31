package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
    private ProgressDialog progressDialog;
    private Plato platoEditado;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case RetrofitRepository.ALTA_PLATO: {
                    System.out.println("Se recibió el mensaje ALTA_PLATO");
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.successToast), Toast.LENGTH_SHORT);
                    toast.show();
                    CreateActivity.this.finish();
                    break;
                }
                case RetrofitRepository.UPDATE_PLATO:{
                    System.out.println("Se recibió el mensaje UPDATE_PLATO");
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.dishUpdateSuccess,Toast.LENGTH_SHORT);
                    toast.show();
                    CreateActivity.this.finish();
                    break;
                }
                case RetrofitRepository.GET_PLATO:{
                    platoEditado = (Plato) msg.obj;
                    idDishEdit.setText(platoEditado.getId().toString());
                    dishNameEdit.setText(platoEditado.getTitulo());
                    dishDescriptionEdit.setText(platoEditado.getDescripcion());
                    dishPriceEdit.setText(platoEditado.getPrecio().toString());
                    dishCaloriesEdit.setText(platoEditado.getCalorias().toString());
                    for(int i=0; i<validations.length; i++){
                        validations[i] = true;
                    }
                    if(progressDialog.isShowing()){
                        progressDialog.cancel();
                    }
                    break;
                }
                case RetrofitRepository.ERROR_UPDATE_PLATO:
                case RetrofitRepository.ERROR_ALTA_PLATO:{
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.databaseSaveDishError,Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                case RetrofitRepository.ERROR_GET_PLATO:{
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.databaseGetDishError,Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    break;
                }
            }
        }
    };

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
        dishNameEdit = findViewById(R.id.dishNameEdit);
        dishDescriptionEdit = findViewById(R.id.dishDescriptionEdit);
        dishPriceEdit = findViewById(R.id.dishPriceEdit);
        dishCaloriesEdit = findViewById(R.id.dishCaloriesEdit);
        saveDishBtn = findViewById(R.id.saveDishBtn);

        id = getIntent().getIntExtra("platoId", -1);

        if(id >= 0){
            RetrofitRepository.getInstance().getPlatoById(id, handler);
            progressDialog = ProgressDialog.show(this, getString(R.string.pleaseWait),getString(R.string.loadingDishData));
            progressDialog.setCancelable(false);
            idDishEdit.setEnabled(false);
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
                    if(id >=0){
                        platoEditado.setTitulo(dishNameEdit.getText().toString());
                        platoEditado.setDescripcion(dishDescriptionEdit.getText().toString());
                        platoEditado.setPrecio(Double.parseDouble(dishPriceEdit.getText().toString()));
                        platoEditado.setCalorias(Integer.parseInt(dishCaloriesEdit.getText().toString()));
                        RetrofitRepository.getInstance().updatePlato(platoEditado, handler);
                    }else{
                        //El id se ignora porque lo pone la base de datos.
                        final Plato plato = new Plato(
                                dishNameEdit.getText().toString(),
                                dishDescriptionEdit.getText().toString(),
                                Double.parseDouble(dishPriceEdit.getText().toString()),
                                Integer.parseInt(dishCaloriesEdit.getText().toString())
                        );
                        //Plato.platos.add(plato);
                        plato.setId(null);
                        RetrofitRepository.getInstance().savePlato(plato, handler);
                        //se recibe la respuesta en el Handler
//                        toast.show();
//                        CreateActivity.this.finish();
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
