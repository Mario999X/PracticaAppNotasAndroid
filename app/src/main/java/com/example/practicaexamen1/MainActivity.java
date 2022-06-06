package com.example.practicaexamen1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicaexamen1.data.DataRoomDatabase;
import com.example.practicaexamen1.data.NotaEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextNota;
    Button btnAgregar, btnReset;
    RecyclerView recycler;

    List<NotaEntity> notaEntityList = new ArrayList<>();
    LinearLayoutManager lm;
    DataRoomDatabase database;
    AdapterRecyclerView adapter;

    NotaEntity nEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DataRoomDatabase.getInstance(this);
        enlazarComponentes();

        actualizarLista();

    }

    private void enlazarComponentes(){
        editTextNota = findViewById(R.id.editTextNota);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnReset = findViewById(R.id.btnReset);
        recycler = findViewById(R.id.recycler);

        btnAgregar.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    private void mostrarLista(List<NotaEntity> dl){

        lm = new LinearLayoutManager(this);
        recycler.setLayoutManager(lm);

        adapter = new AdapterRecyclerView(dl, this);
        recycler.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAgregar:
                nEntity = new NotaEntity();

                nEntity.setTexto(editTextNota.getText().toString());

                long resultado = database.dataDao().insert(nEntity);
                Log.i("insert() = ", "" + resultado); // Comprobacion

                actualizarLista();
                Toast.makeText(this, "Nota Añadida", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnReset:
                database.dataDao().resetear(notaEntityList);
                actualizarLista();
                Toast.makeText(this, "Eliminación Total Realizada", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarLista(){
        notaEntityList = database.dataDao().mostrarTodo();
        mostrarLista(notaEntityList);
    }
}