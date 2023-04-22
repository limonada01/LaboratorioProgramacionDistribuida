package TpLab2.servidor_horoscopo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor_Horoscopo {
    static public void main(String args[]) {

       try { 
           LocateRegistry.createRegistry(54321);
           Servicio_Horoscopo servicio_horoscopo = new Servicio_Horoscopo_Imp();
           //Naming.rebind("rmi://localhost:" + args[0] + "/CalculadoraImp", calc);  }
           Naming.rebind("rmi://localhost:" + "54321" + "/Servicio_Horoscopo_Imp", servicio_horoscopo);  }
       catch (RemoteException e) {
           System.err.println("Error de comunicacion: " + e.toString());
           System.exit(1); }
       catch (Exception e) {
           System.err.println("Excepcion en Servidor_Horoscopo:");
           e.printStackTrace();
           System.exit(1); }
       }
}
