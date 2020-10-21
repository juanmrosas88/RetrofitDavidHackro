package com.jmrootkit.retrofitdavidhackro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  new Peticion().execute(); //Este metodo es llamado asincronamente


     //   getUsers(); //Este metodo es llamado sincro. Osea en el thread principal


        findUserByID(); //Este metodo se llama para ir a buscar un usuario segun su id.

        findUserPost();

    }


    public static class Peticion extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            final String url = "https://androidtutorials.herokuapp.com/";


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            serviceTutorial service = retrofit.create(serviceTutorial.class);
            Call<List<ResponseService>> response = service.getUsersGet();

            try {
                for (ResponseService user : response.execute().body()) {
                    Log.e("Respuesta ", user.getName());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void getUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://androidtutorials.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceTutorial serviceTuto = retrofit.create(serviceTutorial.class);
        Call<List<ResponseService>> call = serviceTuto.getUsersGet();

        call.enqueue(new Callback<List<ResponseService>>() {
            @Override
            public void onResponse(Call<List<ResponseService>> call, Response<List<ResponseService>> response) {
                for (ResponseService res : response.body()) {
                    Log.e("Usuario ", res.getId() + " " + res.getName());

                }
            }

            @Override
            public void onFailure(Call<List<ResponseService>> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
            }
        });
    }

    public void findUserByID() {
        final String url = "https://androidtutorials.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceTutorial service = retrofit.create(serviceTutorial.class);
        Call<ResponseService> call = service.findUserGet(1);

        call.enqueue(new Callback<ResponseService>() {
            @Override
            public void onResponse(Call<ResponseService> call, Response<ResponseService> response) {
                Log.e(TAG, "onResponse: user segun id " + response.body().getName());
            }

            @Override
            public void onFailure(Call<ResponseService> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
            }
        });
    }
    public void findUserPost(){
        final String url = "https://androidtutorials.herokuapp.com/";

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        serviceTutorial servicetuto = retrofit.create(serviceTutorial.class);
        Call<String> call = servicetuto.findUserPost("hackro");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: Exitoso  " + response.body().toString() );
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure: ", t.toString());

            }
        });

    }
}