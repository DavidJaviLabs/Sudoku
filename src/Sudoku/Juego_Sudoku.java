package Sudoku;

import java.util.Random;

/**
 * La clase Juego_Sudoku extiende de Tablero_Sudoku y gestiona las reglas y logica del juego Sudoku.
 */
public class Juego_Sudoku extends Tablero_Sudoku
{
	/**
	 * Instancia unica de la clase Juego_Sudoku.
	 */
	private static Juego_Sudoku instancia;
	
	/**
	 * Almacena la primera solucion encontrada del Sudoku.
	 * Se utiliza para comparar con otras soluciones potenciales y determinar si hay mas de una solucion para el Sudoku.
	 */
	private Celda[][] primeraSolucion;

	/**
	 * Constructor de la clase Juego_Sudoku.
	 * Inicializa la instancia con una solucion inicial nula, indicando que aun no se ha encontrado una solucion para el Sudoku.
	 */
	public Juego_Sudoku() 
	{
		primeraSolucion = null;
	}

	/**
	 * Obtiene la instancia unica de Juego_Sudoku.
	 * @return instancia actual de Juego_Sudoku.
	 */
	public static Juego_Sudoku getInstancia()
	{
		if(instancia == null){
			instancia = new Juego_Sudoku();
		}
		return instancia;
	}

	/**
	 * Crea una nueva instancia de Juego_Sudoku.
	 * @return nueva instancia de Juego_Sudoku.
	 */
	public static Juego_Sudoku crearNuevaInstancia() {
		instancia = null;
		return getInstancia();
	}

	// Metodos de logica del juego

