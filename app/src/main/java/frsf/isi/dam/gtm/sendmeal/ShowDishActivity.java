package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class ShowDishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText idDishEdit, dishNameEdit, dishDescriptionEdit, dishPriceEdit, dishCaloriesEdit;
    private Button saveDishBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Plato plato = null;
        try {
            plato = (Plato) getIntent().getExtras().get("plato");
        } catch (Exception e) {
            e.printStackTrace();
        }

        idDishEdit = findViewById(R.id.idDishEdit);
        dishNameEdit = findViewById(R.id.dishNameSearchEdit);
        dishDescriptionEdit = findViewById(R.id.dishDescriptionEdit);
        dishPriceEdit = findViewById(R.id.dishPriceEdit);
        dishCaloriesEdit = findViewById(R.id.dishCaloriesEdit);
        saveDishBtn = findViewById(R.id.saveDishBtn);

        if(plato != null){
            idDishEdit.setText(plato.getId().toString());
            dishNameEdit.setText(plato.getTitulo());
            dishDescriptionEdit.setText(plato.getDescripcion());
            dishPriceEdit.setText(plato.getPrecio().toString());
            dishCaloriesEdit.setText(plato.getCalorias().toString());
        }

        idDishEdit.setEnabled(false);
        dishNameEdit.setEnabled(false);
        dishDescriptionEdit.setEnabled(false);
        dishPriceEdit.setEnabled(false);
        dishCaloriesEdit.setEnabled(false);
        saveDishBtn.setVisibility(View.GONE);
    }
}
