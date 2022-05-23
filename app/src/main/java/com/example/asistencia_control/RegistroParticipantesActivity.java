package com.example.asistencia_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroParticipantesActivity extends AppCompatActivity {

    EditText cedula,nombre,apellido,activo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_participantes);

        cedula=findViewById(R.id.reg_cedula);
        nombre=findViewById(R.id.reg_nombre);
        apellido=findViewById(R.id.reg_apellido);
        activo=findViewById(R.id.reg_activo);

        cedula.setText(getIntent().getStringExtra("cedula"));
    }
    public void registrarParticipante(View view) {
        DBHelper admin = new DBHelper(this, "control_asistencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String[] parameter = {cedula.getText().toString()};

        Cursor repetido = db.rawQuery("SELECT cedula FROM reg_participantes WHERE cedula=? ", parameter);
        if (repetido.moveToFirst()) {
            Toast.makeText(this, "Este Registro ya existe", Toast.LENGTH_SHORT).show();
        } else {
            String ced = cedula.getText().toString();
            String nom = nombre.getText().toString();
            String ape = apellido.getText().toString();
            String act = activo.getText().toString();


            ContentValues agregar = new ContentValues();
            agregar.put("cedula", ced);
            agregar.put("nombre", nom);
            agregar.put("apellido", ape);
            agregar.put("activo", act);

            long cant = db.insert("reg_participantes", null, agregar);
            if (cant == -1) {
                Toast.makeText(this, "Error al Registrar", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                db.close();
            }
        }
    }
}