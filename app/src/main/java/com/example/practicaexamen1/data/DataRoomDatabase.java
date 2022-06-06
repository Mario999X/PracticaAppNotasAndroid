package com.example.practicaexamen1.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotaEntity.class}, version = 1)
public abstract class DataRoomDatabase extends RoomDatabase {

    private static String DATABASE_NAME = "basededatos";

    public abstract DataDao dataDao();

    private static volatile DataRoomDatabase INSTANCE;

    public synchronized static DataRoomDatabase getInstance(final Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DataRoomDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
