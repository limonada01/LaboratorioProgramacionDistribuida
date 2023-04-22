package TpLab2.servidor_clima;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor_Clima {
    static public void main(String args[]) {

        try { 
            LocateRegistry.createRegistry(54322);
            Servicio_Clima servicio_clima = new Servicio_Clima_Imp();
            //Naming.rebind("rmi://localhost:" + args[0] + "/CalculadoraImp", calc);  }
            Naming.rebind("rmi://localhost:" + "54322" + "/Servicio_Clima_Imp", servicio_clima);  }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en Servidor_Clima:");
            e.printStackTrace();
            System.exit(1); }
        }
}
