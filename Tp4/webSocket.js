const ws = new WebSocket("ws://localhost:3000");

    ws.addEventListener("open",()=>{
      console.log("Te has conectado")
      
      ws.send("Mensaje del front al back")

      ws.addEventListener("message", e =>{
        console.log(e.data)
      })
        
    })