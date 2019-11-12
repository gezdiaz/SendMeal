package frsf.isi.dam.gtm.sendmeal.domain;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_PEDIDO")
public class ItemPedido {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID_ITEM_PEDIDO")
    private int id;

    private int cantidad;
    private double precioItem;
    @ColumnInfo(name = "ID_PEDIDO_FK") private int idPedido;

    private int idPlato;
    private String tituloPlato;
    transient private Plato plato;

    public ItemPedido() {
        precioItem = 0.0;
        cantidad = 0;
    }

    @Ignore
    public ItemPedido(int id, int idPlato, String tituloPlato, int cantidad, double precioItem) {
        this.id = id;
        this.idPlato = idPlato;
        this.tituloPlato = tituloPlato;
        this.cantidad = cantidad;
        this.precioItem = precioItem;
    }

    public void calcularPrecio(){
        if(plato != null){
            precioItem = plato.getPrecioOferta()*cantidad;
        }
    }

    public String getTituloPlato() {
        return tituloPlato;
    }

    public void setTituloPlato(String tituloPlato) {
        this.tituloPlato = tituloPlato;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
        this.tituloPlato = plato.getTitulo();
        this.idPlato = plato.getId();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioItem() {
        calcularPrecio();
        return precioItem;
    }

    public void setPrecioItem(double precioItem) {
        this.precioItem = precioItem;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioItem=" + precioItem +
                ", idPedido=" + idPedido +
                ", idPlato=" + idPlato +
                ", plato=" + plato +
                '}';
    }
}
