import { useState } from "react"
import "./styles.css"
import circleSvg from '../../Images/circle.svg'
import crossSvg from '../../Images/cross.svg'
export const Tablero = (props)=>{
  const {socket, juego, nroJugador } = props

  const click=(i,j)=>{
    if(!juego.finalizado && juego.tablero[i][j]=="" && juego.jugadorTurno == nroJugador){//Si la casilla esta blanca y es el turno del jugador
      const modifiedTablero = [...juego.tablero];
      if(nroJugador==1){
        modifiedTablero[i][j] = 1; // Modificar el valor en la posición deseada
      }else if(juego.tablero[i][j]=="" && nroJugador==2){
        modifiedTablero[i][j] = 2; // Modificar el valor en la posición deseada
      }
 
    socket.emit('mov',{
      nuevoTablero : modifiedTablero
    })
    }

    
  }

  const generateBoard = () => {
    const board = [];
    let cellCounter = 1;
  
    for (let i = 0; i < 3; i++) {
      const row = [];
  
      for (let j = 0; j < 3; j++) {
        let cellContent = ''; // Valor por defecto
        if (juego.tablero[i][j] === 1) {
          cellContent = <img src={circleSvg} alt="O" className="icon circle" />;
        } else if (juego.tablero[i][j] === 2) {
          cellContent = <img src={crossSvg} alt="X" className="icon cross" />;
        }
  
        row.push(
          <td
            className="celda"
            key={cellCounter}
            onClick={() => click(i, j)}
          >
            {cellContent}
          </td>
        );
        cellCounter++;
      }
  
      board.push(<tr key={i}>{row}</tr>);
    }
  
    return board;
  };
  
  return (
    <table className="table">
      <tbody>{generateBoard()}</tbody>
    </table>
  )}