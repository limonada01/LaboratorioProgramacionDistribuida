package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorHoroscopo {
    

    public static void main(String args[]) throws IOException {
        ServerSocket ss;
        System.out.print("Inicializando servidor Horoscopo ");
        try {
            ss = new ServerSocket(20000);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante en ServidorHoroscopo: "+socket);
                ((ServidorHoroscopoHilo) new ServidorHoroscopoHilo(socket, idSession)).start(); //Arranca los hilos
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
