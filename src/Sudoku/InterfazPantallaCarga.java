package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Clase InterfazPantallaCarga, extiende de JFrame e implementa la interfaz PantallaCarga.
 * Esta clase proporciona una interfaz de usuario para una pantalla de carga con una barra de progreso.
 */
public class InterfazPantallaCarga extends JFrame implements PantallaCarga 
{
	/** Barra de progreso que muestra el avance de la carga en la pantalla de carga. */
	private JProgressBar barraProgreso;

	/** Panel principal que contiene los componentes de la pantalla de carga. */
	private JPanel panelPantallaCarga;

	/**
	 * Etiqueta para mostrar una imagen superpuesta en la pantalla de carga.
	 */
	private JLabel imagenSuperpuesta;

	/**
	 * Temporizador que controla la actualizacion de la barra de progreso.
	 */
	private Timer temporizador;

	/**
	 * Contador utilizado para actualizar el valor de la barra de progreso.
	 */
	private int cont;
	
	/**
	 * Constructor de la clase InterfazPantallaCarga.
	 * Inicializa los componentes de la interfaz, establece la ubicacion y configura la barra de progreso.
	 */
	public InterfazPantallaCarga() 
	{
		initComponents();
		setLocationRelativeTo(null);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("sudoku.png");
        setIconImage(icon);

		ImageIcon imagen = new ImageIcon("pantallaCarga.jpeg");
		imagenSuperpuesta = new JLabel(imagen);
		imagenSuperpuesta.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());
		panelPantallaCarga.add(imagenSuperpuesta, BorderLayout.CENTER);

		cont = -1;
		barraProgreso.setValue(0);
		barraProgreso.setStringPainted(true);
		temporizador = new Timer(40, new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actualizarProgreso();
			}
		});
		temporizador.start();
	}

	/**
	 * Inicializa los componentes de la interfaz de usuario.
	 * Este metodo es generado automaticamente y configura los elementos visuales de la interfaz.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		panelPantallaCarga = new javax.swing.JPanel();
		barraProgreso = new javax.swing.JProgressBar();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(600, 880));
		setUndecorated(true);
		setPreferredSize(new java.awt.Dimension(600, 880));
		setResizable(false);

		panelPantallaCarga.setMinimumSize(new java.awt.Dimension(600, 880));
		panelPantallaCarga.setPreferredSize(new java.awt.Dimension(600, 880));

		barraProgreso.setBackground(new java.awt.Color(255, 255, 255));
		barraProgreso.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
		barraProgreso.setForeground(new java.awt.Color(87, 115, 129));

		javax.swing.GroupLayout panelPantallaCargaLayout = new javax.swing.GroupLayout(panelPantallaCarga);
		panelPantallaCarga.setLayout(panelPantallaCargaLayout);
		panelPantallaCargaLayout.setHorizontalGroup(
				panelPantallaCargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelPantallaCargaLayout.createSequentialGroup()
						.addGap(80, 80, 80)
						.addComponent(barraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(80, Short.MAX_VALUE))
				);
		panelPantallaCargaLayout.setVerticalGroup(
				panelPantallaCargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPantallaCargaLayout.createSequentialGroup()
						.addContainerGap(844, Short.MAX_VALUE)
						.addComponent(barraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(23, 23, 23))
				);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panelPantallaCarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panelPantallaCarga, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
				);

		pack();
	}// </editor-fold>                        

	/**
	 * Actualiza el progreso de la barra de progreso y, una vez completado, inicia un temporizador para cambiar la interfaz.
	 */
	public void actualizarProgreso() 
	{
		cont++;
		barraProgreso.setValue(cont);

		if (cont == 100) 
		{
			temporizador.stop();

			Timer segundoTemporizador = new Timer(400, new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					cambiarInterfaz();
				}
			});
			segundoTemporizador.setRepeats(false);
			segundoTemporizador.start();
		}
	}

	/**
	 * Cambia la interfaz actual a la interfaz de seleccion de dificultad despues de que se completa la carga.
	 */
	public void cambiarInterfaz() 
	{
		// Hacer que la interfaz de carga no sea visible
		setVisible(false);

		Interfaz.getInstancia().CrearCarpetas();
		InterfazDificultad.getInstancia().setVisible(true);
	}
}