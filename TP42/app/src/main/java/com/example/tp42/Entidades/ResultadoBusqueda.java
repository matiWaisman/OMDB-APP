package com.example.tp42.Entidades;

import com.google.gson.annotations.SerializedName;

public class ResultadoBusqueda {
    @SerializedName("Title")
    public String _titulo;
    @SerializedName("Year")
    public String _year;
    @SerializedName("imdbID")
    public String _imdbId;
    public ResultadoBusqueda(String Titulo, String Year, String IMDBId){
        _titulo = Titulo;
        _year = Year;
        _imdbId = IMDBId;
    }

    public String get_titulo() {
        return _titulo;
    }

    public void set_titulo(String _titulo) {
        this._titulo = _titulo;
    }

    public String get_imdbId() {
        return _imdbId;
    }

    public void set_imdbId(String _imdbId) {
        this._imdbId = _imdbId;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String _year) {
        this._year = _year;
    }


    @Override
    public String toString() {
        String tA = "";
        tA += _titulo + String.valueOf(_year);
        return tA;
    }
}
