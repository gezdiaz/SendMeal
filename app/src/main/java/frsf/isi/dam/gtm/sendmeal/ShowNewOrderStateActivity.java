package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

public class ShowNewOrderStateActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView pedidoRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.ViewHolder viewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private Pedido pedidoFirebase;
    private TextView idPedidoLbl, statePedidoLbl, totalPriceLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_new_order_state);

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


        if(getIntent().getExtras().get("pedido") != null) {
            pedidoFirebase = (Pedido)getIntent().getExtras().get("pedido");
            idPedidoLbl.setText(idPedidoLbl.getText().toString() + ": " + pedidoFirebase.getId());
            statePedidoLbl.setText(statePedidoLbl.getText().toString() + ": " + pedidoFirebase.getEstado());
            totalPriceLbl.setText(totalPriceLbl.getText().toString() + ": " + pedidoFirebase.getPrecioTotal());
            ((NewOrderStateAdapter) adapter).setPlatoViewDataSet(pedidoFirebase.getItemsPedido());
        }

       pedidoRecyclerView.setAdapter(adapter);
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
