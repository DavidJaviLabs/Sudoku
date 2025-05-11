package Sudoku;

/**
 * Clase InterfazDificil, una subclase de InterfazDificultadAbstracta que representa una interfaz con dificultad dificil.
 * Esta clase define especificamente la dificultad del juego como "Dificil".
 */
public class InterfazDificil extends InterfazDificultadAbstracta 
{
	 /**
     * Constructor de la clase InterfazDificil.
     * Establece el nombre de la dificultad como "Dificil".
     */
    public InterfazDificil() 
    {
        nombre = "Dificil";
    }

    /**
     * Sobrescribe el metodo establecerDificultad de la clase padre InterfazDificultadAbstracta.
     * Este metodo crea una nueva instancia de la interfaz principal del juego y la hace visible,
     * estableciendo asi la dificultad del juego a dificil.
     */
    @Override
    public void establecerDificultad() 
    {
        Interfaz.crearNuevaInstancia().setVisible(true);
    }
}
