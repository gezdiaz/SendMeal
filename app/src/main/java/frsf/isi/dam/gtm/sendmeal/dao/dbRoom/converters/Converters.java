package frsf.isi.dam.gtm.sendmeal.dao.dbRoom.converters;

import androidx.room.TypeConverter;

import java.util.Date;

import frsf.isi.dam.gtm.sendmeal.domain.EstadoPedido;

public class Converters {
    @TypeConverter
    public static Date dateFromTimeStamp(Long timestamp){
        return timestamp == null? null : new Date(timestamp);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
    @TypeConverter
    public static EstadoPedido estadoFromString(String name){
        return name == null ? null : EstadoPedido.valueOf(name);
    }
    @TypeConverter
    public static String estadoToString(EstadoPedido estado){
        return estado == null ? null : estado.name();
    }
}
