import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;

class Servidor {
  static public void main(String args[]) {
 	    //if (args.length!=1) { System.err.println("Uso: Servidor Puerto"); return; }
            /*if (System.getSecurityManager() == null) {
        	// System.setSecurityManager(new RMISecurityManager()); 
	       	System.setProperty("java.rmi.server.hostname","localhost");}
            System.out.println("YELLOW FLAG");*/
        try { 
            LocateRegistry.createRegistry(54321);
            Calculadora calc = new CalculadoraImp();
            System.out.println("holaaaa");
            //Naming.rebind("rmi://localhost:" + args[0] + "/CalculadoraImp", calc);  }
            Naming.rebind("rmi://localhost:" + "54321" + "/CalculadoraImp", calc);  
            System.out.println("que ondaaa");
        }
            
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1); }
        }
}
