package com.example.practicaexamen1.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Insert
    public long insert(NotaEntity n);

    @Delete
    public void borrarNota(NotaEntity n);

    @Delete
    public void resetear(List<NotaEntity> nList);

    @Query("UPDATE datos_tabla SET texto = :sText WHERE id = :sId")
    public void update(int sId, String sText);

    @Query("SELECT * FROM datos_tabla")
    List<NotaEntity> mostrarTodo();
}
