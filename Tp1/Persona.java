package Tp1;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;


public class Persona extends Thread {

    static String[] signos={"Sagitario","Leo","Capricornio"};
    static String[] fechas={"11/05/2023","12/05/2023","13/05/2023"};

    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    public Persona(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        try {
            sk = new Socket("127.0.0.1", 10578);
            dos = new DataOutputStream(sk.getOutputStream());//buffer de salida
            dis = new DataInputStream(sk.getInputStream());//buffer de entrada
            
            String consulta=signos[(int)Math.random()*signos.length]+"-"+fechas[(int)Math.random()*fechas.length];
            
            System.out.println(id + " Consulta horoscopo y clima: "+consulta);
             
            dos.writeUTF(consulta);
            
            String respuesta="";
            respuesta = dis.readUTF();
            System.out.println(id + " Servidor devuelve: " + respuesta);
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
}

