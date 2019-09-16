package frsf.isi.dam.gtm.sendmeal;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> platoViewDataSet;


    public PlatoAdapter (List<Plato> myPlatosDataSet) {
        platoViewDataSet = myPlatosDataSet;
    }

    private Context context;

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = (View) LayoutInflater.from(context).inflate(R.layout.fila_plato, parent,false);
        PlatoViewHolder platoViewHolder = new PlatoViewHolder(v);
        return platoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {

        Plato plato = platoViewDataSet.get(position);

        DecimalFormat format = new DecimalFormat("0.00");

        holder.dishNameView.setText(plato.getTitulo());
        holder.dishPriceView.setText(context.getString(R.string.dishPriceListLabel)+format.format(plato.getPrecio()));
        holder.dishImageView.setImageResource(R.drawable.plato_de_comida);


    }

    @Override
    public int getItemCount() {
        return platoViewDataSet.size();
    }


}
