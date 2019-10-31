package frsf.isi.dam.gtm.sendmeal.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "PEDIDO")
public class Pedido {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "ID_PEDIDO")
    private String id;

    private Date fecha;
    private EstadoPedido estado;
    private double latitud;
    private double longitud;

    public static class PedidoConItems{
        @Embedded public Pedido pedido;
        @Relation(parentColumn = "ID_PEDIDO", entityColumn = "ID_PEDIDO_FK", entity = ItemPedido.class)
        public List<ItemPedido> itemsPedido;

        public PedidoConItems(){}

        @Override
        public String toString() {
            return "PedidoConItems{" +
                    "pedido=" + pedido +
                    ", itemsPedido=" + itemsPedido +
                    '}';
        }
    }

    @Ignore
    private List<ItemPedido> itemsPedido;


    public Pedido() {
        id = UUID.randomUUID().toString();
    }
    @Ignore
    public Pedido(Date fecha, EstadoPedido estado, double latitud, double longitud) {
        id = UUID.randomUUID().toString();
        this.fecha = fecha;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getPrecioTotal(){
        double precio = 0.0;
        for(ItemPedido i: itemsPedido){
            precio = precio + i.getPrecioItem();
        }
        return precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<ItemPedido> getItemsPedido() {
        return itemsPedido;
    }

    public void setItemsPedido(List<ItemPedido> itemsPedido) {
        this.itemsPedido = itemsPedido;
        for(ItemPedido i: this.itemsPedido ){
            i.setIdPedido(this.id);
        }
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
        return Double.compare(pedido.latitud, latitud) == 0 &&
                Double.compare(pedido.longitud, longitud) == 0 &&
                Objects.equals(id, pedido.id) &&
                Objects.equals(fecha, pedido.fecha) &&
                estado == pedido.estado &&
                Objects.equals(itemsPedido, pedido.itemsPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, estado, latitud, longitud, itemsPedido);
    }
}


