package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class ShowDishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText idDishEdit, dishNameEdit, dishDescriptionEdit, dishPriceEdit, dishCaloriesEdit;
    private Button saveDishBtn, takePictureBtn;
    private ImageView platoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        toolbar = findViewById(R.id.createToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbarTitleOfferNotificationActivity);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Plato plato = null;
        try {
            plato = (Plato) getIntent().getExtras().get("plato");
        } catch (Exception e) {
            e.printStackTrace();
        }

        idDishEdit = findViewById(R.id.idDishEdit);
        dishNameEdit = findViewById(R.id.dishNameEdit);
        dishDescriptionEdit = findViewById(R.id.dishDescriptionEdit);
        dishPriceEdit = findViewById(R.id.dishPriceEdit);
        dishCaloriesEdit = findViewById(R.id.dishCaloriesEdit);
        saveDishBtn = findViewById(R.id.saveDishBtn);
        takePictureBtn = findViewById(R.id.takePictureBtn);
        platoImage = findViewById(R.id.platoImage);

        takePictureBtn.setVisibility(View.GONE);

        if(plato != null){
            idDishEdit.setText(plato.getId().toString());
            dishNameEdit.setText(plato.getTitulo());
            dishDescriptionEdit.setText(plato.getDescripcion());
            dishPriceEdit.setText(plato.getPrecioPlato().toString());
            dishCaloriesEdit.setText(plato.getCalorias().toString());
            platoImage.setImageBitmap(plato.getImage());
        }

        idDishEdit.setEnabled(false);
        dishNameEdit.setEnabled(false);
        dishDescriptionEdit.setEnabled(false);
        dishPriceEdit.setEnabled(false);
        dishCaloriesEdit.setEnabled(false);
        saveDishBtn.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
