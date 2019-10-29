package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PedidoCreateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewPedido;
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
                    ((PlatoPedidoAdapter) adapter).setPlatoDataSet(platosRecibidos);
                    adapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()){
                        progressDialog.cancel();
                    }
                    break;
                }
                case RetrofitRepository.ERROR_GETALL_PLATOS:{
                    Toast t = Toast.makeText(PedidoCreateActivity.this, R.string.databaseGetAllDishesError, Toast.LENGTH_LONG);
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
        setContentView(R.layout.activity_pedido_create);
        toolbar = findViewById(R.id.createPedidoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = ProgressDialog.show(this,getString(R.string.pleaseWait),getString(R.string.loadingDishListFromDatabase));
        progressDialog.setCancelable(false);

        recyclerViewPedido = findViewById(R.id.reciclerViewPedido);
        recyclerViewPedido.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewPedido.setLayoutManager(layoutManager);

        adapter = new PlatoPedidoAdapter(this);
        ((PlatoPedidoAdapter)adapter).setPlatoDataSet(new ArrayList<Plato>());

        RetrofitRepository.getInstance().getPlatos(handler);

        recyclerViewPedido.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_search:
                createBusquedaDialog();
                break;
            default:
                Toast.makeText(this, "....", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO Acomodar esto porque no funciona.
    private void createBusquedaDialog(){
        LayoutInflater inflater = this.getLayoutInflater();
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View dialogView = inflater.inflate(R.layout.search_dish_options, null);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText nameDishEdit = dialogView.findViewById(R.id.dishNameSearchEdit);
                EditText minPriceEdit = dialogView.findViewById(R.id.minPriceEdit);
                EditText maxPriceEdit = dialogView.findViewById(R.id.maxPriceEdit);

                if(nameDishEdit.getText().toString().isEmpty() && minPriceEdit.getText().toString().isEmpty() && maxPriceEdit.getText().toString().isEmpty()){
                    Toast t = Toast.makeText(getApplicationContext(),R.string.errorEmptyFields, Toast.LENGTH_LONG);
                    t.show();
                }else{
                    //TODO acomodarlo bien

                    double minPrice = 0, maxPrice = Double.MAX_VALUE;

                    if(!minPriceEdit.getText().toString().isEmpty()) {
                        minPrice = Double.valueOf(minPriceEdit.getText().toString());
                    }
                    if(!maxPriceEdit.getText().toString().isEmpty()){
                        maxPrice = Double.valueOf(maxPriceEdit.getText().toString());
                    }

                    ((PlatoPedidoAdapter) adapter).getPlatosBySearchResults(nameDishEdit.getText().toString(), minPrice, maxPrice);
                }


            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

}
