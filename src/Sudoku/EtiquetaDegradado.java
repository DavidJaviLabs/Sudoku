package Sudoku;

import java.awt.*;
import javax.swing.JLabel;

/**
 * Clase EtiquetaDegradado, una extension de JLabel que implementa un efecto de degradado en el texto.
 * El degradado se realiza desde un color gris a negro.
 */
public class EtiquetaDegradado extends JLabel 
{
	/**
     * Constructor de la clase EtiquetaDegradado.
     * Inicializa una nueva instancia de JLabel con el texto proporcionado.
     * @param text El texto que se mostrara en la etiqueta.
     */
    public EtiquetaDegradado(String text) 
    {
        super(text);
    }

    /**
     * Sobrescribe el metodo paintComponent de JLabel para dibujar el texto con un efecto de degradado.
     * El degradado se realiza desde un color gris (87, 115, 129) en la parte superior a negro en la parte inferior.
     * @param g El objeto Graphics utilizado para dibujar la etiqueta.
     */
    @Override
    protected void paintComponent(Graphics g) 
    {
        Color gris = new Color(87,115,129);
        Color negro = new Color(0,0,0);
        Graphics2D g2d = (Graphics2D) g.create();

        GradientPaint gradient = new GradientPaint(0, 0, gris, getWidth(), getHeight(), negro);
        g2d.setPaint(gradient);

        g2d.setFont(getFont());
        g2d.drawString(getText(), 0, getHeight() - 10);

        g2d.dispose();
    }
}