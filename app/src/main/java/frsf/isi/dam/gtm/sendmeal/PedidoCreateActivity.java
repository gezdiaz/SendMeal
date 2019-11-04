package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.DBClient;
import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.EstadoPedido;
import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PedidoCreateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewPedido;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ViewHolder viewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private Button createOrderBtn, sendOrderBtn;
    private boolean orderCreated;
    private String idPedidoCreado;

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
                case RetrofitRepository.ALTA_PEDIDO:{
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.successToast), Toast.LENGTH_SHORT);
                    toast.show();
                    PedidoCreateActivity.this.finish();
                    break;
                }
                case RetrofitRepository.ERROR_GETALL_PLATOS:{
                    Toast t = Toast.makeText(PedidoCreateActivity.this, R.string.databaseGetAllDishesError, Toast.LENGTH_LONG);
                    t.show();
                    finish();
                    break;
                }
                case RetrofitRepository.ERROR_ALTA_PEDIDO:{
                    Toast toast = Toast.makeText(getApplicationContext(),R.string.databaseSavePedidoError,Toast.LENGTH_SHORT);
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
        setContentView(R.layout.activity_pedido_create);
        toolbar = findViewById(R.id.createPedidoToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderCreated = false;

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

        createOrderBtn = findViewById(R.id.createOrderBtn);
        createOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ItemPedido> listItemsPedido = ((PlatoPedidoAdapter) adapter).getItems();
                Toast t;
                if(listItemsPedido.isEmpty()){
                    sendOrderBtn.setEnabled(false);
                    t = Toast.makeText(PedidoCreateActivity.this, R.string.noItemSelected, Toast.LENGTH_LONG);
                    t.show();
                    orderCreated = false;
                }else{
                    Pedido pedido = new Pedido(new Date(), EstadoPedido.PENDIENTE, 0,0);
                    System.out.println("Id del pedido recién creado: "+ pedido.getId());
                    pedido.setItemsPedido(listItemsPedido);

                    System.out.println("Se creó el pedido:" + pedido.toString());
                    t = Toast.makeText(PedidoCreateActivity.this,R.string.orderCreated,Toast.LENGTH_LONG);
                    t.show();
                    sendOrderBtn.setEnabled(true);
                    orderCreated = true;
                    //TODO guardar pedido en ROOM/SQLite.
                    DBClient.getInstance(PedidoCreateActivity.this).getRoomDB().pedidoDao().insertPedido(pedido);
                    //System.out.println("Pedido despues de guardarlo en la BD:" + pedido.toString());
                    DBClient.getInstance(PedidoCreateActivity.this).getRoomDB().itemPedidoDao().insertAllItemsPedido(pedido.getItemsPedido());
                    idPedidoCreado = pedido.getId();
                }
            }
        });

        sendOrderBtn = findViewById(R.id.sendOrderBtn);
        sendOrderBtn.setEnabled(false);
        sendOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO hacer lo del mapa y guardar en retrofit (hay que ver si se puede enviar un pedido que todavía no se "creó"
                if(idPedidoCreado != null){
                    Intent getLocationIntent = new Intent(PedidoCreateActivity.this, MapActivity.class);
                    getLocationIntent.setAction("getLocation");
                    startActivityForResult(getLocationIntent, MapActivity.GET_LOCATION);
                }else{
                    Toast.makeText(getApplicationContext(),R.string.databaseSavePedidoError, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult de PedidoCreateActivity");
        if(requestCode == MapActivity.GET_LOCATION){
            System.out.println("Entra al if");
            switch (resultCode){
                case Activity.RESULT_OK:{
                    Pedido.PedidoConItems pedidoConItems = DBClient.getInstance(this).getRoomDB().pedidoDao().getPedidoConItemsByIdPedido(idPedidoCreado);
                    pedidoConItems.pedido.setEstado(EstadoPedido.ENVIADO);
                    pedidoConItems.pedido.setItemsPedido(pedidoConItems.itemsPedido);
                    Double lat, lon;
                    lon = data.getDoubleExtra("longitud", 300);
                    lat = data.getDoubleExtra("latitud", 300);
                    if(lat!=300 && lon != 300){
                        pedidoConItems.pedido.setLatitud(lat);
                        pedidoConItems.pedido.setLongitud(lon);
                        RetrofitRepository.getInstance().savePedido(pedidoConItems.pedido,handler);
                        Toast.makeText(this, "Pedido enviado", Toast.LENGTH_LONG).show();
                        System.out.println("finish()");
                        PedidoCreateActivity.this.finish();
                    }else {
                        Toast.makeText(PedidoCreateActivity.this, "Se produjo un error al seleccionar la ubicación", Toast.LENGTH_LONG).show();
                    }
                }
                case Activity.RESULT_CANCELED:{
                    Toast.makeText(this, "Se produjo un error al seleccionar la ubicación", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                requestFinish();
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


    private void requestFinish(){
        if(orderCreated){
            showAlertDialog(getString(R.string.pedidoNoEnviadoMsgAlertDialogPedidoCreateActivity));
        }
        else{
            showAlertDialog(getString(R.string.pedidoNoGuardado));
        }
    }

    private void showAlertDialog(String msgAlertDialog){

        LayoutInflater inflater = this.getLayoutInflater();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.titleAlertDialogPedidoCreateActivity);
        builder.setMessage(msgAlertDialog);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed(){
        requestFinish();
        super.onBackPressed();
    }

}
