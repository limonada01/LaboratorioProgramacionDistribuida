package Tp1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    static private String ipHost;
    static private int puertoDestino;
    static private Socket sk;
    static private DataOutputStream dos;
    static private DataInputStream dis;

    public static void main(String[] args) {
        //args[0]=cantidadDeClientes args[1]=ipHost args[2]=puertoDestino
        ipHost=args[1];
        puertoDestino=Integer.parseInt(args[2]);

        Scanner sc= new Scanner(System.in);
        boolean repetir=true;
        while(repetir) {
            consulta();
            System.out.println("Hacer otra consulta? (Ingrese 1: Si, 0: No)");
            repetir = sc.nextInt()==1;
        }
    }

    public static void consulta(){
        try {
            sk = new Socket(ipHost,puertoDestino);
            dos = new DataOutputStream(sk.getOutputStream());//buffer de salida
            dis = new DataInputStream(sk.getInputStream());//buffer de entrada
            String consulta = solicitarDatos();
            System.out.println("Cliente consulta horoscopo y clima: "+consulta);
            dos.writeUTF(consulta);

            String respuesta="";

            respuesta = dis.readUTF();
            System.out.println("Servidor devuelve: " +respuesta+ ". como respuesta");
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static String solicitarDatos(){
        Scanner sc= new Scanner(System.in);
        String fecha="";
        System.out.println("/////////DATOS HOROSCOPO/////////");
        System.out.println("Ingrese un signo del horoscopo");
        String signo = sc.nextLine();
        System.out.println("/////////DATOS FECHA PARA SABER EL CLIMA/////////");
        System.out.println("Ingrese una fecha con formato dd/mm/aaaa");
        fecha=sc.nextLine();
        return signo+"-"+fecha;
    }
}

