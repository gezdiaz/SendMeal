package frsf.isi.dam.gtm.sendmeal.dao.rest;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlatoRest {
    @GET("platos/")
    Call<List<Plato>> getPlatos();

    @GET("platos/{id}")
    Call<Plato> getPlatoById(@Path("id") int id);

    @POST("platos/")
    Call<Plato> savePlato(@Body Plato p);

    @PUT("platos/{id}")
    Call<Plato> updatePlato(@Path("id") int id, @Body Plato p);

    @DELETE("platos/{id}")
    Call<Plato> deletePlato(@Path("id") int id);
}
