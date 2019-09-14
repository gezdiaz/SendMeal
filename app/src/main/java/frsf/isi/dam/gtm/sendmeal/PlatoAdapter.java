package frsf.isi.dam.gtm.sendmeal;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> platoViewDataSet;


    public PlatoAdapter (List<Plato> myPlatosDataSet) {
        platoViewDataSet = myPlatosDataSet;
    }


    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent,false);
        PlatoViewHolder platoViewHolder = new PlatoViewHolder(v);
        return platoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {

        Plato plato = platoViewDataSet.get(position);

        holder.dishNameView.setText(plato.getTitulo());
        holder.dishPriceView.setText(plato.getPrecio().toString());
        holder.dishImageView.setImageResource(R.drawable.plato_de_comida);


    }

    @Override
    public int getItemCount() {
        return platoViewDataSet.size();
    }


}
