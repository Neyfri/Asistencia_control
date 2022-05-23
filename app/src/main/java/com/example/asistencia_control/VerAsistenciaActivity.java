package com.example.asistencia_control;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class VerAsistenciaActivity extends AppCompatActivity {

    private RecyclerView recordRv;
    private ArrayList<Asistencia> asistenciaArrayList;
    private AsistenciaAdapter asistenciaAdapter;
    private DBHelper sqlite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_asistencia);

        recordRv=findViewById(R.id.recordsRv);
        asistenciaArrayList = new ArrayList<>();
        sqlite = new DBHelper(this,"control_asistencia",null,1);

        asistenciaAdapter = new AsistenciaAdapter(this,asistenciaArrayList);

        RecyclerView recyclerView = findViewById(R.id.recordsRv);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(asistenciaAdapter);
        mostrarDatos();
    }

    public void mostrarDatos(){
        SQLiteDatabase db = sqlite.getReadableDatabase();

        Asistencia asistencia = null;

        Cursor fila = db.rawQuery("SELECT * FROM lista_asistencia",null);
        while (fila.moveToNext()){
            asistencia = new Asistencia();
            asistencia.setFecha(fila.getString(1));
            asistencia.setCedula(fila.getInt(2));
            asistencia.setEntrada(fila.getString(3));
            asistencia.setSalida(fila.getString(4));
            asistenciaAdapter.agregarAsistencia(asistencia);


        }
    }
}