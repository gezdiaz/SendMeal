package frsf.isi.dam.gtm.sendmeal;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStateHolder extends RecyclerView.ViewHolder {
    public TextView dishNameLbl, quantityDishLbl, priceDishLbl;

    public OrderStateHolder(@NonNull View itemView) {
        super(itemView);
        dishNameLbl = itemView.findViewById(R.id.dishNameLbl);
        quantityDishLbl = itemView.findViewById(R.id.quantityDishLbl);
        priceDishLbl = itemView.findViewById(R.id.priceDishLbl);
    }
}
