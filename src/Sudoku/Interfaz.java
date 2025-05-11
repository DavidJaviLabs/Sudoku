package Sudoku;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Esta clase representa la interfaz grafica para un juego de Sudoku.
 */
public class Interfaz extends JFrame 
{

	/**
	 * Instancia unica de la clase Interfaz, utilizada para implementar el patron Singleton.
	 */
	private static Interfaz instancia;

	/**
	 * Boton para cargar una partida guardada previamente.
	 */
	private JButton botonCargarPartida;

	/**
	 * Boton para guardar el estado actual del juego.
	 */
	private JButton botonGuardarPartida;

	/**
	 * Boton para iniciar una nueva partida.
	 */
	private JButton botonNuevaPartida;

	/**
	 * Boton para generar un nuevo tablero de Sudoku.
	 */
	private JButton botonNuevoTablero;

	/**
	 * Boton para reiniciar la partida actual, restableciendo el tablero.
	 */
	private JButton botonReiniciarPartida;

	/**
	 * Boton para mostrar la solucion del tablero actual de Sudoku.
	 */
	private JButton botonSolucionar;

	/**
	 * Etiqueta para mostrar el numero de fallos cometidos por el jugador.
	 */
	private JLabel jLabelFallos;

	/**
	 * Etiqueta para mostrar el tiempo transcurrido desde el inicio de la partida.
	 */
	private JLabel jLabelTiempo;

	/**
	 * Panel que contiene los botones de control del juego.
	 */
	private JPanel panelBotones;

	/**
	 * Panel que muestra informacion adicional, como el tiempo y los fallos.
	 */
	private JPanel panelInfo;

	/**
	 * Panel principal de la interfaz de usuario del juego.
	 */
	public JPanel panelPrincipal;

	/**
	 * Panel que contiene el tablero de Sudoku.
	 */
	private JPanel panelTablero;

	/**
	 * Panel que contiene el titulo o encabezado de la interfaz.
	 */
	private JPanel panelTitulo;

	/**
	 * Etiqueta que contiene una imagen superpuesta, usualmente utilizada en el encabezado.
	 */
	private final JLabel imagenSuperpuesta;

	/**
	 * Indice de la fila del tablero de Sudoku seleccionada por el usuario. Inicialmente es -1, indicando que ninguna fila esta seleccionada.
	 */
	private int filaSeleccionada;

	/**
	 * Indice de la columna del tablero de Sudoku seleccionada por el usuario. Inicialmente es -1, indicando que ninguna columna esta seleccionada.
	 */
	private int columnaSeleccionada;

	/**
	 * Temporizador utilizado para medir el tiempo transcurrido desde el inicio de la partida.
	 */
	private Timer timer;

	/**
	 * Variable para almacenar la cantidad de tiempo transcurrido en milisegundos desde el inicio de la partida.
	 */
	private long tiempoTranscurrido;

	/**
	 * Variable que almacena el tiempo inicial en milisegundos al momento de empezar la partida o reanudar despues de una pausa.
	 */
	private long tiempoInicial;

	/**
	 * Variable que almacena el tiempo transcurrido en milisegundos cuando se pausa el juego.
	 */
	private long tiempoPausado;

	/**
	 * Contador de los fallos cometidos por el jugador durante la partida.
	 */
	private int fallos;

	/**
	 * Bandera para indicar si un boton ha sido presionado, utilizada para controlar ciertas logicas en la interfaz.
	 */
	private boolean botonPresionado;

	/**
	 * Ruta al archivo de sonido que se reproduce al alcanzar una condicion de victoria en el juego.
	 */
	private final String rutaSonido;

	/**
	 * Constructor de la clase Interfaz. 
	 * Inicializa los componentes y configura el entorno grafico de la aplicacion Sudoku, incluyendo el temporizador, 
	 * el contador de fallos y la disposicion del tablero de juego.
	 */
	public Interfaz() 
	{
		initComponents();
		iniciarTemporizador();
		iniciarFallos();       
		
		Image icon = Toolkit.getDefaultToolkit().getImage("sudoku.png");
        setIconImage(icon);
        
        rutaSonido = "victory.wav";

		setTitle("SUDOKU : 'Nivel " + InterfazDificultad.getInstancia().getDificultadSeleccionada() + "'");
		Juego_Sudoku.crearNuevaInstancia().generarSudokuConDificultad(InterfazDificultad.getInstancia().getDificultadSeleccionada());


		setLocationRelativeTo(null);
		panelTablero.setLayout(new GridLayout(9, 9));

		ImageIcon imagen = new ImageIcon("sudokujpeg.png");
		imagenSuperpuesta = new JLabel(imagen);
		imagenSuperpuesta.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());
		panelTitulo.add(imagenSuperpuesta, BorderLayout.CENTER); // Agrega la imagen al centro del panel

