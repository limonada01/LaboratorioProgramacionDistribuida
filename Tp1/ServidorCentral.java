package Tp1;
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorCentral {
    public static void main(String args[]) throws IOException {
        //args[0]=puertoSCentral args[1]=ipSH args[2]=puertoSH args[3]=ipSClima args[4]=puertoSClima
        //System.out.println("------> "+args[0]);
        
        int puertoSCentral=Integer.parseInt(args[0]);
        String ipServidorHoroscopo=args[1];
        //System.out.println("holaa");
        String ipServidorClima=args[3];
        int puertoServidorHoroscopo=Integer.parseInt(args[2]);
        int puertoServidorClima=Integer.parseInt(args[4]);

/* 
        int puertoScentral=10578;
        String ipServidorHoroscopo="127.0.0.1";
        //System.out.println("holaa");
        String ipServidorClima="127.0.0.1";
        int puertoServidorHoroscopo=20000;
        int puertoServidorClima=20001;
*/
        Cache cache = new Cache();
        ServerSocket ss;
        System.out.print("Inicializando servidor Central ");
        try {
            ss = new ServerSocket(puertoSCentral);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante en servidor central: "+socket);
                ((ServidorCentralHilo) new ServidorCentralHilo(socket, idSession, cache,ipServidorHoroscopo,ipServidorClima,puertoServidorHoroscopo,puertoServidorClima)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR en SC");
        }
    }
}
