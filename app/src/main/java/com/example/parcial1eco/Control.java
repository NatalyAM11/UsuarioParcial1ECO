package com.example.parcial1eco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

public class Control extends AppCompatActivity implements OnMessageListener, View.OnTouchListener, View.OnClickListener{


    private Button bArriba,bAbajo,bDerecha,bIzquierda,bCambio;
    TCPSingleton tcp;

    //Datos del avatar
    int x,y,r,g,b;
    String nombre;

    //Booleano que controla el onTouch
    boolean buttonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        //recibo los datos de la pantalla 1
        x=getIntent().getExtras().getInt("x");
        y=getIntent().getExtras().getInt("y");
        r=getIntent().getExtras().getInt("r");
        g=getIntent().getExtras().getInt("g");
        b=getIntent().getExtras().getInt("b");
        nombre=getIntent().getExtras().getString("nombre");

        bArriba=findViewById(R.id.bArriba);
        bAbajo=findViewById(R.id.bAbajo);
        bDerecha=findViewById(R.id.bDerecha);
        bIzquierda=findViewById(R.id.bIzquierda);
        bCambio=findViewById(R.id.bCambio);


        tcp=TCPSingleton.getInstance();

        tcp.setObserver(this);

        bIzquierda.setOnTouchListener(this);
        bDerecha.setOnTouchListener(this);
        bArriba.setOnTouchListener(this);
        bAbajo.setOnTouchListener(this);


        bCambio.setOnClickListener(this);

    }

    //Controlo el boton del cambio de color
    public void onClick(View view) {

        //Cambio de color por numeros random
        r=(int) Math.floor((Math.random() * 250 + 1));
        g=(int) Math.floor((Math.random() * 250 + 1));
        b=(int) Math.floor((Math.random() * 250 + 1));

        //creo el json
        Gson gson = new Gson();
        //Creo el jugador
        Jugador jugador = new Jugador(x, y, nombre, r, g, b);
        //Lo paso a json
        String json = gson.toJson(jugador);

        //Envio el json
        tcp.enviar(json);

    }


    //Controlo lo botones del movimiento
    public boolean onTouch(View view, MotionEvent event) {

        //controlo cada evento de los botones con el boolean
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                buttonPressed=true;
                break;

            case MotionEvent.ACTION_UP:
                buttonPressed=false;
                break;
        }

        if(buttonPressed==true){

            new Thread(
                    ()->{
                        while(buttonPressed) {
                            switch (view.getId()) {
                                case R.id.bDerecha:
                                    x = x + 8;
                                    break;

                                case R.id.bIzquierda:

                                    x = x - 8;
                                    break;

                                case R.id.bArriba:
                                    y = y - 8;
                                    break;
                                case R.id.bAbajo:
                                    y = y + 8;
                                    break;
                            }

                            //Creo el json
                            Gson gson = new Gson();
                            //creo el jugador
                            Jugador jugador = new Jugador(x, y, nombre, r, g, b);
                            //Lo paso a json
                            String json = gson.toJson(jugador);

                            //Envio el json
                            tcp.enviar(json);

                            Log.e(">>>", json);

                            //controlo el tiempo con el que los botones van a responder
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }

        return false;
    }


    @Override
    public void OnMessage(String mensaje) {

    }

}