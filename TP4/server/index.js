const WebSocket = require("ws")
const http = require('http')
const server= http.createServer()

const io = require('socket.io')(server, {
  cors: { origin:'*'}
})
server.listen(8000)

io.on('connection',(socket)=>{
  console.log("Nuevo cliente conectado!")
  //ENVIAR ESTADO
  io.emit("state", estado)//Le envia el estado actual del juego al jugador conectado


  //AÑADIR JUGADORES A LA PARTIDA
  socket.on('play',(data)=>{
    console.log("Usuario "+data.usuario+ " se quiere unir a la partida")
    if(estado.jugadores<2){
      addPlayer()
      socket.emit("playResp",{nroJugador: estado.jugadores})//Si se agrego se le envia su numero de jugador
    }else{
      socket.emit("playResp",{nroJugador: -1}) //Si ya esta lleno devuelve 0 indicando que no hay lugar
    }
    io.emit("state", estado)
  })

  socket.on('mov',(data)=>{
    realizarMov(data.nuevoTablero)
    
    /* 
    Si continua partida retorna el estado
    Si gano uno retorna 1
    Si hay empate retorna 2
    */
   let resp="";
   let verifTablero= verificarTablero(data.nuevoTablero)
   switch(verifTablero){
    case 0://Si sigue la partida envia el estado
      cambioTurno()
      
      break
    case 1: //Victoria de alguno
      io.emit("ganador",estado.jugadorTurno)
      break
    case 2://Empate
      io.emit("ganador",-1)
      break
   }
   io.emit("state", estado)


   if(verifTablero==1){
    resp=1
   }else if(verifTablero==2){
    resp=2
   }
    io.emit("resultMov", resp)
  })
})
/* const wss = new WebSocket.Server({port:8000}) */

const estado = {
  tablero:[
    [1, 0, 0],
    [0, 2, 0],
    [0, 0, 1]
  ],
  jugadorTurno: 1,
  jugadores:0,
  enJuego:false,
}

const addPlayer= ()=>{
  estado.jugadores+=1
  if(estado.jugadores==2){//Si hay dos jugadores arranca el juego
    estado.enJuego=true;
  }
}

const cambioTurno=()=>{
  if(estado.jugadorTurno==1){
    estado.jugadorTurno= 2
  }else{
    estado.jugadorTurno= 1
  }
}

const realizarMov=(nuevoTablero)=>{
  estado.tablero=nuevoTablero
}

function verificarTablero(tablero) {
  // Verificar filas
  for (let fila = 0; fila < tablero.length; fila++) {
    if (
      tablero[fila][0] !== 0 &&
      tablero[fila][0] === tablero[fila][1] &&
      tablero[fila][1] === tablero[fila][2]
    ) {
      return 1;
    }
  }

  // Verificar columnas
  for (let columna = 0; columna < tablero[0].length; columna++) {
    if (
      tablero[0][columna] !== 0 &&
      tablero[0][columna] === tablero[1][columna] &&
      tablero[1][columna] === tablero[2][columna]
    ) {
      return 1;
    }
  }

  // Verificar diagonales
  if (
    (tablero[0][0] !== 0 &&
      tablero[0][0] === tablero[1][1] &&
      tablero[1][1] === tablero[2][2]) ||
    (tablero[0][2] !== 0 &&
      tablero[0][2] === tablero[1][1] &&
      tablero[1][1] === tablero[2][0])
  ) {
    return 1;
  }

  // Verificar si todas las posiciones son diferentes de 0
  let todasDiferentesDeCero = true;
  for (let fila = 0; fila < tablero.length; fila++) {
    for (let columna = 0; columna < tablero[fila].length; columna++) {
      if (tablero[fila][columna] === 0) {
        todasDiferentesDeCero = false;
        break;
      }
    }
    if (!todasDiferentesDeCero) {
      break;
    }
  }

  if (todasDiferentesDeCero) {
    return 2;
  }

  return 0;
}




/* wss.on("connection",ws => {
  console.log("Nuevo cliente conectado!")
  
  ws.send(JSON.stringify(estado))
  
  
  //Recibo los mensajes
  ws.on("message", data =>{//TODO: gestionar concurrencia
    const {action, nuevoEstado} = JSON.parse(data)
    switch (action){
      case 'play':
        //TODO: Asegurar atomicidad
        addPlayer()
        break;
      case 'mov':
        realizarMov(nuevoEstado.tablero)
        cambioTurno()
      break;
    }

   ws.send(JSON.stringify(estado))
    /* ws.send(JSON.stringify({message: "Recibido"})) 
  })


  //Envio mensajes
  //ws.send(JSON.stringify(message))

   ws.on("close", ()=>{
    console.log("Cliente se ha desconectado!")
  }) 
}) */