package frsf.isi.dam.gtm.sendmeal.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

@Entity()
public class Pedido {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID_PEDIDO")
    private int id;

    private Date fecha;
    private EstadoPedido estado;
    private double latitud;
    private double longitud;
    @Relation(parentColumn = "ID_PEDIDO", entityColumn = "ITEM_ITEMS_PEDIDO", entity = ItemsPedido.class)
    private List<ItemsPedido> itemsPedido;


    public Pedido() {

    }
    public Pedido(Date fecha, EstadoPedido estado, double latitud, double longitud) {
        this.fecha = fecha;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getPrecioTotal(){
        double precio = 0.0;
        for(ItemsPedido i: itemsPedido){
            precio = precio + i.getPrecio();
        }
        return precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public List<ItemsPedido> getItemsPedido() {
        return itemsPedido;
    }

    public void setItemsPedido(List<ItemsPedido> itemsPedido) {
        this.itemsPedido = itemsPedido;

    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", itemsPedido=" + itemsPedido +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;

        if (id != pedido.id) return false;
        if (Double.compare(pedido.latitud, latitud) != 0) return false;
        if (Double.compare(pedido.longitud, longitud) != 0) return false;
        if (fecha != null ? !fecha.equals(pedido.fecha) : pedido.fecha != null) return false;
        if (estado != pedido.estado) return false;
        return itemsPedido != null ? itemsPedido.equals(pedido.itemsPedido) : pedido.itemsPedido == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        temp = Double.doubleToLongBits(latitud);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitud);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (itemsPedido != null ? itemsPedido.hashCode() : 0);
        return result;
    }
}
