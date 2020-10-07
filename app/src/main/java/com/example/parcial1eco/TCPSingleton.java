package com.example.parcial1eco;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPSingleton extends Thread {

    public static TCPSingleton unicaInstancia;

    Socket socket;

    //writer y reader
    BufferedWriter writer;
    BufferedReader reader;

    //Observer
    OnMessageListener observer;

    //Metodo TCPSingleton
    private TCPSingleton() {

    }

    //Set los observadores de TCP
    public void setObserver(OnMessageListener observer) {
        this.observer = observer;
    }


    public static TCPSingleton getInstance() {

        if (unicaInstancia == null) {
            unicaInstancia = new TCPSingleton();
            unicaInstancia.start();
        }
        return unicaInstancia;
    }


    //Hilo conexion TCP
    public void run() {
        try {
            Log.e(">", "Esperando conexion");
            socket = new Socket("192.168.0.4", 5000);
            Log.e(">", "Conectamos con el server");

            //Input y Output
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            //Writer y reader
            writer = new BufferedWriter(new OutputStreamWriter(os));
            reader = new BufferedReader(new InputStreamReader(is));

            recibir();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //Metodo para recibir los mensajes
    public void recibir() {

        while (true) {
            try {

                String line = reader.readLine();


                if (line != null) {
                    observer.OnMessage(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //metodo para enviar mensajes
    public void enviar(String mensaje) {

        new Thread(
                () -> {
                    try {

                        if(mensaje!=null) {
                            writer.write(mensaje + "\n");
                            writer.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

        ).start();

    }
}
