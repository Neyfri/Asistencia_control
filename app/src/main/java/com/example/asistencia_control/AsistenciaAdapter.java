package com.example.asistencia_control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.asistenciaView> {

    private List<Asistencia>asistenciaList = new ArrayList<>();
    private Context context;

    public AsistenciaAdapter(Context context, ArrayList<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
        this.context = context;
        this.asistenciaList = asistenciaList;
    }

    @NonNull
    @Override
    public asistenciaView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mostrar_asistencia,parent,false);
        return new asistenciaView(view);
    }

    @Override
    public void onBindViewHolder(asistenciaView holder, int position) {
        Asistencia asistencia = asistenciaList.get(position);
        asistenciaView.txtfecha.setText(asistencia.getFecha());
        asistenciaView.txtcedula.setText(String.valueOf(asistencia.getCedula()));
        asistenciaView.txtentrada.setText(asistencia.getEntrada());
        asistenciaView.txtsalida.setText(asistencia.getSalida());


    }

    @Override
    public int getItemCount() {
        return asistenciaList.size();
    }

    public void agregarAsistencia(Asistencia asistencia){
        asistenciaList.add(asistencia);
        this.notifyDataSetChanged();
    }

    public static class asistenciaView extends RecyclerView.ViewHolder{

        private static TextView txtfecha;
        private static TextView txtcedula;
        private static TextView txtentrada;
        private static TextView txtsalida;
        public asistenciaView(@NonNull View itemView) {
            super(itemView);

            txtfecha=itemView.findViewById(R.id.asi_fecha);
            txtcedula=itemView.findViewById(R.id.asi_ced);
            txtentrada=itemView.findViewById(R.id.asi_entrada);
            txtsalida=itemView.findViewById(R.id.asi_salida);
        }
    }
}
