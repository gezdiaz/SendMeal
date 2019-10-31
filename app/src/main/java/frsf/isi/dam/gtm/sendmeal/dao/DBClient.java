package frsf.isi.dam.gtm.sendmeal.dao;

import android.content.Context;

import androidx.room.Room;

import frsf.isi.dam.gtm.sendmeal.dao.dbRoom.AppDatabase;

public class DBClient {
    private static DBClient DB = null;

    private AppDatabase roomDB;

    private DBClient(Context context){
        roomDB = Room.databaseBuilder(context, AppDatabase.class, "pedidosDB").allowMainThreadQueries().build();
    }

    public synchronized static DBClient getInstance(Context context){
        if(DB == null){
            DB = new DBClient(context);
        }
        return DB;
    }

    public AppDatabase getRoomDB(){
        return roomDB;
    }
}
