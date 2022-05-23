package com.example.asistencia_control;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarSalidaActivity extends AppCompatActivity {

    private EditText salida;
    private Button reg_salida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_salida);

        salida=findViewById(R.id.txtRegSalida);
        reg_salida=findViewById(R.id.regSalida);
        salida.setText(getIntent().getStringExtra("cedula"));

        reg_salida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(RegistrarSalidaActivity.this);
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
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult enter = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(enter != null){
            if(enter.getContents() == null){
                Toast.makeText(this, "Lector Cancelado", Toast.LENGTH_LONG).show();
            }else {
                salida.setText(enter.getContents());
                reg_salida();
            }
        }else {
            Toast.makeText(this, "Oops al parecer no se ha detectado ningun codigo", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void reg_salida(){
        DBHelper admin = new DBHelper(this, "control_asistencia", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String parameter = salida.getText().toString();

        ContentValues agregar = new ContentValues();
        agregar.put("salida", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        long cant=db.update("lista_asistencia",agregar,"cedula=" +parameter,null);
        if (cant == -1) {
            Toast.makeText(this, "Salida no Registrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Salida Registrada", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    public void consutalEntradahoy(){
        DBHelper admin = new DBHelper(this,"control_asistencia",null,1);
        SQLiteDatabase db=admin.getReadableDatabase();
        String[] parameter={salida.getText().toString()};
        String fecha = new SimpleDateFormat("yyyy,MM,dd").format(new Date());

        try{
            Cursor fila=db.rawQuery("SELECT fecha FROM lista_asistencia WHERE fecha= " +fecha+" AND cedula=? ",parameter);

            if(fila.moveToFirst()){
                Toast.makeText(this, "Cargando..."+fila.getString(1), Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Registro No encontrado", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){
            Toast.makeText(this, "Upps ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }
}