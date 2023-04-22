package TpLab2.servidor_central;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import TpLab2.servidor_clima.Servicio_Clima;
import TpLab2.servidor_horoscopo.Servicio_Horoscopo;

public class Servicio_Consulta_Imp extends UnicastRemoteObject implements Servicio_Consulta {
    
    static private Cache cache;

    protected Servicio_Consulta_Imp(Cache cache) throws RemoteException{
        super();
        this.cache = cache;
    }

    @Override
    public String realizar_consulta(String consulta)throws RemoteException{
        String[] consutas_separadas = separadorConsultas(consulta);//c_s[0]=horoscopo,c_s[1]=clima
        String respuesta_horoscopo = "";
        String respuesta_clima = "";
        try {
            respuesta_horoscopo = consultaHoroscopo(consutas_separadas[0]);
            respuesta_clima = consultaClima(consutas_separadas[1]);
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return respuesta_horoscopo+" - "+respuesta_clima;
    }

    private String consultaHoroscopo(String signo) throws NotBoundException {
        String respuesta="";
        try {
            respuesta= cache.getConsulta(signo);
            if(respuesta==null){
                if(cache.realizarConsulta(signo)) { //Si el hilo fue elegido para hacer la consulta
                    Servicio_Horoscopo servicio_horoscopo = (Servicio_Horoscopo)Naming.lookup ("//localhost:54321/Servicio_Horoscopo_Imp");
                    respuesta = servicio_horoscopo.getHoroscopo(signo);
                    cache.putRespuesta(signo, respuesta);
                }else{
                    respuesta= cache.getConsulta(signo);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return respuesta;
    }

    private String consultaClima(String fecha) throws NotBoundException {
        String respuesta="";
        try {
            respuesta= cache.getConsulta(fecha);
            if(respuesta==null){ //Si no lo pudo obtener de cache
                if(cache.realizarConsulta(fecha)){ //Si el hilo fue elegido para hacer la consulta
                    Servicio_Clima servicio_clima= (Servicio_Clima)Naming.lookup ("//localhost:54322/Servicio_Clima_Imp");
                    respuesta = servicio_clima.getClima(fecha);
                    cache.putRespuesta(fecha, respuesta);
                }else{//Si el hilo viene de estar bloqueado esperando a que se cargue en cache la saca de ahi
                    respuesta= cache.getConsulta(fecha);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return respuesta;
    }

    private String[] separadorConsultas(String consultaOrigen){
        String consultaLowerCase= consultaOrigen.toLowerCase();
        String[] consultasSeparadas=consultaLowerCase.split("-");
        return consultasSeparadas;
    }
}   
