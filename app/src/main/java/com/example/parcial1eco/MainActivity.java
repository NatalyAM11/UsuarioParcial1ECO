package com.example.parcial1eco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMessageListener {


    TCPSingleton tcp;
    private EditText name;
    private Button bListo;
    int x,y,r,g,b;

    String nombre;

    //Json
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.Name);
        bListo=findViewById(R.id.bListo);

        //Datos iniciales del avatar del jugador, antes de que mueva el avatar y le cambie el color
        x=400;
        y=250;
        r=63;
        g=81;
        b=181;

        tcp= TCPSingleton.getInstance();

        //Me aseguro de no setear el observer hasta que todos los datos del avatar esten
        if(nombre!= null) {
            tcp.setObserver(this);
        }

        bListo.setOnClickListener(this);

    }


    public void onClick( View view) {

        //Obtengo el nombre que pone el usuario
        nombre =name.getText().toString();
        Log.e(">",nombre);

        //No lo dejo pasar si no lleno el campo de nombre
        if (nombre.trim().isEmpty()){
            Toast.makeText(this, "Debe llenar el campo", Toast.LENGTH_LONG).show();
            return;
        }


        //Creo el json
        Gson gson= new Gson();

        //Creo el avatar del jugador
        Jugador jugador= new Jugador(x,y,nombre,r,g,b);

        //Lo paso a json
        String json=gson.toJson(jugador);

        //Envio el json
        tcp.enviar(json);

        //Cambio de pantalla y envio todos los datos del avatar a la pantalla control
        Intent i= new Intent( this,Control.class);
        i.putExtra("x",x);
        i.putExtra("y",y);
        i.putExtra("r",r);
        i.putExtra("g",g);
        i.putExtra("b",b);
        i.putExtra("nombre",nombre);
        startActivity(i);

        Log.e(">>>", json);


    }


    @Override
    public void OnMessage(String mensaje) {

        mensaje=mensaje;
    }
}