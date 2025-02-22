package com.example.xpiration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;

import entidades.Lotes;

public class VerLoteActivity extends AppCompatActivity {
    //******************************* Elementos en pantalla ****************************************
    EditText fecha;
    Button guardar;
    EditText nombre;
    TextView notiNaranja,notiRoja;
    ImageView editar;
    ImageView borrar;
    //******************************* Definicion de Parametros *************************************
    Context context;
    int id;
    Lotes lotes;
    Connection con;  //Coneccion con la Base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lote);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        context = this;
        //************************ Declaracion y seteo de Elementos *******************************
        nombre = findViewById(R.id.NombreEditLote);
        fecha = findViewById(R.id.FechaCaducidadEdit);
        notiNaranja = findViewById(R.id.notiNaranjaEdit);
        notiRoja = findViewById(R.id.notiRojaEdit);
        guardar = findViewById(R.id.BtnGuardar);
        borrar = findViewById(R.id.iconBorrar);

        //En la vista anterior se envia un parametro al generar la vista, las siguientes lineas recuperan este dato
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }
        //Toast.makeText(context, "lote con id = " + id, Toast.LENGTH_LONG).show();

        lotes = new Lotes().verLote(id);

        //Asignacion de parametros a los elementos que se ven por pantalla
        if(lotes != null){
            nombre.setText(lotes.getLOTE_NOMBRE());
            nombre.setInputType(InputType.TYPE_NULL);
            nombre.setFocusable(false);
            String modificado = lotes.getLOTE_FECHA_CADUCIDAD().toString().replace("-","/");
            fecha.setText(modificado);
            fecha.setInputType(InputType.TYPE_NULL);
            fecha.setFocusable(false);
            notiNaranja.setText(lotes.getLOTE_NOTIFICACION_NARANJA()+"");
            notiNaranja.setInputType(InputType.TYPE_NULL);
            notiNaranja.setFocusable(false);
            notiRoja.setText(lotes.getLOTE_NOTIFICACION_ROJA()+"");
            notiRoja.setInputType(InputType.TYPE_NULL);
            notiRoja.setFocusable(false);
            getSupportActionBar().setTitle("Lote de "+lotes.getPRODUCTO_NOMBRE());
            guardar.setVisibility(View.INVISIBLE);


        }
//******************************** Boton de Borrar *************************************************
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerLoteActivity.this);
                builder.setMessage("¿Desea eliminar este lote?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        lotes.Borrar(id);
                        Intent intent = new Intent(VerLoteActivity.this,MainActivity.class);
                        intent.putExtra("ID",id);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

//******************************** Boton de Editar *************************************************
        editar=findViewById(R.id.IconEditar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerLoteActivity.this,EditarLoteActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });



    }

}