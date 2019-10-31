package frsf.isi.dam.gtm.sendmeal.domain;

import androidx.room.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Plato implements Serializable {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precioPlato;

    private Double oferta;
    private Integer calorias;
    private boolean inOffer;

    public Plato() {
    }
    @Ignore
    public Plato(String titulo, String descripcion, Double precioPlato, Integer calorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precioPlato = precioPlato;
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

    public void setOferta(Double oferta) {
        this.oferta = oferta;
    }
    public Double getOferta(){
        return oferta;
    }
    public Double getPrecioOferta(){
        return precioPlato * (1 - oferta);
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

    public Double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Double precioPlato) {
        this.precioPlato = precioPlato;
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
                Objects.equals(precioPlato, plato.precioPlato) &&
                Objects.equals(calorias, plato.calorias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descripcion, precioPlato, calorias);
    }

    public Integer getPorcentajeOferta() {
        return ((Double) (oferta*100)).intValue();
    }

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPlato=" + precioPlato +
                ", oferta=" + oferta +
                ", calorias=" + calorias +
                ", inOffer=" + inOffer +
                '}';
    }
}
