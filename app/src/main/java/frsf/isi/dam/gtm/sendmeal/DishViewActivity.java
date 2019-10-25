package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView dishRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ViewHolder viewHolder;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_view);

        toolbar = findViewById(R.id.dishViewToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dishRecyclerView = findViewById(R.id.dishRecyclerView);
        dishRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        dishRecyclerView.setLayoutManager(layoutManager);

        adapter = new PlatoAdapter(this);
        ((PlatoAdapter)adapter).setPlatoViewDataSet(new ArrayList<Plato>());

        RetrofitRepository.getInstance().getPlatos().enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    ((PlatoAdapter) adapter).setPlatoViewDataSet(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {

            }
        });

        dishRecyclerView.setAdapter(adapter);

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

    public void editDish(int pos){
        Intent i1 = new Intent(this, CreateActivity.class);
        i1.putExtra("platoId", pos);
        startActivityForResult(i1, 1);
    }

    public void removeDish(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.removeDishQuestion);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO eliminar plato de la base de datos
                Plato.platos.remove(pos);
                ((PlatoAdapter) adapter).updatePlatos();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((PlatoAdapter)adapter).updatePlatos();
    }

    @Override
    protected void onResume() {
        System.out.println("Se ejecuta onResume");
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
