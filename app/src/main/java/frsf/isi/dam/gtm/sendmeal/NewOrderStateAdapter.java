package frsf.isi.dam.gtm.sendmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

public class NewOrderStateAdapter extends RecyclerView.Adapter<OrderStateHolder> {

    private Pedido pedido;
    private List<ItemPedido> itemPedidoViewDataSet;
    private ShowNewOrderStateActivity activity;

    public NewOrderStateAdapter (ShowNewOrderStateActivity activity) {
        this.activity = activity;
    }

    public void setPlatoViewDataSet(List<ItemPedido> itemsPedido) {
        this.itemPedidoViewDataSet = itemsPedido;
    }

    @NonNull
    @Override
    public OrderStateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato_pedido_state, parent, false);
        return new OrderStateHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStateHolder holder, int position) {

        final ItemPedido itemPedido = itemPedidoViewDataSet.get(position);

        holder.dishNameLbl.setText(holder.dishNameLbl.getText().toString() + ": " + itemPedido.getPlato().getTitulo());
        holder.quantityDishLbl.setText(holder.quantityDishLbl.getText().toString() + ": " + itemPedido.getCantidad());
        holder.priceDishLbl.setText(holder.priceDishLbl.getText().toString() + ": " + (itemPedido.getPlato().getPrecioPlato()*itemPedido.getPlato().getOferta()));

    }

    @Override
    public int getItemCount() {
        return itemPedidoViewDataSet.size();
    }
}
