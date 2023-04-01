package Tp1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;


public class Cache {
    private Map<String, String> consultas;
    private Semaphore mutex; 
    private ArrayList<String> consultasEnEspera;
    public Cache(){
        this.mutex= new Semaphore(1);
        this.consultasEnEspera=  new ArrayList<String>();
        this.consultas = new HashMap<String, String>();
    }

    public String getConsulta(String consulta){ //Recibimos o el horoscopo o la fecha
        String res=null;
        try{
            mutex.acquire();
            res= consultas.get(consulta); //Si existe retorna la respuesta caso contrario retorna null
            mutex.release();
        }catch(InterruptedException e){}
        return res;
    }

    public synchronized boolean realizarConsulta(String consulta){
        boolean realizaConsulta=false;
        try{
            if(!consultasEnEspera.contains(consulta)){//Si no habia nadie realizando consultas
                consultasEnEspera.add(consulta); //indica que hay un hilo realizando consultas y sale del metodo
                realizaConsulta=true;
            }else{
                while(consultasEnEspera.contains(consulta)){//Mientras otro hilo realiza la consulta esperan
                    this.wait();
                }
            }
        }catch(InterruptedException e){}
        return realizaConsulta;
    }

    public void putRespuesta(String consultaKey, String respuestaVal) {
        try {
            mutex.acquire(); //Consultas protegido por mutex
            consultas.put(consultaKey, respuestaVal);
            mutex.release();
            synchronized (this) {//consultasEnEspera protegido por synchronized(this)
                consultasEnEspera.remove(consultaKey); //indica que ya no se esta haciendo la consulta
                this.notifyAll();//Avisa a todos los hilos que puede ser que su consulta se haya resuelto
            }
        }catch(InterruptedException e){}
    }
}
