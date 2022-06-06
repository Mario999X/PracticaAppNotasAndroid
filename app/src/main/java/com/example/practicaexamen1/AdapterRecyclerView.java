package com.example.practicaexamen1;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaexamen1.data.DataRoomDatabase;
import com.example.practicaexamen1.data.NotaEntity;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

    private List<NotaEntity> notaEntityList;
    private Activity context;
    private DataRoomDatabase database;

    public AdapterRecyclerView(List<NotaEntity> notaEntityList, Activity context) {
        this.notaEntityList = notaEntityList;
        this.context = context;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolder holder, int position) {

        NotaEntity item = notaEntityList.get(position);

        holder.textViewNota.setText(item.getTexto());

        database = DataRoomDatabase.getInstance(context);

        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotaEntity n = notaEntityList.get(holder.getAdapterPosition());
                int sId = n.getId();
                String sText = n.getTexto();

                // Crear dialogo
                Dialog dialog = new Dialog(context);
                // Construir el contenido
                dialog.setContentView(R.layout.dialogo_update);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                // Activar dialogo
                dialog.show();
                dialog.getWindow().setAttributes(lp);

                // Inicializar variables
                EditText editTextNotaUpdate = dialog.findViewById(R.id.editTextNotaUpdate);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
                // Poner texto seleccionado
                editTextNotaUpdate.setText(sText);

                // Listener para btnUpdate del dialogo
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // cerrar dialogo
                        dialog.dismiss();
                        //obtener modificacion del texto
                        String uText = editTextNotaUpdate.getText().toString().trim();
                        // Modificar BBDD
                        database.dataDao().update(sId, uText);
                        notaEntityList.clear();
                        notaEntityList.addAll(database.dataDao().mostrarTodo());
                        Toast.makeText(context, "Nota Editada", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();

                    }
                });

            }
        });

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                NotaEntity nEntity = notaEntityList.get(position);
                database.dataDao().borrarNota(nEntity);

                notaEntityList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Nota Eliminada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notaEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNota;
        ImageView imageEdit, imageDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNota = itemView.findViewById(R.id.textViewNota);
            imageEdit = itemView.findViewById(R.id.imageEdit);
            imageDelete = itemView.findViewById(R.id.imageDelete);
        }
    }
}
