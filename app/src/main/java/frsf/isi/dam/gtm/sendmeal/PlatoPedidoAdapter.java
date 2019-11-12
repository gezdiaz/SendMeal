package frsf.isi.dam.gtm.sendmeal;

import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PlatoPedidoAdapter extends RecyclerView.Adapter<PlatoPedidoHolder> {

    private HashSet<Plato> selectedPlatos = new HashSet<>();
    private SparseIntArray selectedPlatosQuantity = new SparseIntArray();

    private List<Plato> platoDataSet;
    private PedidoCreateActivity activity;


    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){

                case RetrofitRepository.GET_SEARCH_PLATO:{

                    List<Plato> platosEncontrados = (List<Plato>) msg.obj;
                    platoDataSet = platosEncontrados;
                    notifyDataSetChanged();

                    break;
                }
                case RetrofitRepository.ERROR_SEARCH_PLATO:{
                    Toast t = Toast.makeText(activity, R.string.databaseGetAllDishesError, Toast.LENGTH_LONG);
                    t.show();
                    break;
                }
            }
        }
    };

    public PlatoPedidoAdapter(PedidoCreateActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlatoPedidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato_pedido, parent,false);
        return new PlatoPedidoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlatoPedidoHolder holder, int position) {
        final Plato plato = platoDataSet.get(position);

        DecimalFormat format = new DecimalFormat("0.00");

        holder.dishNamePedido.setText(plato.getTitulo());
        if(plato.getInOffer()){
            holder.dishPricePedido.setText("$ "+format.format(plato.getPrecioPlato()));
            if(!holder.dishPricePedido.getPaint().isStrikeThruText()){
                holder.dishPricePedido.setPaintFlags(holder.dishPricePedido.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.offerPricePedido.setText("$ " + format.format(plato.getPrecioOferta()));
            holder.offerPricePedido.setVisibility(View.VISIBLE);
        }else{
            holder.dishPricePedido.setText("$ "+format.format(plato.getPrecioPlato()));
            if(holder.dishPricePedido.getPaint().isStrikeThruText()){
                holder.dishPricePedido.setPaintFlags(holder.dishPricePedido.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
            }
            holder.offerPricePedido.setText("");
            holder.offerPricePedido.setVisibility(View.INVISIBLE);
        }

        if(plato.getImageBase64() != null){
            holder.dishImagePedido.setImageBitmap(plato.getImage());
        }else{
            holder.dishImagePedido.setImageResource(R.drawable.hamburger);
        }

        if(plato.getInOffer()){
            holder.offerImagePedido.setVisibility(View.VISIBLE);
        }else{
            holder.offerImagePedido.setVisibility(View.INVISIBLE);
        }
        Plato p = platoDataSet.get(holder.getAdapterPosition());
        if(selectedPlatos.contains(p)){
            holder.quantityPedido.setText(String.valueOf(selectedPlatosQuantity.get(p.getId())));
        }else{
            holder.quantityPedido.setText("0");
        }

        //TODO actualizar lista
        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO restar cantidad
                int pos = holder.getAdapterPosition();
                Plato plato = platoDataSet.get(pos);
                if(selectedPlatos.contains(plato)){
                    Integer quantity = selectedPlatosQuantity.get(plato.getId());
                    quantity--;
                    if(quantity<=0){
                        selectedPlatos.remove(plato);
                        selectedPlatosQuantity.delete(plato.getId());
                        holder.quantityPedido.setText("0");
                    }else{
                        selectedPlatosQuantity.put(plato.getId(),quantity);
                        holder.quantityPedido.setText(quantity.toString());
                    }
                }
            }
        });
        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO incrementar cantidad
                int pos = holder.getAdapterPosition();
                Plato plato = platoDataSet.get(pos);
                if(selectedPlatos.contains(plato)){
                    Integer quantity = selectedPlatosQuantity.get(plato.getId());
                    quantity++;
                    selectedPlatosQuantity.put(plato.getId(), quantity);
                    holder.quantityPedido.setText(quantity.toString());
                }else{
                    selectedPlatos.add(plato);
                    selectedPlatosQuantity.put(plato.getId(), 1);
                    holder.quantityPedido.setText("1");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return platoDataSet.size();
    }

    public void setPlatoDataSet(List<Plato> platos) {
        platoDataSet = platos;
    }

    public void getPlatosBySearchResults(String title, double minPrice, double maxPrice) {
        RetrofitRepository.getInstance().getPlatosBySearchResults(title, minPrice, maxPrice, handler);
    }

    public List<ItemPedido> getItems(){
        List<ItemPedido> listaItems = new ArrayList<>();

        for(Plato p: selectedPlatos){
            ItemPedido item = new ItemPedido();
            item.setPlato(p);
            item.setCantidad(selectedPlatosQuantity.get(p.getId()));
            item.calcularPrecio();
            listaItems.add(item);
        }

        return listaItems;
    }
}
