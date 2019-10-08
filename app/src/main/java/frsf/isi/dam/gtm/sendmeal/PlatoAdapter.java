package frsf.isi.dam.gtm.sendmeal;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> platoViewDataSet;
    DishViewActivity activity;
    private Context context;


    public PlatoAdapter (List<Plato> myPlatosDataSet, DishViewActivity activity) {
        platoViewDataSet = myPlatosDataSet;
        this.activity = activity;
    }

    public void updatePlatos(List<Plato> newPlatos){
        platoViewDataSet = newPlatos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = (View) LayoutInflater.from(context).inflate(R.layout.fila_plato, parent,false);
        return new PlatoViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final PlatoViewHolder holder, int position) {

        Plato plato = platoViewDataSet.get(position);

        DecimalFormat format = new DecimalFormat("0.00");

        holder.dishNameView.setText(plato.getTitulo());
        holder.dishPriceView.setText(context.getString(R.string.dishPriceListLabel)+format.format(plato.getPrecio()));

        int[] images = {R.drawable.milanesa, R.drawable.hamburger, R.drawable.papas_cheddar, R.drawable.pizza, R.drawable.tarta, R.drawable.tarta_vertical};
        Random rand = new Random();
        holder.dishImageView.setImageResource(images[rand.nextInt(images.length)]);
        holder.offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.getAdapterPosition();
                platoViewDataSet.get(pos).switchInOffer();
                //TODO Falta crear hilo secundario.
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO falta actualizar lista.
                Integer pos = (Integer) holder.getAdapterPosition();
                activity.editDish(pos);
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //TODO falta todo
                Integer pos = (Integer) holder.getAdapterPosition();
                activity.removeDish(pos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return platoViewDataSet.size();
    }


}
