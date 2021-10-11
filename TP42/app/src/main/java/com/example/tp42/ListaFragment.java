package com.example.tp42;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tp42.Entidades.ResultadoBusqueda;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;


public class ListaFragment extends Fragment {
    private String link = "https://www.omdbapi.com/?apikey=1179e855&s=";
    private String peliculaAbuscar;
    ListView lvDatos;
    private List<ResultadoBusqueda> ArrayBusquedas = new ArrayList<ResultadoBusqueda>();

    public ListaFragment(String pelicula) {
        // Required empty public constructor
        peliculaAbuscar = pelicula;
    }

    private void ObtenerReferencias(View v){

        lvDatos = (ListView) v.findViewById(R.id.listView);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista,container,false);
        ObtenerReferencias(v);
        Log.d("pelicula", peliculaAbuscar);
        Tarea();

        return v;
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
            String url = link + peliculaAbuscar;
            Log.d("url", url);
            BufferedReader responseReader;
            String responseLine, strResultado = "";
            StringBuilder sbResponse;
            String toaster = "";
            Context context;
            context = getContext();
            int duration = Toast.LENGTH_SHORT;
            try {
                strApiUrl = new URL(url);
                miConexion = (HttpURLConnection) strApiUrl.openConnection();
                miConexion.setRequestMethod("GET");
                if (miConexion.getResponseCode() == 200) {
                    context = context.getApplicationContext();


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
            cargarListView();
            setearListeners();
        }
    }




    private void Tarea(){
        TareaAsincronica miTarea = new TareaAsincronica();
        miTarea.execute();
    }




    private void parsearStringToGson(String resultadoAParsear){
        String strTitle, year, imdbId;
        JsonObject convertedObject = new Gson().fromJson(resultadoAParsear,JsonObject.class);
        JsonArray searhJsonArray = convertedObject.getAsJsonArray("Search");
        JsonObject objectPelicula;


        for(int i = 0; i < searhJsonArray.size(); i++){
            objectPelicula = searhJsonArray.get(i).getAsJsonObject();
            strTitle = objectPelicula.get("Title").getAsString();
            year = objectPelicula.get("Year").getAsString();
            imdbId = objectPelicula.get("imdbID").getAsString();
            sumarObjectABusquedas(strTitle,imdbId, year);
        }
    }

    private void sumarObjectABusquedas(String title, String imbdId, String year){
        String yearEspaciado = " ";
        yearEspaciado += year;
        ResultadoBusqueda object = new ResultadoBusqueda(title, yearEspaciado, imbdId);
        ArrayBusquedas.add(object);
        Log.d("Titulo", object._titulo);
        Log.d("Year", object._year);
        Log.d("Id", object._imdbId);
    }

    private void cargarListView(){
        ArrayAdapter<ResultadoBusqueda>     adapter;
        adapter = new ArrayAdapter<ResultadoBusqueda>(getContext(), android.R.layout.simple_list_item_1,ArrayBusquedas);
        lvDatos.setAdapter(adapter);
    }

    private void setearListeners(){
        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirFragment(position);
            }
        });
    }

    private void abrirFragment(int posicion){
        String id = ArrayBusquedas.get(posicion)._imdbId;
        ResultadoFragment fgResultado = new ResultadoFragment(id);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentcontainer, fgResultado);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}