		rellenarTablero();
		botonPresionado = false;
	}
	
	/**
	 * Establece el numero de fallos ocurridos durante el juego.
	 * @param nuevoValor el nuevo numero de fallos.
	 */
	public void setFallos(int nuevoValor) 
	{
		fallos = nuevoValor;
	}

	/**
	 * Obtiene el numero de fallos ocurridos durante el juego.
	 * @return el numero de fallos.
	 */
	public int getFallos() 
	{
		return fallos;
	}

	/**
	 * Establece el tiempo transcurrido en el temporizador.
	 * @param nuevoValor el nuevo tiempo transcurrido en milisegundos.
	 */
	public void setTiempoTranscurrido(long nuevoValor) 
	{
		tiempoTranscurrido = nuevoValor;
	}

	/**
	 * Obtiene el tiempo transcurrido en el temporizador.
	 * @return el tiempo transcurrido en milisegundos.
	 */
	public long getTiempoTranscurrido() 
	{
		return tiempoTranscurrido;
	}

	/**
	 * Obtiene el texto actual del JLabel asociado con el tiempo.
	 * @return el texto del JLabel del tiempo.
	 */
	public String obtenerTextoLabel() 
	{
		return jLabelTiempo.getText();
	}

	
	/**
	 * Implementacion del patron de disenio Singleton para obtener la instancia actual de la clase.
	 * @return instancia actual de la clase Interfaz.
	 */
	public static Interfaz getInstancia()
	{
		if(instancia == null)
		{
			instancia = new Interfaz();
		}
		return instancia;
	}

	/**
	 * Crea una nueva instancia de la clase Interfaz, anulando la instancia existente si la hay.
	 * @return la nueva instancia de la clase Interfaz.
	 */
	public static Interfaz crearNuevaInstancia() 
	{
		instancia = null;
		return getInstancia();
	}

	/**
	 * Inicializa el contador de fallos a cero y actualiza su representacion en la interfaz.
	 */
	public void iniciarFallos()
	{
		setFallos(0);
		actualizarNumeroFallos();
	}

	/**
	 * Actualiza el contador de fallos en la interfaz.
	 */
	public void actualizarNumeroFallos()
	{
		jLabelFallos.setText("Fallos : " + getFallos());
	}

	/**
	 * Reproduce un sonido de victoria a partir de un archivo de audio especificado.
	 */
	public void reproducirSonido() 
	{
		try 
		{
			File archivoSonido = new File(rutaSonido);
			if (archivoSonido.exists()) 
			{
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);

				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicia el temporizador para llevar un registro del tiempo transcurrido durante el juego.
	 */
	public void iniciarTemporizador() 
	{
		tiempoInicial = System.currentTimeMillis();
		timer = new Timer(10, new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				tiempoTranscurrido = System.currentTimeMillis() - tiempoInicial;
				actualizarTiempo(tiempoTranscurrido);
			}
		});
		timer.start();
	}

	/**
	 * Actualiza la etiqueta del tiempo en la interfaz con el tiempo transcurrido desde el inicio de una partida.
	 * @param tiempoTranscurrido tiempo transcurrido en milisegundos desde el inicio del juego.
	 */
	public void actualizarTiempo(long tiempoTranscurrido) 
	{
		long horas = tiempoTranscurrido / 3600000;
		long minutos = (tiempoTranscurrido % 3600000) / 60000;
		long segundos = (tiempoTranscurrido % 60000) / 1000;
		long milisegundos = tiempoTranscurrido % 1000;

		String tiempoFormateado = String.format("Tiempo: %01d:%02d:%02d.%03d", horas, minutos, segundos, milisegundos);

		jLabelTiempo.setText(tiempoFormateado);
	}

	/**
	 * Pausa el temporizador, almacenando el tiempo transcurrido hasta ese momento.
	 */
	public void pausarTemporizador() 
	{
		tiempoPausado = tiempoTranscurrido; // Almacena el tiempo actual
		timer.stop(); // Detiene el temporizador
	}

	/**
	 * Valida la entrada del usuario en un campo de texto especifico del tablero de Sudoku.
	 * @param textField el campo de texto a validar.
	 * @param fila la fila del tablero en la que se encuentra el campo de texto.
	 * @param col la columna del tablero en la que se encuentra el campo de texto.
	 */
	public void validateInput(JTextField textField, int fila, int col) 
	{
		String texto = textField.getText();
		Font fuente = textField.getFont();
		final int nuevoTamanoLetra = 20; // Tamanio de letra deseado

		// Verificar si la cadena de entrada esta vacia
		if (texto.isEmpty()) 
		{
			Juego_Sudoku.getInstancia().getTablero()[fila][col].setValorDado(0); // Establecer el valor dado a 0 si la cadena esta vacia
		} 
		else 
		{
			try 
			{
				int enteredValue = Integer.parseInt(texto);
				int correctValue = Juego_Sudoku.getInstancia().getTablero()[fila][col].getValorBueno();

				if (enteredValue != correctValue)
				{
					Juego_Sudoku.getInstancia().getTablero()[fila][col].setCorrecto(false);
					textField.setForeground(Color.RED); // Cambia el color del texto a rojo
					fallos++;
					actualizarNumeroFallos();
					textField.setFont(fuente.deriveFont(Font.PLAIN, nuevoTamanoLetra));
				} else 
				{
					Juego_Sudoku.getInstancia().getTablero()[fila][col].setCorrecto(true);
					textField.setForeground(Color.BLACK); // Cambia el color del texto a negro si es correcto
					textField.setFont(fuente.deriveFont(Font.PLAIN, nuevoTamanoLetra));

					if (Juego_Sudoku.getInstancia().getTablero()[fila][col].isBase())
					{
						textField.setFont(fuente.deriveFont(Font.BOLD, nuevoTamanoLetra));
					}

					if (!botonPresionado && Juego_Sudoku.getInstancia().resuelto()) 
					{
						timer.stop();
						reproducirSonido();

						InterfazVictoria.crearNuevaInstancia();

						// Establecer un temporizador para llamar al segundo constructor despues de 5 segundos
						Timer delayTimer = new Timer(5000, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// Llamada al segundo constructor despues de 5 segundos
								new InterfazRepetir().setVisible(true);
							}
						});

						delayTimer.setRepeats(false); // Esto asegura que el temporizador se ejecute solo una vez
						delayTimer.start(); // ¡Olvidaste iniciar el temporizador!
					}
				}

				Juego_Sudoku.getInstancia().getTablero()[fila][col].setValorDado(enteredValue);
			} 
			catch (NumberFormatException e) 
			{
				// Manejar la excepcion de formato si la entrada no es un numero valido
				textField.setForeground(Color.BLACK);
				Juego_Sudoku.getInstancia().getTablero()[fila][col].setValorDado(0); // Establecer el valor dado a 0 en caso de error
			}
		}
	}

	/**
	 * Genera un nuevo tablero de Sudoku segun la dificultad seleccionada.
	 */
	public void generarNuevoSudokuConDificultad() {

		Juego_Sudoku.crearNuevaInstancia().generarSudokuConDificultad(InterfazDificultad.getInstancia().getDificultadSeleccionada());
		updateInterfaz();
	}

	/**
	 * Define los bordes de las celdas del tablero para visualizar la estructura del Sudoku.
	 * @param i indice de la fila de la celda.
	 * @param j indice de la columna de la celda.
	 * @param textField campo de texto de la celda.
	 */
	public void bordesCeldas(int i, int j, JTextField textField)
	{
		boolean bordeSuperiorGr, BordeIzqGr;

		bordeSuperiorGr = (i % 3 == 0);
		BordeIzqGr = (j % 3 == 0);

		int arriba = 1;
		int izq = 1;
		int abajo = 1;
		int drch = 2;

		if (bordeSuperiorGr) 
		{
			arriba = 3;
		}
		if (BordeIzqGr) 
		{
			izq = 3;
		}
		if (i == 8) 
		{
			abajo = 3; // Borde grueso en la ultima fila
		}
		if (j == 8) 
		{
			drch = 3; // Borde grueso en la ultima columna
		}

		textField.setBorder(BorderFactory.createMatteBorder(arriba, izq, abajo, drch, Color.DARK_GRAY));
	}

	/**
	 * Rellena el tablero de Sudoku con campos de texto, aplicando filtros y estilos adecuados.
	 */
	public void rellenarTablero()
	{

		// Reemplaza el texto en el documento solo si el nuevo texto es un solo digito entre 1 y 9.
		DocumentFilter filter = new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				Document doc = fb.getDocument();
				String currentText = doc.getText(0, doc.getLength());
				currentText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
				if (currentText.length() <= 1 && currentText.matches("[1-9]?")) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		};
		// Vuelve a llenar la interfaz con los numeros del nuevo Sudoku
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				final int fila = i; // Definir 'fila' como final
				final int col = j; // Definir 'col' como final
				JTextField textField = new JTextField();
				int value = Juego_Sudoku.getInstancia().getTablero()[i][j].getValorDado();
				if (value != 0) {
					textField.setText(String.valueOf(value));
					validateInput(textField, fila, col);
					if (Juego_Sudoku.getInstancia().getTablero()[i][j].isBase()) 
					{
						textField.setEditable(false);
					} 
				}
				textField.setHorizontalAlignment(SwingConstants.CENTER);
				((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);

				bordesCeldas(i, j, textField);

				// Agrega un DocumentListener al JTextField
				textField.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						validateInput(textField, fila, col);
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						validateInput(textField, fila, col);
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						validateInput(textField, fila, col);
					}
				});
				textField.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int fila = (panelTablero.getComponentZOrder(textField)) / 9;
						int col = (panelTablero.getComponentZOrder(textField)) % 9;
						if (filaSeleccionada != -1 && columnaSeleccionada != -1) 
						{
							quitarResaltadoFilaColumna(filaSeleccionada, columnaSeleccionada);
						}
						resaltarFilaColumna(fila, col);
						filaSeleccionada = fila;
						columnaSeleccionada = col;
					}
				});
				panelTablero.add(textField);
			}
		}
		panelTablero.revalidate(); // Vuelve a validar el panelTablero para que se muestren los nuevos componentes
		panelTablero.repaint(); // Repinta el panelTablero para que se muestren los nuevos componentes  
	}

	/**
	 * Crea las carpetas necesarias para guardar las partidas del juego Sudoku en el sistema del usuario.
	 */
	public void CrearCarpetas()
	{
		// Obtener la ruta al escritorio del usuario
		String rutaPartidas = System.getProperty("user.home") + File.separator + "Sudoku";

		// Rutas completas para las carpetas
		String rutaCarpetaPartidas = rutaPartidas + File.separator + "PartidasGuardadas";
		String rutaCarpetaFacil = rutaCarpetaPartidas + File.separator + " Nivel_Facil";
		String rutaCarpetaMedio = rutaCarpetaPartidas + File.separator + " Nivel_Medio";
		String rutaCarpetaDificil = rutaCarpetaPartidas + File.separator + "Nivel_Dificil";

		// Crear objetos File para las carpetas
		File carpetaPartidas = new File(rutaCarpetaPartidas);
		File carpetaFacil = new File(rutaCarpetaFacil);
		File carpetaMedio = new File(rutaCarpetaMedio);
		File carpetaDificil = new File(rutaCarpetaDificil);

		// Verificar si las carpetas ya existen
		if (!carpetaPartidas.exists() && !carpetaFacil.exists() && !carpetaMedio.exists() && !carpetaDificil.exists()) 
		{
			// Si no existen, crearlas
			carpetaPartidas.mkdirs(); // Crear carpeta 'PartidasGuardadas' en el escritorio
			carpetaFacil.mkdirs(); // Crear carpeta para partidas de nivel facil
			carpetaMedio.mkdirs(); // Crear carpeta para partidas de nivel medio
			carpetaDificil.mkdirs(); // Crear carpeta para partidas de nivel dificil

			System.out.println("Carpetas creadas exitosamente.");
		} else 
		{
			System.out.println("Las carpetas ya existen.");
		}
	}

	/**
	 * Actualiza la interfaz del tablero de Sudoku, limpiando y rellenandolo de nuevo.
	 */
	public void updateInterfaz() {
		panelTablero.removeAll(); // Elimina los componentes anteriores del panelTablero
		rellenarTablero();
	}

	/**
	 * Resalta la fila y columna del tablero de Sudoku en la que se encuentra una celda seleccionada.
	 * @param fila indice de la fila de la celda seleccionada.
	 * @param col indice de la columna de la celda seleccionada.
	 */
	public void resaltarFilaColumna(int fila, int col) {
		Color filacol = new Color(226, 243, 227);
		Color casilla = new Color(187, 251, 191);
		for (Component component : panelTablero.getComponents()) {
			if (component instanceof JTextField) {
				JTextField textField = (JTextField) component;
				int currentfila = (panelTablero.getComponentZOrder(textField)) / 9;
				int currentCol = (panelTablero.getComponentZOrder(textField)) % 9;

				if (currentfila == fila || currentCol == col) {
					textField.setBackground(filacol);
				}

				// Resaltar la casilla seleccionada con un color diferente
				if (currentfila == fila && currentCol == col) {
					textField.setBackground(casilla);
				}
			}
		}
	}
	
	/**
	 * Quita el resaltado de una fila y columna especificadas en el tablero de Sudoku.
	 * @param fila indice de la fila a desresaltar.
	 * @param col indice de la columna a desresaltar.
	 */
	public void quitarResaltadoFilaColumna(int fila, int col) {
		Color customColor = new Color(238, 238, 238);
		for (int i = 0; i < 9; i++) {
			JTextField textFieldfila = (JTextField) panelTablero.getComponent(fila * 9 + i);
			JTextField textFieldCol = (JTextField) panelTablero.getComponent(i * 9 + col);

			// Verificar si la celda es editable para cambiar su color de fondo
			if (Juego_Sudoku.getInstancia().getTablero()[fila][i].isBase()) {
				textFieldfila.setBackground(customColor); // Color para celda no editable
			} else {
				textFieldfila.setBackground(Color.WHITE); // Color para celda editable
			}

			if (Juego_Sudoku.getInstancia().getTablero()[i][col].isBase()) {
				textFieldCol.setBackground(customColor); // Color para celda no editable
			} else {
				textFieldCol.setBackground(Color.WHITE); // Color para celda editable
			}
		}
	}

	/**
	 * Inicializa los componentes de la interfaz de usuario.
	 * Este metodo es generado automaticamente y configura los elementos visuales de la interfaz.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		panelPrincipal = new javax.swing.JPanel();
		panelTitulo = new javax.swing.JPanel();
		panelInfo = new javax.swing.JPanel();
		jLabelTiempo = new javax.swing.JLabel();
		jLabelFallos = new javax.swing.JLabel();
		panelTablero = new javax.swing.JPanel();
		panelBotones = new javax.swing.JPanel();
		botonNuevaPartida = new javax.swing.JButton();
		botonNuevoTablero = new javax.swing.JButton();
		botonReiniciarPartida = new javax.swing.JButton();
		botonGuardarPartida = new javax.swing.JButton();
		botonCargarPartida = new javax.swing.JButton();
		botonSolucionar = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(990, 800));
		setResizable(false);

		panelPrincipal.setMaximumSize(new java.awt.Dimension(945, 762));
		panelPrincipal.setMinimumSize(new java.awt.Dimension(945, 762));
		panelPrincipal.setLayout(null);

		panelTitulo.setBackground(new java.awt.Color(102, 255, 153));

		javax.swing.GroupLayout panelTituloLayout = new javax.swing.GroupLayout(panelTitulo);
		panelTitulo.setLayout(panelTituloLayout);
		panelTituloLayout.setHorizontalGroup(
				panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE)
				);
		panelTituloLayout.setVerticalGroup(
				panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 64, Short.MAX_VALUE)
				);

		panelPrincipal.add(panelTitulo);
		panelTitulo.setBounds(0, 0, 200, 64);

		panelInfo.setBackground(new java.awt.Color(87, 115, 129));
		panelInfo.setMinimumSize(new java.awt.Dimension(0, 0));

		jLabelTiempo.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
		jLabelTiempo.setForeground(new java.awt.Color(255, 255, 255));
		jLabelTiempo.setToolTipText("");

		jLabelFallos.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
		jLabelFallos.setForeground(new java.awt.Color(255, 255, 255));

		javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
		panelInfo.setLayout(panelInfoLayout);
		panelInfoLayout.setHorizontalGroup(
				panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
						.addGap(173, 173, 173)
						.addComponent(jLabelFallos, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
						.addComponent(jLabelTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(173, 173, 173))
				);
		panelInfoLayout.setVerticalGroup(
				panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelInfoLayout.createSequentialGroup()
						.addGap(21, 21, 21)
						.addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabelFallos, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
								.addComponent(jLabelTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(28, Short.MAX_VALUE))
				);

		panelPrincipal.add(panelInfo);
		panelInfo.setBounds(209, 0, 770, 64);

		panelTablero.setBackground(new java.awt.Color(64, 64, 64));
		panelTablero.setToolTipText("");
		panelTablero.setAutoscrolls(true);
		panelTablero.setMinimumSize(new Dimension(692, 692));
        panelTablero.setMaximumSize(new Dimension(692, 692));
        panelTablero.setPreferredSize(new Dimension(692, 692));

		javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
		panelTablero.setLayout(panelTableroLayout);
		panelTableroLayout.setHorizontalGroup(
				panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 692, Short.MAX_VALUE)
				);
		panelTableroLayout.setVerticalGroup(
				panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 692, Short.MAX_VALUE)
				);

		panelPrincipal.add(panelTablero);
		panelTablero.setBounds(0, 70, 692, 692);

		panelBotones.setBackground(new java.awt.Color(87, 115, 129));
		panelBotones.setForeground(new java.awt.Color(186, 187, 188));
		panelBotones.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		botonNuevaPartida.setBackground(new java.awt.Color(18, 18, 18));
		botonNuevaPartida.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
		botonNuevaPartida.setForeground(new java.awt.Color(255, 255, 255));
		botonNuevaPartida.setText("NUEVA PARTIDA");
		botonNuevaPartida.setFocusPainted(false);
		botonNuevaPartida.setActionCommand("");
		botonNuevaPartida.setAlignmentY(0.0F);
		botonNuevaPartida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		botonNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonNuevaPartidaActionPerformed(evt);
			}
		});

		botonNuevoTablero.setBackground(new java.awt.Color(18, 18, 18));
		botonNuevoTablero.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
		botonNuevoTablero.setForeground(new java.awt.Color(255, 255, 255));
		botonNuevoTablero.setText("NUEVO TABLERO");
		botonNuevoTablero.setFocusPainted(false);
		botonNuevoTablero.setActionCommand("");
		botonNuevoTablero.setAlignmentY(0.0F);
		botonNuevoTablero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		botonNuevoTablero.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonNuevoTableroActionPerformed(evt);
			}
		});

		botonReiniciarPartida.setBackground(new java.awt.Color(18, 18, 18));
		botonReiniciarPartida.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
		botonReiniciarPartida.setForeground(new java.awt.Color(255, 255, 255));
		botonReiniciarPartida.setText("REINICIAR PARTIDA");
		botonReiniciarPartida.setFocusPainted(false);
		botonReiniciarPartida.setActionCommand("");
		botonReiniciarPartida.setAlignmentY(0.0F);
		botonReiniciarPartida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		botonReiniciarPartida.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonReiniciarPartidaActionPerformed(evt);
			}
		});

		botonGuardarPartida.setBackground(new java.awt.Color(18, 18, 18));
		botonGuardarPartida.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
		botonGuardarPartida.setForeground(new java.awt.Color(255, 255, 255));
		botonGuardarPartida.setText("GUARDAR PARTIDA");
		botonGuardarPartida.setFocusPainted(false);
		botonGuardarPartida.setActionCommand("");
		botonGuardarPartida.setAlignmentY(0.0F);
		botonGuardarPartida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		botonGuardarPartida.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonGuardarPartidaActionPerformed(evt);
			}
		});

		botonCargarPartida.setBackground(new java.awt.Color(18, 18, 18));
		botonCargarPartida.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
		botonCargarPartida.setForeground(new java.awt.Color(255, 255, 255));
		botonCargarPartida.setText("CARGAR PARTIDA");
		botonCargarPartida.setFocusPainted(false);
		botonCargarPartida.setActionCommand("");
		botonCargarPartida.setAlignmentY(0.0F);
		botonCargarPartida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		botonCargarPartida.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonCargarPartidaActionPerformed(evt);
			}
		});

		botonSolucionar.setBackground(new java.awt.Color(18, 18, 18));
		botonSolucionar.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
		botonSolucionar.setForeground(new java.awt.Color(255, 255, 255));
		botonSolucionar.setText("SOLUCIONAR");
		botonSolucionar.setFocusPainted(false);
		botonSolucionar.setActionCommand("");
		botonSolucionar.setAlignmentY(0.0F);
		botonSolucionar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		botonSolucionar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonSolucionarActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
		panelBotones.setLayout(panelBotonesLayout);
		panelBotonesLayout.setHorizontalGroup(
				panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelBotonesLayout.createSequentialGroup()
						.addContainerGap(27, Short.MAX_VALUE)
						.addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(botonSolucionar, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonCargarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonGuardarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonReiniciarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonNuevoTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(botonNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(27, Short.MAX_VALUE))
				);
		panelBotonesLayout.setVerticalGroup(
				panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelBotonesLayout.createSequentialGroup()
						.addGap(101, 101, 101)
						.addComponent(botonNuevaPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(38, 38, 38)
						.addComponent(botonNuevoTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(38, 38, 38)
						.addComponent(botonReiniciarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(38, 38, 38)
						.addComponent(botonGuardarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(38, 38, 38)
						.addComponent(botonCargarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(38, 38, 38)
						.addComponent(botonSolucionar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		botonNuevaPartida.getAccessibleContext().setAccessibleName("botonNuevaPartida");
		botonNuevaPartida.getAccessibleContext().setAccessibleDescription("");

		panelPrincipal.add(panelBotones);
		panelBotones.setBounds(698, 70, 280, 692);
		panelBotones.getAccessibleContext().setAccessibleDescription("");

		getContentPane().add(panelPrincipal, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>                        

	/**
	 * Acciones realizadas al presionar el boton para reiniciar la partida actual.
	 * @param evt evento de accion.
	 */
	private void botonReiniciarPartidaActionPerformed(ActionEvent evt) 
	{                                                      
		botonPresionado = false;
		Component[] components = panelTablero.getComponents();
		for (Component component : components) 
		{
			if (component instanceof JTextField textField) 
			{
				int fila = (panelTablero.getComponentZOrder(textField)) / 9;
				int col = (panelTablero.getComponentZOrder(textField)) % 9;
				
				if (!Juego_Sudoku.getInstancia().getTablero()[fila][col].isBase()) 
				{
					Juego_Sudoku.getInstancia().getTablero()[fila][col].setCorrecto(false);
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}
		}
		iniciarFallos();
	}                                                     

	/**
	 * Acciones realizadas al presionar el boton para guardar la partida actual.
	 * @param evt evento de accion.
	 */
	private void botonGuardarPartidaActionPerformed(ActionEvent evt) 
	{                                                    
		pausarTemporizador();

		// Obtener el nombre de la partida del usuario
		String rutaPartidas = System.getProperty("user.home") + File.separator + "Sudoku";

		// Crear un objeto JFileChooser
		JFileChooser selectorDeArchivo = new JFileChooser();
		File myFile = null;

		// Establecer la carpeta predeterminada para el JFileChooser
		if(InterfazDificultad.getInstancia().getDificultadSeleccionada() == "Facil")
		{
			myFile = new File(rutaPartidas + File.separator + "\\PartidasGuardadas\\ Nivel_Facil");
		}
		else if(InterfazDificultad.getInstancia().getDificultadSeleccionada() == "Medio")
		{
			myFile = new File(rutaPartidas + File.separator + "\\PartidasGuardadas\\ Nivel_Medio");
		}
		else if(InterfazDificultad.getInstancia().getDificultadSeleccionada() == "Dificil")
		{

			myFile = new File(rutaPartidas + File.separator + "\\PartidasGuardadas\\Nivel_Dificil");
		}
		
		String rutaCarpeta = myFile.getAbsolutePath();
		selectorDeArchivo.setCurrentDirectory(new File(rutaCarpeta));
		
		// Mostrar el cuadro de dialogo para seleccionar la ubicacion y el nombre del archivo
		int seleccion = selectorDeArchivo.showSaveDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) 
		{
			// Obtener el archivo seleccionado por el usuario
			File archivo = selectorDeArchivo.getSelectedFile();

			try 
			{
				FileWriter escritorDeArchivo = new FileWriter(archivo);
				BufferedWriter escritorBufferizado = new BufferedWriter(escritorDeArchivo);

				for (int i = 0; i < 9; i++) 
				{
					for (int j = 0; j < 9; j++) 
					{
						int valorDado = Juego_Sudoku.getInstancia().getTablero()[i][j].getValorDado();
						if (!Juego_Sudoku.getInstancia().getTablero()[i][j].isBase()) 
						{
							valorDado *= 10;
						}
						escritorBufferizado.write(Integer.toString(valorDado));
						escritorBufferizado.newLine();
					}
				}
				escritorBufferizado.write(Integer.toString(fallos));
				
				// Cerrar BufferedWriter
				escritorBufferizado.close();

				JOptionPane.showMessageDialog(this, "Partida guardada correctamente.");

			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al guardar la partida.");
			}
		} 
		else{
			tiempoInicial = System.currentTimeMillis() - tiempoPausado; // Restablece el tiempo inicial con el tiempo pausado
			timer.start(); // Reanuda el temporizador con el tiempo pausado
		} 
	}                                                   

	/**
	 * Acciones realizadas al presionar el boton para cargar una partida guardada.
	 * @param evt evento de accion.
	 */
	private void botonCargarPartidaActionPerformed(ActionEvent evt) 
	{                                                   
		pausarTemporizador();
		botonPresionado = false;
		
		int contador = 0, fallosAux = 0;
		
		String rutaPartidas = System.getProperty("user.home") + File.separator + "Sudoku";
		File myFile = new File(rutaPartidas + File.separator + "\\PartidasGuardadas");
		String rutaCarpeta = myFile.getAbsolutePath();
		
		JFileChooser selectorDeArchivo = new JFileChooser();
		selectorDeArchivo.setCurrentDirectory(new File(rutaCarpeta));
		selectorDeArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int seleccion = selectorDeArchivo.showOpenDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) 
		{
			File archivo = selectorDeArchivo.getSelectedFile();

			try 
			{
				BufferedReader lectorBufferizado = new BufferedReader(new FileReader(archivo));
				String linea;

				while ((linea = lectorBufferizado.readLine()) != null) 
				{
					for (int i = 0; i < 9; i++) 
					{
						for (int j = 0; j < 9; j++) 
						{
							int numero = Integer.parseInt(linea);
							if(numero >= 10 || numero == 0)
							{
								numero/=10;
								Juego_Sudoku.getInstancia().getTablero()[i][j].setValorBase(false);
								Juego_Sudoku.getInstancia().getTablero()[i][j].setValorBueno(0);
							}
							else
							{
								Juego_Sudoku.getInstancia().getTablero()[i][j].setValorBase(true);
								contador++;
								Juego_Sudoku.getInstancia().getTablero()[i][j].setValorBueno(numero);
							}
							Juego_Sudoku.getInstancia().getTablero()[i][j].setValorDado(numero);
							linea = lectorBufferizado.readLine();
						}
					}
					setFallos(Integer.parseInt(linea));
				}

				fallosAux = getFallos();
				
				if(contador == 37)	InterfazDificultad.getInstancia().setDificultadSeleccionada("Facil");
				else if(contador == 30) InterfazDificultad.getInstancia().setDificultadSeleccionada("Medio");
				else if(contador == 23) InterfazDificultad.getInstancia().setDificultadSeleccionada("Dificil");
				
				setTitle("SUDOKU : 'Nivel " + InterfazDificultad.getInstancia().getDificultadSeleccionada() + "'");
				lectorBufferizado.close();
				
				Juego_Sudoku.getInstancia().resolverSudoku();
				updateInterfaz();
				iniciarTemporizador();
				iniciarFallos();
				
				setFallos(fallosAux);
				actualizarNumeroFallos();
				JOptionPane.showMessageDialog(this, "Partida cargada correctamente.");

			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al cargar la partida.");
			}
		}
		else{
			tiempoInicial = System.currentTimeMillis() - tiempoPausado; // Restablece el tiempo inicial con el tiempo pausado
			timer.start(); // Reanuda el temporizador con el tiempo pausado
		}
	}                                                  

	/**
	 * Acciones realizadas al presionar el boton para solucionar el tablero de Sudoku.
	 * @param evt evento de accion.
	 */
	private void botonSolucionarActionPerformed(ActionEvent evt) 
	{                                                
		botonPresionado = true;
		for (int i = 0; i < 9; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				JTextField textField = (JTextField) panelTablero.getComponent(i * 9 + j);
				textField.setText(String.valueOf(Juego_Sudoku.getInstancia().getTablero()[i][j].getValorBueno()));
			}
		}
	}                                               

	/**
	 * Acciones realizadas al presionar el boton para iniciar una nueva partida.
	 * @param evt evento de accion.
	 */
	private void botonNuevaPartidaActionPerformed(ActionEvent evt) 
	{                                                  
		timer.stop();
		InterfazDificultad.crearNuevaInstancia().setVisible(true);
		((JFrame) SwingUtilities.getWindowAncestor((Component)evt.getSource())).dispose(); // Cerrar la ventana principal
	}                                                 

	/**
	 * Acciones realizadas al presionar el boton para generar un nuevo tablero de Sudoku.
	 * @param evt evento de accion.
	 */
	private void botonNuevoTableroActionPerformed(ActionEvent evt) 
	{                                                  
		timer.stop();
		generarNuevoSudokuConDificultad();
		iniciarTemporizador();
		iniciarFallos();
	}                                                 
}