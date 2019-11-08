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

        holder.dishNameLbl.setText(activity.getString(R.string.dishNameLbl) + ": " + itemPedido.getPlato().getTitulo());
        holder.quantityDishLbl.setText(activity.getString(R.string.amount) + ": " + itemPedido.getCantidad());
        holder.priceDishLbl.setText(activity.getString(R.string.dishPriceLbl) + ": $" + itemPedido.getPrecioItem());

    }

    @Override
    public int getItemCount() {
        return itemPedidoViewDataSet.size();
    }
}
