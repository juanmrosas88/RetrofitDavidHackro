package com.jmrootkit.retrofitdavidhackro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface serviceTutorial {

    @GET("usersFake")
    Call<List<ResponseService>> getUsersGet();

    @POST("usersFake")
    Call<List<ResponseService>> getUsersPost();

    //https://androidtutorials.herokuapp.com/findUser?id=1
    @GET("findUser")
    Call<ResponseService> findUserGet(@Query("id") int idUser); //El valor @Query(valor) debe ser igual que el del web services.


    @FormUrlEncoded
    @POST("findUserPost")
    Call<String> findUserPost(@Field("name")String nombre);

}
