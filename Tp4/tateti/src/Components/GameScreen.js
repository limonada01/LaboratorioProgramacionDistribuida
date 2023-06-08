import {useEffect, useRef, useState} from "react"
import { Tablero } from "./Tablero/Tablero";
import { io } from 'socket.io-client' 
import './Tablero/styles.css'
import circleSvg from '../Images/circle.svg'
import crossSvg from '../Images/cross.svg'
const socket= io('http://localhost:8000', {reconnect:false})

export const GameScreen = ()=>{
  const [juego, setJuego] = useState({
    tablero: [[],[],[]],
    jugadorTurno: 0,
    enJuego:false,
    finalizado:false
  })
 
  //ESTADOS
  //1=jugador, 2=jugador2, -1=espectador
  const [nroJugador, setNroJugador] = useState(0)
  //-1=empate,0=enjuego, 1=jugador1, 2=jugador2, 
  const[nroGanador, setNroGanador] = useState(0)
  const[jugando, setJugando] = useState(false)
  const resetOnClick = async()=>{
    socket.emit('reset',{
    
    })
  }
  const playOnClick = async () => {
    socket.emit('play',{
      usuario:socket.id
    })
  };

  useEffect(()=>{
    if(juego.enJuego && nroJugador==0){//Si arranca el juego y no fue elegido
      setNroJugador(-1)//Se le asigna numero de jugador -1
    }
  },[juego])  

  useEffect(()=>{
    socket.on('state', (data)=>{
      setJuego(data)
    })

    socket.on('playResp',(data)=>{
      console.log(data)
      setNroJugador(data.nroJugador)
      setJugando(true)
    })

    socket.on('resultMov', (data)=>{
      setJuego(data.state)
      setNroGanador(data.nroGanador)
    })

    socket.on('reset', (data)=>{
      setJuego(data)
      setNroJugador(0)
      setNroGanador(0)
      setJugando(false)
    })
    return(()=>{
      socket.off('state')
      socket.off('playResp')
      socket.off('resultMov')
    })
    
  },[])
  //Recepcion.
  
  //TODO: Modificar el mostrado del boton para que lo afecte el estado del juego y no el del jugador
  return(
    <div className="tableContainer">
      {
        nroJugador==0 && 
        !juego.enJuego && 
        <button className="button play" onClick={playOnClick}>Jugar</button>
      }
      {
        !juego.finalizado && 
        nroJugador==-1 && 
        <h1 style={{color:"white"}}>ESPECTADOR</h1>
      }
      {
        juego.enJuego && 
        !juego.finalizado && 
        nroJugador!=0 && 
        nroJugador==juego.jugadorTurno && 
        <div className="containerMessage">
          {nroJugador==1 && <img src={circleSvg} alt="O" className="icon circle littleIcon" />}
          {nroJugador==2 && <img src={crossSvg} alt="X" className="icon cross littleIcon" />}
          <h1 style={{color:"white"}}>TU TURNO</h1>
        </div>
      }
      {
        juego.enJuego && 
        !juego.finalizado && 
        nroJugador!=-1 && 
        nroJugador!=juego.jugadorTurno && 
        <h1 style={{color:"white"}}>ESPERANDO MOVIMIENTO DEL RIVAL</h1>
      }

      {
        !juego.enJuego &&   
        nroJugador>0 && 
        <h1 style={{color:"white"}}>ESPERANDO SEGUNDO JUGADOR</h1>
      }

      {
        jugando && 
        nroGanador == nroJugador && 
        <h1 style={{color:"white"}} >GANASTE</h1>
      }

      {
        jugando && 
        nroGanador!=0 && 
        nroGanador!=3 && 
        nroGanador != nroJugador && 
        <h1 style={{color:"white"}}>PERDISTE</h1>
      }
      {
        !jugando &&
        nroGanador!=0 &&
        <h1 style={{color:"white"}}>GANO JUGADOR: {nroGanador}</h1>
      }
      {nroGanador==3 && <h1 style={{color:"white"}}>EMPATE</h1>}
      <Tablero 
        socket={socket}
        juego={juego}
        nroJugador={nroJugador}
        nroGanador={nroGanador}
      />
      {juego.finalizado && nroJugador!=-1 && <button className="button reset" onClick={resetOnClick}>Resetear</button>}
    </div>
  )
}