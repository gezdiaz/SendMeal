package frsf.isi.dam.gtm.sendmeal.dao;

import android.os.Message;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.rest.PedidoRest;
import frsf.isi.dam.gtm.sendmeal.dao.rest.PlatoRest;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;
import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRepository {

    private static RetrofitRepository instance;
    private static String SERVER = "http://10.15.153.240:5000";

    public static final int ALTA_PLATO = 1;
    public static final int UPDATE_PLATO = 2;
    public static final int ERROR_ALTA_PLATO = 3;
    public static final int ERROR_UPDATE_PLATO = 4;
    public static final int GET_PLATO = 5;
    public static final int GETALL_PLATOS = 6;
    public static final int ERROR_GET_PLATO = 7;
    public static final int ERROR_GETALL_PLATOS = 8;
    public static final int DELETE_PLATO = 9;
    public static final int ERROR_DELETE_PLATO = 10;
    public static final int GET_SEARCH_PLATO = 11;
    public static final int ERROR_SEARCH_PLATO = 12;
    public static final int ALTA_PEDIDO = 13;
    public static final int ERROR_ALTA_PEDIDO = 14;
    public static final int UPDATE_PEDIDO = 15;
    public static final int ERROR_UPDATE_PEDIDO = 16;
    public static final int GET_PEDIDO = 17;
    public static final int ERROR_GET_PEDIDO = 18;
    public static final int GETALL_PEDIDOS = 19;
    public static final int ERROR_GETALL_PEDIDOS = 20;
    public static final int DELETE_PEDIDO = 21;
    public static final int ERROR_DELETE_PEDIDO = 22;


    private PlatoRest platoRest;
    private PedidoRest pedidoRest;

    private RetrofitRepository(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(SERVER);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        platoRest = retrofit.create(PlatoRest.class);
        pedidoRest = retrofit.create(PedidoRest.class);

    }

    public static RetrofitRepository getInstance(){
        if(instance == null){
            instance = new RetrofitRepository();
        }
        return instance;
    }

    public void getPlatoById(int id, final Handler handler){
        Call<Plato> call = platoRest.getPlatoById(id);
        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if (response.isSuccessful()) {
                    Message m = Message.obtain();
                    m.what = GET_PLATO;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Message m = new Message();
                m.what = ERROR_GET_PLATO;
                handler.sendMessage(m);
            }
        });
    }

    public void getPlatos(final Handler handler){
        Call<List<Plato>> call = platoRest.getPlatos();
        call.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    Message m = Message.obtain();
                    m.what = GETALL_PLATOS;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Message m = Message.obtain();
                m.what = ERROR_GETALL_PLATOS;
                handler.sendMessage(m);
            }
        });
    }

    public void savePlato(Plato plato, final Handler handler){
        final Call<Plato> call = platoRest.savePlato(plato);
        System.out.println("Ejecuta savePlato en Retrofit");
        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if(response.isSuccessful()){
                    Message m = Message.obtain();
                    m.what = ALTA_PLATO;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Message m = new Message();
                m.what = ERROR_ALTA_PLATO;
                handler.sendMessage(m);
            }
        });
    }

    public void updatePlato(Plato plato, final Handler handler){
        final Call<Plato> call = platoRest.updatePlato(plato.getId(),plato);
        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if(response.isSuccessful()){
                    Message m = new Message();
                    m.what = UPDATE_PLATO;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Message m = new Message();
                m.what = ERROR_UPDATE_PLATO;
                handler.sendMessage(m);
            }
        });
    }

    public void deletePlato(Plato plato, final Handler handler){
        System.out.println("En retrofit se recibió el plato: "+plato);
        System.out.println("Con id: "+plato.getId());
        Call<Plato> call = platoRest.deletePlato(plato.getId());
        final int id = plato.getId();
        System.out.println("Ejecuta enqueue");
        call.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                if(response.isSuccessful()){
                    System.out.println("Termina la ejecución correctamente");
                    Message m = new Message();
                    m.what = DELETE_PLATO;
                    m.obj = response.body();
                    m.arg1 = id;
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Message m = new Message();
                m.what = ERROR_DELETE_PLATO;
                handler.sendMessage(m);
            }
        });
    }


    public void getPlatosBySearchResults(String title, double priceMin, double  priceMax, final Handler handler){
        Call<List<Plato>> call = platoRest.getPlatosSearchResult(title, priceMin,priceMax);

        call.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {

                if(response.isSuccessful()){
                    Message m = Message.obtain();
                    m.what = GET_SEARCH_PLATO;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }

            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {

                Message m = Message.obtain();
                m.what = ERROR_SEARCH_PLATO;
                handler.sendMessage(m);
            }
        });


    }

    public void savePedido(Pedido pedido, final Handler handler) {

        final Call<Pedido> call = pedidoRest.savePedido(pedido);
        System.out.println("Ejecuta savePedido en Retrofit");
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Message m = Message.obtain();
                    m.what = ALTA_PEDIDO;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                System.out.println("Error en save pedido: "+t.getMessage());
                Message m = new Message();
                m.what = ERROR_ALTA_PEDIDO;
                handler.sendMessage(m);
            }
        });
    }

    public void getPedidoById(String id, final Handler handler) {
        Call<Pedido> call = pedidoRest.getPedidoById(id);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful()) {
                    Message m = Message.obtain();
                    m.what = GET_PEDIDO;
                    m.obj = response.body();
                    handler.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Message m = new Message();
                m.what = ERROR_GET_PEDIDO;
                handler.sendMessage(m);
            }
        });

    }

        public void getPedidos(final Handler handler){
            Call<List<Pedido>> call = pedidoRest.getPedidos();
            call.enqueue(new Callback<List<Pedido>>() {
                @Override
                public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                    if(response.isSuccessful()){
                        Message m = Message.obtain();
                        m.what = GETALL_PEDIDOS;
                        m.obj = response.body();
                        handler.sendMessage(m);
                    }
                }

                @Override
                public void onFailure(Call<List<Pedido>> call, Throwable t) {
                    Message m = Message.obtain();
                    m.what = ERROR_GETALL_PEDIDOS;
                    handler.sendMessage(m);
                }
            });
        }


        public void updatePedido(Pedido pedido, final Handler handler){
            final Call<Pedido> call = pedidoRest.updatePedido(pedido.getId(),pedido);
            call.enqueue(new Callback<Pedido>() {
                @Override
                public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                    if(response.isSuccessful()){
                        Message m = new Message();
                        m.what = UPDATE_PEDIDO;
                        m.obj = response.body();
                        handler.sendMessage(m);
                    }
                }

                @Override
                public void onFailure(Call<Pedido> call, Throwable t) {
                    Message m = new Message();
                    m.what = ERROR_UPDATE_PEDIDO;
                    handler.sendMessage(m);
                }
            });
        }

        public void deletePedido(Pedido pedido, final Handler handler){
            System.out.println("En retrofit se recibió el plato: "+pedido);
            System.out.println("Con id: "+pedido.getId());
            Call<Pedido> call = pedidoRest.deletePedido(pedido.getId());
            final String id = pedido.getId();
            System.out.println("Ejecuta enqueue");
            call.enqueue(new Callback<Pedido>() {
                @Override
                public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                    if(response.isSuccessful()){
                        System.out.println("Termina la ejecución correctamente");
                        Message m = new Message();
                        m.what = DELETE_PEDIDO;
                        m.obj = response.body();
                        handler.sendMessage(m);
                    }
                }

                @Override
                public void onFailure(Call<Pedido> call, Throwable t) {
                    Message m = new Message();
                    m.what = ERROR_DELETE_PEDIDO;
                    handler.sendMessage(m);
                }
            });
        }

}
