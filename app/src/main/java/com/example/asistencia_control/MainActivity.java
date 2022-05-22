package com.example.asistencia_control;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button entrada,salida,registro;
    EditText txtResultadoScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrada=findViewById(R.id.entrada);
        salida=findViewById(R.id.salida);
        registro=findViewById(R.id.registro);
        txtResultadoScan=findViewById(R.id.txtResultadoScan);

        entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector de Codigos");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setOrientationLocked(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(Capture.class);
                integrator.initiateScan();
            }
        });
        salida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegistrarSalidaActivity.class);
                i.putExtra("cedula",txtResultadoScan.getText().toString());
                startActivity(i);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult enter = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(enter != null){
            if(enter.getContents() == null){
                Toast.makeText(this, "Lector Cancelado", Toast.LENGTH_LONG).show();
            }else {
                consutalCedula();
                txtResultadoScan.setText(enter.getContents());
            }
        }else {
            Toast.makeText(this, "Oops al parecer no se ha detectado ningun codigo", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void reg_Particiapantes(View view){
        Intent i = new Intent(this,RegistroParticipantesActivity.class);
        i.putExtra("cedula",txtResultadoScan.getText().toString());
        startActivity(i);
    }

    public void consutalCedula(){
        DBHelper admin = new DBHelper(this,"control_asistencia",null,1);
        SQLiteDatabase db=admin.getReadableDatabase();
        String[] parameter={txtResultadoScan.getText().toString()};

            Cursor fila = db.rawQuery("SELECT nombre FROM reg_participantes WHERE cedula=? ", parameter);

            if (fila.moveToFirst()) {
                Toast.makeText(this, "Registrando salida..", Toast.LENGTH_SHORT).show();
                reg_entrada();
            } else {
                Toast.makeText(this, "Registro No encontrado"+parameter.toString(), Toast.LENGTH_SHORT).show();

            }

    }
    public void reg_entrada() {
        DBHelper admin = new DBHelper(this, "control_asistencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String[] parameter = {txtResultadoScan.getText().toString()};

        Cursor fila = db.rawQuery("SELECT cedula FROM reg_participantes WHERE cedula=? ", parameter);
        if (fila.moveToFirst()) {
            ContentValues agregar = new ContentValues();
            agregar.put("fecha", new SimpleDateFormat("yyyy,MM,dd").format(new Date()));
            agregar.put("cedula", txtResultadoScan.getText().toString());
            agregar.put("entrada", new SimpleDateFormat("HH:mm:ss").format(new Date()));
            agregar.put("presencia", "Completa");

            long cant = db.insert("lista_asistencia", null, agregar);
            if (cant == -1) {
                Toast.makeText(this, "Registrando Salida..", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Entrada Registrada", Toast.LENGTH_SHORT).show();
                db.close();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Aviso!");
            builder.setMessage("Esta cedula no existe en el registro Desea Registrarse");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent o = new Intent(MainActivity.this,RegistroParticipantesActivity.class);
                    o.putExtra("cedula",txtResultadoScan.getText().toString());
                    startActivity(o);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Importante!");
                    builder.setMessage("Esta cedula no fue encontrada en el Registro de estudiante, Debe Registrar al estudiate antes pulse el boton de Registrar");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //cerrar caja de mensaje
                            dialogInterface.dismiss();
                        }
                    });
                }
            });
            builder.show();
        }
    }
}