package frsf.isi.dam.gtm.sendmeal;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlatoPedidoHolder extends RecyclerView.ViewHolder {
    public ImageView dishImagePedido, offerImagePedido;
    public TextView dishNamePedido, dishPricePedido, offerPricePedido, quantityPedido;
    public Button decreaseBtn, increaseBtn;

    public PlatoPedidoHolder(@NonNull View itemView) {
        super(itemView);
        dishImagePedido = itemView.findViewById(R.id.dishImageViewPedido);
        offerImagePedido = itemView.findViewById(R.id.offerImagePedido);
        dishNamePedido = itemView.findViewById(R.id.dishNameLblPedido);
        dishPricePedido = itemView.findViewById(R.id.dishPricePedido);
        offerPricePedido = itemView.findViewById(R.id.offerPricePedido);
        quantityPedido = itemView.findViewById(R.id.quantityPedido);
        decreaseBtn = itemView.findViewById(R.id.decreaseBtn);
        increaseBtn = itemView.findViewById(R.id.increaseBtn);
    }

}
