package frsf.isi.dam.gtm.sendmeal.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.rest.PlatoRest;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRepository {

    private static RetrofitRepository instance;
    private static String SERVER = "http://10.15.158.59:5000";



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

    public void savePlato(Plato plato){
        final Call<Plato> call = platoRest.savePlato(plato);
        new Thread(){
            @Override
            public void run() {
                Response<Plato> resp = null;
                try{
                    resp = call.execute();
                    System.out.println("Ejecuto correctamente savePlato en repository");
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }.start();
    }

    public void updatePlato(Plato plato){
        final Call<Plato> call = platoRest.updatePlato(plato.getId(),plato);
        new Thread(){
            @Override
            public void run() {
                Response<Plato> resp = null;
                try{
                    resp = call.execute();
                    System.out.println("Ejecuto correctamente updatePlato en repository");
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }.start();
    }

}
