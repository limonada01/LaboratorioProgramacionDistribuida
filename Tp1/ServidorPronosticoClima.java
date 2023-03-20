package Tp1;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorPronosticoClima {
    
    public static void main(String args[]) throws IOException {
        ServerSocket ss;
        System.out.print("Inicializando servidor Pronostico Clima... ");
        try {
            ss = new ServerSocket(20001);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante en ServidorPronosticoClima: "+socket);
                ((ServidorPronosticoClimaHilo) new ServidorPronosticoClimaHilo(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
