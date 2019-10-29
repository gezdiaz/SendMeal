package frsf.isi.dam.gtm.sendmeal;

import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PlatoPedidoAdapter extends RecyclerView.Adapter<PlatoPedidoHolder> {

    List<Plato> platoDataSet;
    PedidoCreateActivity activity;
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
    public void onBindViewHolder(@NonNull PlatoPedidoHolder holder, int position) {
        final Plato plato = platoDataSet.get(position);

        DecimalFormat format = new DecimalFormat("0.00");

        holder.dishNamePedido.setText(plato.getTitulo());
        if(plato.getInOffer()){
            holder.dishPricePedido.setText("$ "+format.format(plato.getPrecio()));
            if(!holder.dishPricePedido.getPaint().isStrikeThruText()){
                holder.dishPricePedido.setPaintFlags(holder.dishPricePedido.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.offerPricePedido.setText("$ " + format.format(plato.getPrecioOferta()));
            holder.offerPricePedido.setVisibility(View.VISIBLE);
        }else{
            holder.dishPricePedido.setText("$ "+format.format(plato.getPrecio()));
            if(holder.dishPricePedido.getPaint().isStrikeThruText()){
                holder.dishPricePedido.setPaintFlags(holder.dishPricePedido.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
            }
            holder.offerPricePedido.setText("");
            holder.offerPricePedido.setVisibility(View.INVISIBLE);
        }

        holder.dishImagePedido.setImageResource(R.drawable.hamburger);

        if(plato.getInOffer()){
            holder.offerImagePedido.setVisibility(View.VISIBLE);
        }else{
            holder.offerImagePedido.setVisibility(View.INVISIBLE);
        }

        holder.quantityPedido.setText("0");
        //TODO actualizar lista

        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO restar cantidad
            }
        });
        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO incrementar cantidad
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
}
