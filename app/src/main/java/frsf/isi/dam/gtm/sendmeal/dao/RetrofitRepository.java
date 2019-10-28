package frsf.isi.dam.gtm.sendmeal.dao;

import android.os.Message;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.rest.PlatoRest;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRepository {


    private static RetrofitRepository instance;
    private static String SERVER = "http://192.168.0.8:5000/";

    public static final int ALTA_PLATO = 1;
    public static final int UPDATE_PLATO = 2;
    public static final int ERROR_ALTA_PLATO = 3;
    public static final int ERROR_UPDATE_PLATO = 4;
    public static final int GET_PLATO = 5;
    public static final int GETALL_PLATOS = 6;
    public static final int ERROR_GET_PLATO = 7;
    public static final int ERROR_GETALL_PLATOS = 8;


    private PlatoRest platoRest;

    private RetrofitRepository(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(SERVER);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        platoRest = retrofit.create(PlatoRest.class);
    }

    public static RetrofitRepository getInstance(){
        if(instance == null){
            instance = new RetrofitRepository();
        }
        return instance;
    }

    public Call<Plato> getPlatoById(int id){
        Call<Plato> call = platoRest.getPlatoById(id);
        return call;
//        Response<Plato> resp = null;
//        Plato plato = null;
//        try{
//            resp = call.execute();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(resp != null){
//            plato = resp.body();
//        }
//        return plato;
    }

    public Call<List<Plato>> getPlatos(){
        Call<List<Plato>> call = platoRest.getPlatos();
//        Response<List<Plato>> resp = null;
//        List<Plato> platos = null;
//        try{
//            resp = call.execute();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(resp != null){
//            platos= resp.body();
//        }
        return call;
    }

    public void savePlato(Plato plato, final Handler handler){
        final Call<Plato> call = platoRest.savePlato(plato);
        System.out.println("Ejecuta savePlato en Retrofit");
        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if(response.isSuccessful()){
                    System.out.println("Se guardó el plato correctamente");
                    Message m = Message.obtain();
                    m.what = ALTA_PLATO;
                    m.obj = response.body();
                    System.out.println("Se envía el mensaje ALTA_PLATO");
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                System.out.println("Se produjo un error al guardar el plato");
                Message m = new Message();
                m.what = ERROR_ALTA_PLATO;
                System.out.println("Se envía el mensaje ERROR_ALTA_PLATO");
                handler.sendMessage(m);
            }
        });
    }

    public void updatePlato(Plato plato, final Handler handler){
        final Call<Plato> call = platoRest.updatePlato(plato.getId(),plato);
        System.out.println("Ejecuta updatePlato en Retrofit");
        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if(response.isSuccessful()){
                    System.out.println("Se actualizó el plato correctamente");
                    Message m = new Message();
                    m.what = UPDATE_PLATO;
                    m.obj = response.body();
                    System.out.println("Se envía el mensaje UPDATE_PLATO");
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                System.out.println("Se produjo un error al guardar el plato");
                Message m = new Message();
                m.what = ERROR_UPDATE_PLATO;
                System.out.println("Se envía el mensaje ERROR_UPDATE_PLATO");
                handler.sendMessage(m);
            }
        });
    }

}
