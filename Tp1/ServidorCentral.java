package Tp1;
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorCentral {
    static private int puertoSCentral;
    static private String ipServidorHoroscopo;
    static private String ipServidorClima;
    static private int puertoServidorHoroscopo;
    static private int puertoServidorClima;
    static private Cache cache;
    static private ServerSocket ss;
    public static void main(String args[]) throws IOException {
        //args[0]=puertoSCentral args[1]=ipSH args[2]=puertoSH args[3]=ipSClima args[4]=puertoSClima    
        puertoSCentral=Integer.parseInt(args[0]);
        ipServidorHoroscopo=args[1];
        ipServidorClima=args[3];
        puertoServidorHoroscopo=Integer.parseInt(args[2]);
        puertoServidorClima=Integer.parseInt(args[4]);

/* 
        int puertoScentral=10578;
        String ipServidorHoroscopo="127.0.0.1";
        String ipServidorClima="127.0.0.1";
        int puertoServidorHoroscopo=20000;
        int puertoServidorClima=20001;
*/
        cache = new Cache();
        
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
