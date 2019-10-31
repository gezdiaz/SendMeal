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
    @ColumnInfo(name = "ID_PEDIDO_FK") private String idPedido;
    @Embedded() private Plato plato;

    public ItemPedido() {
        precioItem = 0.0;
        cantidad = 0;
    }
    @Ignore
    public ItemPedido(int id, Plato plato, int cantidad, double precioItem) {
        this.id = id;
        this.plato = plato;
        this.cantidad = cantidad;
        this.precioItem = precioItem;
    }

    public void calcularPrecio(){
        if(plato != null){
            precioItem = plato.getPrecioOferta()*cantidad;
        }else {
            precioItem = 0;
        }
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
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
                ", plato=" + plato +
                '}';
    }
}
