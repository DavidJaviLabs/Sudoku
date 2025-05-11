package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Clase InterfazDificultad, una subclase de JFrame que proporciona una interfaz grafica para la seleccion de la dificultad en un juego de Sudoku.
 * Permite al usuario elegir entre dificultades facil, media y dificil, y aplica el patron de disenio Singleton para asegurar una unica instancia de esta interfaz.
 */
public class InterfazDificultad extends JFrame
{
	/**
	 * Boton para seleccionar la dificultad dificil en la interfaz de seleccion de dificultad.
	 */
	private JButton botonDificil;

	/**
	 * Boton para seleccionar la dificultad facil en la interfaz de seleccion de dificultad.
	 */
	private JButton botonFacil;

	/**
	 * Boton para seleccionar la dificultad media en la interfaz de seleccion de dificultad.
	 */
	private JButton botonMedio;

	/**
	 * Panel que contiene los botones para la seleccion de la dificultad del juego.
	 */
	private JPanel panelDificultad;

	/**
	 * Variable estatica para almacenar la dificultad seleccionada como un string ("Facil", "Medio", "Dificil").
	 */
	static private String dificultadSeleccionada;

	/**
	 * Instancia de InterfazDificultadAbstracta que representa la dificultad seleccionada actualmente.
	 */
	private InterfazDificultadAbstracta dificultadSeleccionado;

	/**
	 * Instancia unica de la clase InterfazDificultad, utilizada para implementar el patron Singleton.
	 */
	private static InterfazDificultad instancia;
	
	
	/**
	 * Constructor de la clase InterfazDificultad.
	 * Inicializa los componentes de la interfaz y establece propiedades de disenio como colores y bordes.
	 */
	public InterfazDificultad() 
	{
		Color c = new Color(87,115,129);
		initComponents();
		
		Image icon = Toolkit.getDefaultToolkit().getImage("sudoku.png");
        setIconImage(icon);
        
		panelDificultad.setBorder(BorderFactory.createLineBorder(c, 3));
		setTitle("DIFICULTAD");
		setLocationRelativeTo(null);
		botonFacil.setBorder(BorderFactory.createLineBorder(c, 2));
		botonFacil.setFocusPainted(false);
		botonMedio.setBorder(BorderFactory.createLineBorder(c, 2));
		botonMedio.setFocusPainted(false);
		botonDificil.setBorder(BorderFactory.createLineBorder(c, 2));
		botonDificil.setFocusPainted(false);
	}

	/**
	 * Obtiene la dificultad seleccionada.
	 * @return 	La dificultad seleccionada ("Facil", "Medio" o "Dificil").
	 */
	public String getDificultadSeleccionada() 
	{
		return dificultadSeleccionada;
	}

	/**
	 * Establece la dificultad seleccionada.
	 * @param dificultadseleccionada La nueva dificultad seleccionada.
	 */
	public static void setDificultadSeleccionada(String dificultadseleccionada)
	{
		dificultadSeleccionada = dificultadseleccionada;
	}
	
	/**
	 * Obtiene la instancia actual de la clase InterfazDificultad, aplicando el patron Singleton.
	 * @return La instancia actual de InterfazDificultad.
	 */
	public static InterfazDificultad getInstancia()
	{
		if(instancia == null)
		{
			instancia = new InterfazDificultad();
		}
		return instancia;
	}

	/**
	 * Crea y devuelve una nueva instancia de InterfazDificultad, anulando la existente si la hay.
	 * @return La nueva instancia de InterfazDificultad.
	 */
	public static InterfazDificultad crearNuevaInstancia() 
	{
		instancia = null;
		return getInstancia();
	}

	/**
	 * Inicializa los componentes de la interfaz de usuario.
	 * Este metodo es generado automaticamente y configura los elementos visuales de la interfaz.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() 
	{
		panelDificultad = new javax.swing.JPanel();
		botonFacil = new javax.swing.JButton();
		botonMedio = new javax.swing.JButton();
		botonDificil = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(360, 298));
		setUndecorated(true);
		setResizable(false);

		panelDificultad.setBackground(new java.awt.Color(204, 204, 204));
		panelDificultad.setMinimumSize(new java.awt.Dimension(360, 298));

		botonFacil.setBackground(new java.awt.Color(255, 255, 255));
		botonFacil.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
		botonFacil.setForeground(new java.awt.Color(0, 153, 0));
		botonFacil.setText("FACIL");
		botonFacil.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonFacilActionPerformed(evt);
			}
		});

		botonMedio.setBackground(new java.awt.Color(255, 255, 255));
		botonMedio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
		botonMedio.setForeground(new java.awt.Color(255, 102, 51));
		botonMedio.setText("MEDIO");
		botonMedio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonMedioActionPerformed(evt);
			}
		});

		botonDificil.setBackground(new java.awt.Color(255, 255, 255));
		botonDificil.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
		botonDificil.setForeground(new java.awt.Color(255, 0, 0));
		botonDificil.setText("DIFICIL");
		botonDificil.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonDificilActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelDificultadLayout = new javax.swing.GroupLayout(panelDificultad);
		panelDificultad.setLayout(panelDificultadLayout);
		panelDificultadLayout.setHorizontalGroup(
				panelDificultadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDificultadLayout.createSequentialGroup()
						.addGap(110, 110, 110)
						.addGroup(panelDificultadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(botonDificil, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonFacil, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(110, 110, 110))
				);
		panelDificultadLayout.setVerticalGroup(
				panelDificultadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelDificultadLayout.createSequentialGroup()
						.addGap(65, 65, 65)
						.addComponent(botonFacil, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(30, 30, 30)
						.addComponent(botonMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(30, 30, 30)
						.addComponent(botonDificil, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(65, 65, 65))
				);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panelDificultad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panelDificultad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);

		pack();
	}// </editor-fold>                        

	/**
	 * Accion realizada al presionar el boton para la dificultad media.
	 * Establece la dificultad del juego a media y cierra la ventana actual.
	 * @param evt El evento de accion.
	 */
	private void botonMedioActionPerformed(ActionEvent evt) 
	{                                           
		establecerDificultad(new InterfazMedio());
	}                                          

	/**
	 * Accion realizada al presionar el boton para la dificultad dificil.
	 * Establece la dificultad del juego a dificil y cierra la ventana actual.
	 * @param evt El evento de accion.
	 */
	private void botonDificilActionPerformed(ActionEvent evt) 
	{                                             
		establecerDificultad(new InterfazDificil());
	}                                            

	/**
	 * Accion realizada al presionar el boton para la dificultad facil.
	 * Establece la dificultad del juego a facil y cierra la ventana actual.
	 * @param evt El evento de accion.
	 */
	private void botonFacilActionPerformed(ActionEvent evt) 
	{                                           
		establecerDificultad(new InterfazFacil());
	}                                          

	/**
	 * Establece la dificultad del juego basandose en la instancia de InterfazDificultadAbstracta proporcionada.
	 * Actualiza la dificultad seleccionada y cierra la ventana actual.
	 * @param dificultad La instancia de InterfazDificultadAbstracta que define la dificultad a establecer.
	 */
	public void establecerDificultad(InterfazDificultadAbstracta dificultad) 
	{
		this.dificultadSeleccionado = dificultad;
		dificultadSeleccionada = dificultadSeleccionado.getNombre();
		this.dificultadSeleccionado.establecerDificultad();

		this.dispose();
	}
}
