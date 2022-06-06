package com.example.practicaexamen1.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "datos_tabla")
public class NotaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "texto")
    private String texto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
