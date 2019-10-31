package frsf.isi.dam.gtm.sendmeal.dao.dbRoom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import frsf.isi.dam.gtm.sendmeal.domain.ItemsPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

@Database(entities = {Pedido.class, ItemsPedido.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PedidoDao pedidoDao();
    public abstract ItemPedidoDao itemPedidoDao();
}
