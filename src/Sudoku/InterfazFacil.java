package Sudoku;

/**
 * Clase InterfazFacil, una subclase de InterfazDificultadAbstracta que representa una interfaz con dificultad facil.
 * Esta clase define especificamente la dificultad del juego como "Facil".
 */
public class InterfazFacil extends InterfazDificultadAbstracta 
{
    /**
     * Constructor de la clase InterfazFacil.
     * Establece el nombre de la dificultad como "Facil".
     */
    public InterfazFacil() 
    {
        nombre = "Facil";
    }

    /**
     * Sobrescribe el metodo establecerDificultad de la clase padre InterfazDificultadAbstracta.
     * Este metodo crea una nueva instancia de la interfaz principal del juego y la hace visible,
     * estableciendo asi la dificultad del juego a facil.
     */
    @Override
    public void establecerDificultad() 
    {
        Interfaz.crearNuevaInstancia().setVisible(true);
    }
}