	/**
	 * Comprueba si el tablero de Sudoku esta resuelto.
	 * @return true si el tablero esta completamente resuelto, false de lo contrario.
	 */
	public boolean resuelto() 
	{
		for (int i = 0; i < 9; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				if (!this.getTablero()[i][j].isCorrecto()) 
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Verifica si el numero ingresado en una casilla es valido en esa fila.
	 * @param fila La fila en la que se encuentra la casilla.
	 * @param columna La columna en la que se encuentra la casilla.
	 * @param num El numero a verificar.
	 * @return true Si el numero es valido en la fila, false de lo contrario.
	 */
	public boolean posicionValidaFila(int fila, int columna, int num)
	{
		for (int j = 0; j < 9; j++) 
		{
			if (this.getTablero()[fila][j].getValorBueno() == num && j != columna) 
			{
				this.getTablero()[fila][columna].setCorrecto(false);
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifica si el numero ingresado en una casilla es valido en esa columna.
	 * @param fila La fila en la que se encuentra la casilla.
	 * @param columna La columna en la que se encuentra la casilla.
	 * @param num El numero a verificar.
	 * @return true Si el numero es valido en la columna, false de lo contrario.
	 */
	public boolean posicionValidaColumna(int fila, int columna, int num) 
	{
		for (int i = 0; i < 9; i++) 
		{
			if (this.getTablero()[i][columna].getValorBueno() == num && i != fila) 
			{
				this.getTablero()[fila][columna].setCorrecto(false);
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifica si el numero ingresado en una casilla de 3x3 es valido.
	 * @param fila La fila en la que se encuentra la casilla.
	 * @param columna La columna en la que se encuentra la casilla.
	 * @param num El numero a verificar.
	 * @return true Si el numero es valido en la region 3x3, false de lo contrario.
	 */
	public boolean posicionValida3x3(int fila, int columna, int num) 
	{
		int cuadranteFila = ((fila) / 3) * 3;
		int cuadranteColumna = ((columna) / 3) * 3;
		for (int i = cuadranteFila; i < cuadranteFila + 3; i++) 
		{
			for (int j = cuadranteColumna; j < cuadranteColumna + 3; j++) 
			{
				if (getTablero()[i][j].getValorBueno() == num && i != fila && j != columna) 
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Resuelve el Sudoku verificando si hay multiples soluciones. 
	 * Utiliza un enfoque recursivo de backtracking para encontrar una solucion y luego verifica si hay otra.
	 * Almacena temporalmente la primera solucion encontrada para compararla con siguientes soluciones.
	 * @return true si el Sudoku tiene una unica solucion, false si tiene multiples soluciones o no se puede resolver.
	 */
	public boolean resolverSudoku() 
	{
		if (primeraSolucion == null) 
		{
			if (resolverYGuardar(0, 0)) 
			{
				primeraSolucion = copiarTablero(getTablero());
				return resolverSudoku();
			}
			return false;
		} else {
			if (resolverYComparar(0, 0)) 
			{
				primeraSolucion = null;
				return false;
			}
			primeraSolucion = null;
			return true;
		}
	}

	/**
	 * Resuelve el Sudoku recursivamente y guarda la primera solucion encontrada.
	 * Este metodo implementa la tecnica de backtracking para probar diferentes combinaciones de numeros.
	 * @param fila La fila actual en la que se esta trabajando.
	 * @param columna La columna actual en la que se esta trabajando.
	 * @return true si se encuentra una solucion valida, false de lo contrario.
	 */
	private boolean resolverYGuardar(int fila, int columna) 
	{
		if (fila == 9) 
		{
			return true; // Se encontro una solucion
		}

		int siguienteFila = fila;
		int siguienteColumna = columna + 1;

		if (siguienteColumna == 9) 
		{
			siguienteFila++;
			siguienteColumna = 0;
		}

		if (getTablero()[fila][columna].getValorBueno() != 0) 
		{
			return resolverYGuardar(siguienteFila, siguienteColumna);
		}

		for (int num = 1; num <= 9; num++) 
		{
			if (posicionValidaFila(fila, columna, num) && posicionValidaColumna(fila, columna, num) && posicionValida3x3(fila, columna, num)) 
			{
				getTablero()[fila][columna].setValorBueno(num);
				if (resolverYGuardar(siguienteFila, siguienteColumna)) 
				{
					return true;
				}
				getTablero()[fila][columna].setValorBueno(0); // Backtracking
			}
		}
		return false;
	}

	/**
	 * Resuelve el Sudoku recursivamente y compara la solucion encontrada con la primera solucion almacenada.
	 * Este metodo se utiliza para determinar si hay mas de una solucion posible para el Sudoku.
	 * @param fila La fila actual en la que se esta trabajando.
	 * @param columna La columna actual en la que se esta trabajando.
	 * @return true si se encuentra una solucion diferente a la primera, false de lo contrario.
	 */
	private boolean resolverYComparar(int fila, int columna) 
	{
		if (fila == 9) 
		{
			return !esIgualAPrimeraSolucion(); // Compara con la primera solucion
		}

		int siguienteFila = fila;
		int siguienteColumna = columna + 1;

		if (siguienteColumna == 9) 
		{
			siguienteFila++;
			siguienteColumna = 0;
		}

		if (getTablero()[fila][columna].getValorBueno() != 0) 
		{
			return resolverYComparar(siguienteFila, siguienteColumna);
		}

		for (int num = 1; num <= 9; num++) 
		{
			if (posicionValidaFila(fila, columna, num) && posicionValidaColumna(fila, columna, num) && posicionValida3x3(fila, columna, num)) 
			{
				getTablero()[fila][columna].setValorBueno(num);
				if (resolverYComparar(siguienteFila, siguienteColumna)) 
				{
					return true;
				}
				getTablero()[fila][columna].setValorBueno(0); // Backtracking
			}
		}
		return false;
	}

	/**
	 * Compara el tablero actual de Sudoku con la primera solucion almacenada para verificar si son iguales.
	 * @return true si el tablero actual es igual a la primera solucion, false de lo contrario.
	 */
	private boolean esIgualAPrimeraSolucion() 
	{
		for (int i = 0; i < 9; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				if (this.getTablero()[i][j].getValorBueno() != primeraSolucion[i][j].getValorBueno()) 
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Crea y devuelve una copia del tablero de Sudoku actual.
	 * Este metodo se utiliza para almacenar una copia de la solucion encontrada.
	 * @param tablero El tablero de Sudoku a copiar.
	 * @return Una copia del tablero de Sudoku.
	 */
	private Celda[][] copiarTablero(Celda[][] tablero) 
	{
		Celda[][] copia = new Celda[tablero.length][tablero[0].length];
		for (int i = 0; i < tablero.length; i++) 
		{
			for (int j = 0; j < tablero[i].length; j++) 
			{
				copia[i][j] = new Celda();
				copia[i][j].setValorBueno(tablero[i][j].getValorBueno());
			}
		}
		return copia;
	}

	/**
	 * Genera un tablero de Sudoku con una dificultad especificada.
	 * @param dificultadSeleccionada La dificultad del tablero a generar.
	 */
	public void generarSudokuConDificultad(String dificultadSeleccionada) 
	{
		int dificultad = 0;

		if(dificultadSeleccionada == "Facil")
		{
			dificultad = 37;
		} 
		else if(dificultadSeleccionada == "Medio")
		{
			dificultad = 30;
		} 
		else if(dificultadSeleccionada == "Dificil")
		{
			dificultad = 23;
		}

		Random random = new Random();
		int cont = 0;
		do {
			if(cont > 0)
			{
				this.setTablero(Juego_Sudoku.crearNuevaInstancia().getTablero());
			}

			for (int i = 0; i < dificultad; i++) 
			{
				int fila = random.nextInt(9);
				int columna = random.nextInt(9);
				int numero = random.nextInt(9) + 1;

				if (this.getTablero()[fila][columna].getValorBueno() == 0 && posicionValidaFila(fila, columna, numero) && posicionValidaColumna(fila, columna, numero) && posicionValida3x3(fila, columna, numero)) 
				{
					getTablero()[fila][columna].setValorBueno(numero);
					getTablero()[fila][columna].setValorDado(numero);
					getTablero()[fila][columna].setValorBase(true);
				} else {
					i--;
				}
			}
			cont ++;
		} while (!resolverSudoku());
	}
}