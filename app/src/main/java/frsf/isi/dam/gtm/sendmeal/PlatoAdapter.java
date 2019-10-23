package frsf.isi.dam.gtm.sendmeal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> platoViewDataSet;
    DishViewActivity activity;
    private Context context;


    public PlatoAdapter (DishViewActivity activity) {
        this.activity = activity;
    }

    public void setPlatoViewDataSet(List<Plato> platoViewDataSet) {
        this.platoViewDataSet = platoViewDataSet;
    }

    public void updatePlatos(){
        final Call<List<Plato>> c = RetrofitRepository.getInstance().getPlatos();
        new Thread(){
            @Override
            public void run() {
                Response<List<Plato>> response;
                try {
                    response = c.execute();
                    PlatoAdapter.this.platoViewDataSet = response.body();
                    PlatoAdapter.this.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error en updatePlatos del Adapter");
                }

            }
        }.start();

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

        final Plato plato = platoViewDataSet.get(position);

        DecimalFormat format = new DecimalFormat("0.00");

        holder.dishNameView.setText(plato.getTitulo());
        if(plato.getInOffer()){
            holder.dishPriceView.setText("$ "+format.format(plato.getPrecio()));
            if(!holder.dishPriceView.getPaint().isStrikeThruText()){
                holder.dishPriceView.setPaintFlags(holder.dishPriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.offerPriceView.setText("$ " + format.format(plato.getPrecioOferta()));
            holder.offerPriceView.setVisibility(View.VISIBLE);
        }else{
            holder.dishPriceView.setText("$ "+format.format(plato.getPrecio()));
            if(holder.dishPriceView.getPaint().isStrikeThruText()){
                holder.dishPriceView.setPaintFlags(holder.dishPriceView.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
            }
            holder.offerPriceView.setText("");
            holder.offerPriceView.setVisibility(View.INVISIBLE);
        }


        holder.dishImageView.setImageResource(R.drawable.hamburger);

        if(plato.getInOffer()){
            holder.offerImage.setVisibility(View.VISIBLE);
        }else{
            holder.offerImage.setVisibility(View.INVISIBLE);
        }

        holder.offerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer pos = (Integer) holder.getAdapterPosition();

                if(platoViewDataSet.get(pos).getInOffer()){
                    platoViewDataSet.get(pos).switchInOffer(0.0);
                    Plato.platos = (ArrayList<Plato>)platoViewDataSet;
                    updatePlatos();
                }else{
                    createOfferDialog(pos);
                }
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.getAdapterPosition();
                activity.editDish(platoViewDataSet.get(pos).getId());
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.getAdapterPosition();
                activity.removeDish(pos);
            }
        });
    }

    private void createOfferDialog(final Integer pos) {
        LayoutInflater inflater = activity.getLayoutInflater();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = inflater.inflate(R.layout.choose_offer, null);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText offerPercentage = dialogView.findViewById(R.id.offerPercentage);
                offerPercentage.clearFocus();
                Toast t;
                try {
                    final int percentage = Integer.parseInt(offerPercentage.getText().toString());
                    if(percentage > 100){
                        t = Toast.makeText(activity, R.string.percentageOver100, Toast.LENGTH_LONG);
                        t.show();
                    }else{
                        if(percentage == 0){
                            t = Toast.makeText(activity, R.string.offer0p, Toast.LENGTH_LONG);
                            t.show();
                        }else {
                            if (percentage == 100) {
                                AlertDialog.Builder b = new AlertDialog.Builder(builder.getContext());
                                b.setTitle(R.string.offer100p);
                                b.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        platoViewDataSet.get(pos).switchInOffer(percentage / 100.0);
                                        //TODO base de datos
                                        Plato.platos = (ArrayList<Plato>)platoViewDataSet;
                                        updatePlatos();
                                        createThread(pos);
                                    }
                                });
                                b.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                AlertDialog dial = b.create();
                                dial.show();
                            } else {
                                platoViewDataSet.get(pos).switchInOffer(percentage / 100.0);
                                //TODO base de datos
                                Plato.platos = (ArrayList<Plato>)platoViewDataSet;
                                updatePlatos();
                                createThread(pos);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    t = Toast.makeText(activity,R.string.noPercentage, Toast.LENGTH_LONG);
                    t.show();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return platoViewDataSet.size();
    }

    private void createThread(final Integer pos) {
        System.out.println("Antes de crear el hilo");
        new Thread(){
            @Override
            public void run() {
                try{
                    sleep(10000);
                    Intent i = new Intent();
                    i.setAction(NotificationReceiver.OFFERNOTIFICATION);
                    i.putExtra("plato", platoViewDataSet.get(pos));
                    context.sendBroadcast(i);
                }catch (Exception e){
                    Log.e("exeption", "Se capturo una exepción en el hilo secundario");
                }
            }
        }.start();

    }

}
