package com.example.tp42;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp42.Entidades.Pelicula;
import com.example.tp42.Entidades.ResultadoBusqueda;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ResultadoFragment extends Fragment {
    private String link = "https://www.omdbapi.com/?apikey=1179e855&i=";
    TextView tvTitulo, tvInfo, tvSinopsis;
    ImageView imgViewPoster;
    View rootLayout;
    String id;
    Pelicula infoPelicula;


    public ResultadoFragment(String ID) {
        id = ID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootLayout == null){
            rootLayout = inflater.inflate(R.layout.fragment_resultado, container, false);
        }
        ObtenerReferencias();
        Tarea();
        return rootLayout;
    }


    private class TareaAsincronica extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection miConexion = null;
            URL strApiUrl;
            String url = link + id;
            Log.d("url", url);
            BufferedReader responseReader;
            String responseLine, strResultado = "";
            StringBuilder sbResponse;
            Context context;
            context = getContext();
            try {
                strApiUrl = new URL(url);
                miConexion = (HttpURLConnection) strApiUrl.openConnection();
                miConexion.setRequestMethod("GET");
                if (miConexion.getResponseCode() == 200) {


                    responseReader = new BufferedReader(new InputStreamReader(miConexion.getInputStream()));
                    sbResponse = new StringBuilder();
                    while ((responseLine = responseReader.readLine()) != null) {
                        sbResponse.append(responseLine);
                    }
                    responseReader.close();
                    strResultado = sbResponse.toString();
                    Log.d("resultado", strResultado);
                } else {
                }
            } catch (Exception e) {
                Log.d("ErrorA", e.getMessage());
            } finally {
                if (miConexion != null) {
                    miConexion.disconnect();
                }
            }
            return strResultado;
        }

        @Override
        protected void onPostExecute(String s) {
            parsearStringToGson(s);
            arreglarInfo();
        }
    }

    private void Tarea(){
       TareaAsincronica miTarea = new TareaAsincronica();
       miTarea.execute();
    }

    private void parsearStringToGson(String resultadoAParsear){
        Log.d("String resultado peli", resultadoAParsear);
        String strTitle, year, genre, poster, language, runtime, plot;
        JsonObject convertedObject = new Gson().fromJson(resultadoAParsear,JsonObject.class);
        strTitle = convertedObject.get("Title").getAsString();
        year = convertedObject.get("Year").getAsString();
        genre = convertedObject.get("Genre").getAsString();
        poster = convertedObject.get("Poster").getAsString();
        language = convertedObject.get("Language").getAsString();
        runtime = convertedObject.get("Runtime").getAsString();
        plot = convertedObject.get("Plot").getAsString();
        infoPelicula = new Pelicula(strTitle,year,genre,poster,runtime,language,plot);
    }

    private void ObtenerReferencias(){
        tvTitulo = (TextView) rootLayout.findViewById(R.id.textViewTitulo);
        tvInfo = (TextView) rootLayout.findViewById(R.id.textViewInformacion);
        tvSinopsis = (TextView) rootLayout.findViewById(R.id.textViewSinopsis);
        imgViewPoster = (ImageView) rootLayout.findViewById(R.id.imageViewPoster);
    }

    private void arreglarInfo(){
        String titulo = infoPelicula.Title + " " + infoPelicula.Year;
        tvTitulo.setText(titulo);
        String sinopsis = infoPelicula.Plot;
        tvSinopsis.setText(sinopsis);
        String info = infoPelicula.Runtime + ", " + infoPelicula.Genre + ", " + infoPelicula.Language + ".";
        tvInfo.setText(info);
        Picasso.get().load(infoPelicula.Poster).into(imgViewPoster);
    }
}