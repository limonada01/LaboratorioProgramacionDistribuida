package Tp1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Cache {
    Map<String, String> consultas;
    Semaphore mutex = new Semaphore(1);
    public Cache(){
        consultas = new HashMap<String, String>();

    }

    public String getConsulta(String consulta){ //Recibimos o el horoscopo o la fecha
        String res=null;
        try{
            mutex.acquire();
            res= consultas.get(consulta); //Si existe retorna la calve caso contrario retorna null
            if(res!=null){
                mutex.release();
            }
        }catch(InterruptedException e){}
        return res;
    }

    public void putRespuesta(String consultaKey, String respuestaVal){
        consultas.put(consultaKey, respuestaVal);
        mutex.release();
    }

}
