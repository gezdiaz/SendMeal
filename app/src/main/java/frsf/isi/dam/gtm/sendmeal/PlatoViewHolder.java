package frsf.isi.dam.gtm.sendmeal;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlatoViewHolder extends RecyclerView.ViewHolder {
    public ImageView dishImageView, offerImage;
    public TextView dishNameView, dishPriceView, offerPriceView;
    public Button offerBtn, editBtn, removeBtn;

    public PlatoViewHolder(@NonNull View itemView) {
        super(itemView);
        dishImageView = itemView.findViewById(R.id.dishImageView);
        dishNameView = itemView.findViewById(R.id.dishNameLbl);
        dishPriceView = itemView.findViewById(R.id.dishPrice);
        offerPriceView = itemView.findViewById(R.id.offerPrice);
        offerBtn = itemView.findViewById(R.id.offerBtn);
        editBtn = itemView.findViewById(R.id.editBtn);
        removeBtn = itemView.findViewById(R.id.removeBtn);
        offerImage = itemView.findViewById(R.id.offerImage);
    }
}
