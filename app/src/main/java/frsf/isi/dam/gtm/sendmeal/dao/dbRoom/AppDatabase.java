package frsf.isi.dam.gtm.sendmeal.dao.dbRoom;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import frsf.isi.dam.gtm.sendmeal.dao.dbRoom.converters.Converters;
import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

@Database(entities = {Pedido.class, ItemPedido.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PedidoDao pedidoDao();
    public abstract ItemPedidoDao itemPedidoDao();
}
