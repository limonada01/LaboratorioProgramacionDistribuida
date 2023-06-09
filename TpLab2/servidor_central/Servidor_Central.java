package TpLab2.servidor_central;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor_Central {
    static Cache cache = new Cache();
    static public void main(String args[]) {
        try { 
            LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            Servicio_Consulta servicio_central = new Servicio_Consulta_Imp(cache, args[1], args[2], args[3], args[4]);
            //Naming.rebind("rmi://localhost:" + args[0] + "/CalculadoraImp", calc);  }
            Naming.rebind("rmi://localhost:" + args[0] + "/Servicio_Consulta_Imp", servicio_central);  }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en Servidor_Central:");
            e.printStackTrace();
            System.exit(1); }
    }

}
