package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class ShowNewOrderStateActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView pedidoRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ViewHolder viewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private int idPedido;
    private TextView idPedidoLbl, statePedidoLbl, totalPriceLbl;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case RetrofitRepository.GET_PEDIDO:{
                    Pedido pedidoFirebase = (Pedido) msg.obj;
                    idPedidoLbl.setText(idPedidoLbl.getText().toString() + ": " + pedidoFirebase.getId());
                    statePedidoLbl.setText(statePedidoLbl.getText().toString() + ": " + pedidoFirebase.getEstado());
                    totalPriceLbl.setText(totalPriceLbl.getText().toString() + ": " + pedidoFirebase.getPrecioTotal());
                    ((NewOrderStateAdapter) adapter).setPlatoViewDataSet(pedidoFirebase.getItemsPedido());
                    adapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    break;
                }
                case RetrofitRepository.ERROR_GET_PEDIDO:{
                    Toast.makeText(ShowNewOrderStateActivity.this, R.string.databaseGetPedidoError, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_new_order_state);

        progressDialog = ProgressDialog.show(this, getString(R.string.pleaseWait),getString(R.string.loadingOrderData));
        progressDialog.setCancelable(false);

        idPedidoLbl = findViewById(R.id.idStatePedidoLbl);
        statePedidoLbl = findViewById(R.id.statePedidoLbl);
        totalPriceLbl = findViewById(R.id.totalPriceLbl);

        toolbar = findViewById(R.id.newOrderStateToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pedidoRecyclerView = findViewById(R.id.pedidoRecyclerView);
        pedidoRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        pedidoRecyclerView.setLayoutManager(layoutManager);

        adapter = new NewOrderStateAdapter(this);
        ((NewOrderStateAdapter)adapter).setPlatoViewDataSet(new ArrayList<ItemPedido>());
        pedidoRecyclerView.setAdapter(adapter);

        if(getIntent().getIntExtra("idPedido", 0) != 0) {
            idPedido = getIntent().getIntExtra("idPedido", 0);
            RetrofitRepository.getInstance().getPedidoById(idPedido, handler);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
