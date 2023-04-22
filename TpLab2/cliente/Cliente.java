package TpLab2.cliente;

import java.rmi.Naming;
import java.util.Scanner;

import TpLab2.servidor_central.Servicio_Consulta;

public class Cliente {
    
    public Cliente(){
        try {	
            Servicio_Consulta servicio_consulta = (Servicio_Consulta)Naming.lookup ("//localhost:54323/Servicio_Consulta_Imp");
            Scanner sc= new Scanner(System.in);
            boolean repetir=true;
            while(repetir) {
                String consulta = solicitarDatos(); 
                System.out.println ("Resultado Consulta: "+servicio_consulta.realizar_consulta(consulta));
                System.out.println("Hacer otra consulta? (Ingrese 1: Si, 0: No)");
                repetir = sc.nextInt()==1;
            }  
        }
        catch (Exception e)  {
            e.printStackTrace();   
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

    public static void main(String[] args) {
     new Cliente();
    }
}
