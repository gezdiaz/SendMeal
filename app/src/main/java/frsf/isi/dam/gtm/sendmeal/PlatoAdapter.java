package frsf.isi.dam.gtm.sendmeal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.icu.text.DecimalFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> platoViewDataSet;
    private DishViewActivity activity;
    private Context context;
    private Integer globalPos;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case RetrofitRepository.UPDATE_PLATO: {
                    Plato updatedPlato = (Plato) msg.obj;
                    Toast toast = Toast.makeText(activity.getApplicationContext(), R.string.dishUpdateSuccess, Toast.LENGTH_SHORT);
                    toast.show();
                    if (updatedPlato.getInOffer()) {
                        createThread(globalPos);
                    }
                    updatePlatos();
                    break;
                }
                case RetrofitRepository.GET_SEARCH_PLATO:{

                    List<Plato> platosEncontrados = (List<Plato>) msg.obj;
                    platoViewDataSet = platosEncontrados;
                    notifyDataSetChanged();

                    break;
                }
                case RetrofitRepository.GETALL_PLATOS:{
                    platoViewDataSet = (List<Plato>) msg.obj;
                    notifyDataSetChanged();
                    break;
                }
                case RetrofitRepository.DELETE_PLATO:{
                    Plato deleted = null;
                    for(Plato p: platoViewDataSet){
                        if(p.getId() == msg.arg1){
                            deleted = p;
                        }
                    }
                    platoViewDataSet.remove(deleted);
                    notifyDataSetChanged();
                    Toast t = Toast.makeText(activity, R.string.dishDeleteSuccess, Toast.LENGTH_LONG);
                    t.show();
                    break;
                }
                case RetrofitRepository.ERROR_DELETE_PLATO:{
                    Toast t = Toast.makeText(activity, R.string.databaseDeleteDishError, Toast.LENGTH_LONG);
                    t.show();
                    break;
                }
                case RetrofitRepository.ERROR_SEARCH_PLATO:
                case RetrofitRepository.ERROR_GETALL_PLATOS:{
                    Toast t = Toast.makeText(activity, R.string.databaseGetAllDishesError, Toast.LENGTH_LONG);
                    t.show();
                    break;
                }
                case RetrofitRepository.ERROR_UPDATE_PLATO:{
                    Toast t = Toast.makeText(activity, R.string.databaseSaveDishError, Toast.LENGTH_LONG);
                    t.show();
                    break;
                }
            }
        }
    };

    public PlatoAdapter (DishViewActivity activity) {
        this.activity = activity;
    }

    public void setPlatoViewDataSet(List<Plato> platoViewDataSet) {
        this.platoViewDataSet = platoViewDataSet;
    }

    public void updatePlatos(){
        RetrofitRepository.getInstance().getPlatos(handler);
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.fila_plato, parent,false);
        return new PlatoViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return platoViewDataSet.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final PlatoViewHolder holder, int position) {

        final Plato plato = platoViewDataSet.get(position);

        DecimalFormat format = new DecimalFormat("0.00");

        holder.dishNameView.setText(plato.getTitulo());
        if(plato.getInOffer()){
            holder.dishPriceView.setText("$ "+format.format(plato.getPrecioPlato()));
            if(!holder.dishPriceView.getPaint().isStrikeThruText()){
                holder.dishPriceView.setPaintFlags(holder.dishPriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.offerPriceView.setText("$ " + format.format(plato.getPrecioOferta()));
            holder.offerPriceView.setVisibility(View.VISIBLE);
        }else{
            holder.dishPriceView.setText("$ "+format.format(plato.getPrecioPlato()));
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
                    RetrofitRepository.getInstance().updatePlato(platoViewDataSet.get(pos), handler);
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
                removeDish(pos);
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
                                        Plato plato = platoViewDataSet.get(pos);
                                        plato.switchInOffer(percentage / 100.0);
                                        RetrofitRepository.getInstance().updatePlato(plato, handler);
                                        globalPos = pos;
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
                                Plato plato = platoViewDataSet.get(pos);
                                plato.switchInOffer(percentage / 100.0);
                                RetrofitRepository.getInstance().updatePlato(plato, handler);
                                globalPos = pos;
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


    public void getPlatosBySearchResults(String title, double priceMin, double priceMax){

        RetrofitRepository.getInstance().getPlatosBySearchResults(title, priceMin, priceMax, handler);

    }

    public void removeDish(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(R.string.removeDishQuestion);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Plato a aliminar"+platoViewDataSet.get(pos));
                System.out.println("En la posición: "+pos);
                RetrofitRepository.getInstance().deletePlato(platoViewDataSet.get(pos), handler);
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

}
