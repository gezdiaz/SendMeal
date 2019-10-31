package frsf.isi.dam.gtm.sendmeal.dao.rest;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoRest {

    @GET("pedidos/")
    Call<List<Pedido>> getPedidos();

    @GET("pedidos/{id}")
    Call<Pedido> getPedidoById(@Path("id") String id);

    @POST("pedidos/")
    Call<Pedido> savePedido(@Body Pedido p);

    @PUT("pedidos/{id}")
    Call<Pedido> updatePedido(@Path("id") String id, @Body Pedido p);

    @DELETE("pedidos/{id}")
    Call<Pedido> deletePedido(@Path("id") String id);
}
