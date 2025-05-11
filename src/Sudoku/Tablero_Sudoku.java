package Sudoku;

/**
 * Clase que representa el tablero de juego del Sudoku.
 */
public class Tablero_Sudoku extends Celda
{
    /** Matriz de tipo Celda que representa el tablero del Sudoku. */
    private Celda[][] tablero;

    /**
     * Constructor de la clase Tablero_Sudoku que inicializa el tablero con objetos Celda.
     */
    public Tablero_Sudoku()
    {
        this.tablero = new Celda[9][9];
        for (int i = 0; i < 9; i++) 
        {
            for (int j = 0; j < 9; j++) 
            {
                this.tablero[i][j] = new Celda(); // Inicializa cada celda con un nuevo objeto Celda
            }
        }
        
    }

    /**
     * Obtiene el tablero del Sudoku.
     * @return el tablero del Sudoku como una matriz de celdas.
     */
    public Celda[][] getTablero() {
        return tablero;
    }

    /**
     * Establece el tablero del Sudoku.
     * @param tablero 	la matriz de celdas que representa el tablero del Sudoku.
     */
    public void setTablero(Celda[][] tablero) {
        this.tablero = tablero;
    }
}