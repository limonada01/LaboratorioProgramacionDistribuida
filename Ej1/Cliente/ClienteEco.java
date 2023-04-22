import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;

class ClienteEco {
    static public void main (String args[]) {

/*        if (args.length<2) {
            System.err.println("Uso: ClienteEco IPServidor PuertoServidor ...");
            return;
        }

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
*/
        try {
            //LocateRegistry.createRegistry(20001);
            ServicioEco srv = (ServicioEco) Naming.lookup("//" + "192.168.1.36" + ":" + "54321" + "/Eco");

            //for (int i=2; i<args.length; i++)
                System.out.println(srv.eco("hola perro"));
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteEco:");
            e.printStackTrace();
        }
    }
}
