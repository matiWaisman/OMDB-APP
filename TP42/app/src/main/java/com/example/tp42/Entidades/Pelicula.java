package com.example.tp42.Entidades;

public class Pelicula {
    public String  Title;
    public String  Year;
    public String  Genre;
    public String  Poster;
    public String Runtime;
    public String Language;
    public String Plot;


    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public Pelicula(String _title, String _year, String _genre, String _poster, String _runtime, String _language, String _plot){
        Title = _title;
        Year = _year;
        Genre = _genre;
        Poster = _poster;
        Runtime = _runtime;
        Language = _language;
        Plot = _plot;
    }


    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getGenre() {
        return Genre;
    }

    public void setType(String genre) {
        Genre = genre;
    }


    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
