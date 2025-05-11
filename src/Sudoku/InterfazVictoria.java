package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase InterfazVictoria, extiende de JFrame.
 * Esta clase crea una interfaz grafica para mostrar una pantalla de victoria despues de completar un juego de Sudoku.
 */
public class InterfazVictoria extends JFrame 
{
	/**
	 * Etiqueta personalizada que muestra el mensaje de victoria con un efecto de degradado.
	 */
	private final EtiquetaDegradado labelVictoria;

	/**
	 * Temporizador que controla el movimiento del texto de victoria.
	 */
	private final Timer timer;

	/**
	 * Etiqueta que muestra el numero de fallos cometidos durante el juego.
	 */
	private final JLabel labelFallos;

	/**
	 * Etiqueta que muestra el tiempo transcurrido durante el juego.
	 */
	private final JLabel labelTiempo;

	/**
	 * Panel principal que contiene todos los componentes de la ventana.
	 */
	private JPanel panel;

	/**
	 * Variable que mantiene la posicion actual en el eje X del texto de victoria.
	 */
	private int posicionX;

	/**
	 * Instancia unica de la clase InterfazVictoria, utilizada para implementar el patron Singleton.
	 */
	private static InterfazVictoria instancia;


	/**
	 * Constructor de la clase InterfazVictoria.
	 * Inicializa la ventana de victoria con etiquetas personalizadas y configura el movimiento del texto de victoria.
	 */
	public InterfazVictoria() 
	{
		//Busca todas las ventanas JFrame, menos la actual, para deshabilitarlas.
		Window[] ventanas = Window.getWindows();
		for (Window ventana : ventanas) {
			if (ventana instanceof JFrame && ventana != this) {
				JFrame frame = (JFrame) ventana;
				frame.setEnabled(false);
			}
		}
		
		Image icon = Toolkit.getDefaultToolkit().getImage("sudoku.png");
        setIconImage(icon);

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Color colorBorde = new Color(226, 243, 227);

		setSize(974, 195);
		setLocation(473, 26);

		panel = new JPanel();
		panel.setLayout(null); // Establece un null layout para el panel
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		add(panel);

		labelVictoria = new EtiquetaDegradado("¡ ¡ ¡ HAS GANADO ! ! !");
		labelVictoria.setFont(new Font("Eras Bold ITC", Font.BOLD, 60));
		labelVictoria.setBounds(0, 0, labelVictoria.getPreferredSize().width, labelVictoria.getPreferredSize().height);
		labelVictoria.setHorizontalAlignment(SwingConstants.CENTER);

		panel.add(labelVictoria);

		labelFallos = new JLabel("Fallos: " + Interfaz.getInstancia().getFallos());
		labelFallos.setFont(new Font("Eras Bold ITC", Font.BOLD, 25));
		labelFallos.setHorizontalAlignment(SwingConstants.CENTER);
		labelFallos.setBounds(150, 75, 200, 150);
		panel.add(labelFallos);

		labelTiempo = new JLabel(Interfaz.getInstancia().obtenerTextoLabel());
		labelTiempo.setFont(new Font("Eras Bold ITC", Font.BOLD, 25));
		labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTiempo.setBounds(475, 75, 300, 150);
		panel.add(labelTiempo);

		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moverTexto();
			}
		});

		posicionX = -labelVictoria.getWidth(); // Iniciar desde la derecha (fuera de la pantalla)


		setVisible(true);

		// Iniciar el movimiento del texto
		timer.start();
	}

	/**
	 * Implementacion del patron de disenio Singleton para obtener la instancia actual de la clase.
	 * @return Instancia actual de InterfazVictoria.
	 */
	public static InterfazVictoria getInstancia()
	{
		if(instancia == null)
		{
			instancia = new InterfazVictoria();
		}
		return instancia;
	}

	/**
	 * Crea una nueva instancia de la clase InterfazVictoria, anulando la instancia existente si la hay.
	 * @return La nueva instancia de InterfazVictoria.
	 */
	public static InterfazVictoria crearNuevaInstancia() 
	{
		instancia = null;
		return getInstancia();
	}

	/**
	 * Mueve el texto de la etiqueta de victoria a traves de la ventana.
	 * El texto se mueve de derecha a izquierda y se reinicia una vez que sale de la pantalla.
	 */
	private void moverTexto() 
	{
		int velocidadMovimiento = 3; // Puedes ajustar la velocidad aqui

		// Mover el texto de derecha a izquierda
		posicionX += velocidadMovimiento;
		labelVictoria.setBounds(posicionX, 25, labelVictoria.getPreferredSize().width, labelVictoria.getPreferredSize().height);

		if (posicionX > getWidth()) 
		{
			// Si el texto ha desaparecido por la izquierda, reiniciar desde la derecha
			posicionX = -labelVictoria.getWidth();
		}
		repaint();
	}
}
