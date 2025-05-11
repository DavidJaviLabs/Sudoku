package Sudoku;

/**
 * Clase InterfazMedio, una extension de InterfazDificultadAbstracta que representa una interfaz con dificultad media.
 * Esta clase establece especificamente la dificultad del juego como "Medio".
 */
public class InterfazMedio extends InterfazDificultadAbstracta 
{
    /**
     * Constructor de la clase InterfazMedio.
     * Asigna el nombre de la dificultad a "Medio".
     */
    public InterfazMedio() 
    {
        nombre = "Medio";
    }

    /**
     * Implementa el metodo abstracto establecerDificultad.
     * Crea y muestra una nueva instancia de la interfaz principal del juego, configurando la dificultad a nivel medio.
     */
    @Override
    public void establecerDificultad() 
    {
        Interfaz.crearNuevaInstancia().setVisible(true);
    }
}