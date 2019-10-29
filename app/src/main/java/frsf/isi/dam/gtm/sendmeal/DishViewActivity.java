package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

    private ProgressDialog progressDialog;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case RetrofitRepository.GETALL_PLATOS:{
                    List<Plato> platosRecibidos = (List<Plato>) msg.obj;
                    ((PlatoAdapter) adapter).setPlatoViewDataSet(platosRecibidos);
                    adapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()){
                        progressDialog.cancel();
                    }
                    break;
                }
                case RetrofitRepository.ERROR_GETALL_PLATOS:{
                    Toast t = Toast.makeText(DishViewActivity.this, R.string.databaseGetAllDishesError, Toast.LENGTH_LONG);
                    t.show();
                    finish();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_view);

        progressDialog = ProgressDialog.show(this,getString(R.string.pleaseWait),getString(R.string.loadingDishListFromDatabase));
        progressDialog.setCancelable(false);

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

        RetrofitRepository.getInstance().getPlatos(handler);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((PlatoAdapter)adapter).updatePlatos();
    }

}
