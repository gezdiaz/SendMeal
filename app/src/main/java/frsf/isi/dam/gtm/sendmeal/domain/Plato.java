package frsf.isi.dam.gtm.sendmeal.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;

public class Plato implements Serializable {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio, oferta;
    private Integer calorias;
    private boolean inOffer;
    public static ArrayList<Plato> platos;

    public Plato() {
    }

    public Plato(String titulo, String descripcion, Double precio, Integer calorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
        this.inOffer = false;
        this.oferta = 0.0;
    }

    public boolean switchInOffer(Double off){
        inOffer=!inOffer;
        if(inOffer){
            oferta = off;
        }else{
            oferta = 0.0;
        }
        return inOffer;
    }

    public Double getPrecioOferta(){
        return precio - (oferta * precio);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getInOffer() {
        return inOffer;
    }

    public void setInOffer(boolean inOffer) {
        this.inOffer = inOffer;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plato plato = (Plato) o;
        return Objects.equals(id, plato.id) &&
                Objects.equals(titulo, plato.titulo) &&
                Objects.equals(descripcion, plato.descripcion) &&
                Objects.equals(precio, plato.precio) &&
                Objects.equals(calorias, plato.calorias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descripcion, precio, calorias);
    }

    public Integer getOferta() {
        return ((Double) (oferta*100)).intValue();
    }

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", oferta=" + oferta +
                ", calorias=" + calorias +
                ", inOffer=" + inOffer +
                '}';
    }
}
