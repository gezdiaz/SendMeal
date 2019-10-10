package frsf.isi.dam.gtm.sendmeal;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.util.Log;
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

        holder.dishImageView.setImageResource(R.drawable.hamburger);

        if(plato.getInOffer()){
            holder.offerImage.setVisibility(View.VISIBLE);
        }else{
            holder.offerImage.setVisibility(View.INVISIBLE);
        }

        holder.offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.getAdapterPosition();
                platoViewDataSet.get(pos).switchInOffer();
                updatePlatos(Plato.platos);

                if(platoViewDataSet.get(pos).getInOffer()){
                    new Thread(){
                        @Override
                        public void run() {
                            try{
                                sleep(10000);
                                Intent i = new Intent();
                                i.setAction(NotificationReceiver.OFFERNOTIFICATION);
                                activity.sendBroadcast(i);
                            }catch (Exception e){
                                Log.e("exeption", "Se capturo una exepción en el hilo secundario");
                            }
                        }
                    }.start();
                }

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
