package frsf.isi.dam.gtm.sendmeal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlatoViewHolder extends RecyclerView.ViewHolder {
    public ImageView dishImageView;
    public TextView dishNameView, dishPriceView;

    public PlatoViewHolder(@NonNull View itemView) {
        super(itemView);
        dishImageView = itemView.findViewById(R.id.dishImageView);
        dishNameView = itemView.findViewById(R.id.dishName);
        dishPriceView = itemView.findViewById(R.id.dishPrice);
    }
}
