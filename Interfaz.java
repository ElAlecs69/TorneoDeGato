package Paquetón;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

class GatoConInterfaz extends JFrame implements Runnable {
    
	private static final long serialVersionUID = 1L;
	private String jugador1;
    private String jugador2;
    private String ganador;
    private JButton[][] botones;
    private JLabel etiquetaGanador;
    private boolean juegoTerminado;
    private CountDownLatch latch;
    
    public GatoConInterfaz(String jugador1, String jugador2, int x, int y, CountDownLatch latch, String fase) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.ganador = "";
        this.juegoTerminado = false;
        this.latch = latch;
        
        setUndecorated(true);  // Elimina los bordes predeterminados
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);  // Agrega los botones de control personalizados
        JFrame.setDefaultLookAndFeelDecorated(true); 
        
        getRootPane().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        // Configurar la ventana
        setTitle("Juego de Gato: " + jugador1 + " vs " + jugador2);
        setSize(350, 400);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo de la ventana con un color profundo
        getContentPane().setBackground(new Color(30, 30, 30)); // Un gris oscuro elegante

        // Crear el tablero de Gato con un estilo moderno
        JPanel panelTablero = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
				super.paintComponent(g);
			    Graphics2D g2d = (Graphics2D) g;
			    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			    // Aplicar un degradado al fondo del tablero
			    GradientPaint gp = new GradientPaint(0, 0, Color.DARK_GRAY, getWidth(), getHeight(), Color.GRAY, true);
			    g2d.setPaint(gp);
			    g2d.fillRect(0, 0, getWidth(), getHeight());

			    // Si hay un ganador, dibujar la línea
			    if (!ganador.isEmpty()) {
			        g.setColor(Color.RED); // Color de la línea
			        String caso = DibujarRayaEn3(ganador.equals(jugador1) ? "X" : "O");
			        switch (caso) {
			            case "1": // Fila 1
			                g.drawLine(20, 50, getWidth() - 20, 50);
			                break;
			            case "2": // Fila 2
			                g.drawLine(20, getHeight() / 2, getWidth() - 20, getHeight() / 2);
			                break;
			            case "3": // Fila 3
			                g.drawLine(20, getHeight() - 50, getWidth() - 20, getHeight() - 50);
			                break;
			            case "4": // Columna 1
			                g.drawLine(50, 20, 50, getHeight() - 20);
			                break;
			            case "5": // Columna 2
			                g.drawLine(getWidth() / 2, 20, getWidth() / 2, getHeight() - 20);
			                break;
			            case "6": // Columna 3
			                g.drawLine(getWidth() - 50, 20, getWidth() - 50, getHeight() - 20);
			                break;
			            case "7": // Diagonal principal
			                g.drawLine(20, 20, getWidth() - 20, getHeight() - 20);
			                break;
			            case "8": // Diagonal secundaria
			                g.drawLine(getWidth() - 20, 20, 20, getHeight() - 20);
			                break;
			        }
			    }
  
            }
        };
        panelTablero.setLayout(new GridLayout(3, 3));
        botones = new JButton[3][3];

        // Colores y estilos para los botones
        Color botonColor = new Color(80, 80, 80);  // Color oscuro para los botones
        Color textoColor = Color.WHITE;  // Color blanco para el texto
        Color hoverColor = new Color(100, 100, 100);  // Color de hover

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton("");
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                botones[i][j].setForeground(textoColor);
                botones[i][j].setBackground(botonColor);
                botones[i][j].setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setOpaque(false);
                        button.setBorder(new LineBorder(Color.GRAY, 2, true)); // Borde redondeado con sombra
                        button.setContentAreaFilled(false);
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        AbstractButton button = (AbstractButton) c;
                        ButtonModel model = button.getModel();

                        // Cambiar color en hover
                        if (model.isRollover()) {
                            g2.setColor(hoverColor);
                        } else {
                            g2.setColor(button.getBackground());
                        }

                        // Dibujar el fondo con bordes redondeados
                        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
                panelTablero.add(botones[i][j]);
            }
        }

        // Estilizar la etiqueta del ganador
        etiquetaGanador = new JLabel(jugador1 + " vs " + jugador2, SwingConstants.CENTER);
        etiquetaGanador.setForeground(Color.WHITE);  // Texto en blanco
        etiquetaGanador.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaGanador.setBorder(new LineBorder(Color.GRAY, 1, true));
        etiquetaGanador.setBackground(new Color(50, 50, 50));  // Fondo oscuro
        etiquetaGanador.setOpaque(true);

        // Añadir componentes a la ventana
        add(panelTablero, BorderLayout.CENTER);
        add(etiquetaGanador, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void run() {
        Random random = new Random();
        String simboloActual;

        // Continuar jugando hasta que haya un ganador
        while (!juegoTerminado) {
            // Simular el juego de Gato
            for (int i = 0; i < 9 && !juegoTerminado; i++) {
                int fila = random.nextInt(3);
                int columna = random.nextInt(3);

                // Encontrar un botón vacío
                while (!botones[fila][columna].getText().equals("")) {
                    fila = random.nextInt(3);
                    columna = random.nextInt(3);
                }

                // Alternar entre los dos jugadores
                simboloActual = (i % 2 == 0) ? "X" : "O";
                botones[fila][columna].setText(simboloActual);

                // Verificar si alguien ganó después de cada jugada
                if (verificarGanador(simboloActual)) {
                    ganador = (simboloActual.equals("X")) ? jugador1 : jugador2;
                    etiquetaGanador.setText("Ganador: " + ganador);
                    etiquetaGanador.setForeground(Color.YELLOW);
                    juegoTerminado = true;
                    break;
                }

                // Pausar para simular el progreso del juego
                try {
                    Thread.sleep(1000); // Pausa de 1 segundo entre cada jugada
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Si no hubo ganador, es un empate, entonces reiniciar el tablero
            if (!juegoTerminado) {
                reiniciarTablero();
                etiquetaGanador.setText("Empate. Reiniciando partida...");
                etiquetaGanador.setForeground(Color.GREEN);
                try {
                    Thread.sleep(2000); 
                    etiquetaGanador.setText(jugador1 +" VS "+jugador2); // Pausa de 2 segundos antes de reiniciar
                    etiquetaGanador.setForeground(Color.WHITE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Notificar que este hilo ha terminado
        latch.countDown();

        // Esperar a que todos los juegos de la ronda terminen antes de cerrar la ventana
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Pausa adicional para que el resultado sea visible antes de cerrar la ventana
        try {
            Thread.sleep(3000); // 3 segundos extra para mostrar el ganador antes de cerrar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Cerrar la ventana
        dispose();
    }

    // Método para reiniciar el tablero
    private void reiniciarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText("");
            }
        }
    }

    // Método para verificar si el jugador ha ganado
    private boolean verificarGanador(String simbolo) {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (botones[i][0].getText().equals(simbolo) &&
                botones[i][1].getText().equals(simbolo) &&
                botones[i][2].getText().equals(simbolo)) {
                dibujarRayaEnFila(i);  // Dibuja una raya en la fila ganadora
                return true;
            }
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (botones[0][j].getText().equals(simbolo) &&
                botones[1][j].getText().equals(simbolo) &&
                botones[2][j].getText().equals(simbolo)) {
                dibujarRayaEnColumna(j);  // Dibuja una raya en la columna ganadora
                return true;
            }
        }

        // Verificar diagonales
        if (botones[0][0].getText().equals(simbolo) &&
            botones[1][1].getText().equals(simbolo) &&
            botones[2][2].getText().equals(simbolo)) {
            dibujarRayaEnDiagonalPrincipal();  // Dibuja una raya en la diagonal principal
            return true;
        }

        if (botones[0][2].getText().equals(simbolo) &&
            botones[1][1].getText().equals(simbolo) &&
            botones[2][0].getText().equals(simbolo)) {
            dibujarRayaEnDiagonalSecundaria();  // Dibuja una raya en la diagonal secundaria
            return true;
        }

        return false;
    }
    
    private String DibujarRayaEn3(String simbolo) {
    	String caso = null;
    	
    	if (botones[0][0].getText().equals(simbolo) &&
                botones[0][1].getText().equals(simbolo) &&
                botones[0][2].getText().equals(simbolo)) {
                caso = "1";    
            }
    	if (botones[1][0].getText().equals(simbolo) &&
                botones[1][1].getText().equals(simbolo) &&
                botones[1][2].getText().equals(simbolo)) {
                caso = "2";    
            }
    	if (botones[2][0].getText().equals(simbolo) &&
                botones[2][1].getText().equals(simbolo) &&
                botones[2][2].getText().equals(simbolo)) {
                caso = "3";    
            }
    	if (botones[0][0].getText().equals(simbolo) &&
                botones[1][0].getText().equals(simbolo) &&
                botones[2][0].getText().equals(simbolo)) {
                caso = "4";    
            }
    	if (botones[0][1].getText().equals(simbolo) &&
                botones[1][1].getText().equals(simbolo) &&
                botones[2][1].getText().equals(simbolo)) {
                caso = "5";    
            }
    	if (botones[0][2].getText().equals(simbolo) &&
                botones[1][2].getText().equals(simbolo) &&
                botones[2][2].getText().equals(simbolo)) {
                caso = "6";    
            }
    	if (botones[0][0].getText().equals(simbolo) &&
                botones[1][1].getText().equals(simbolo) &&
                botones[2][2].getText().equals(simbolo)) {
                caso = "7";    
            }
    	if (botones[0][2].getText().equals(simbolo) &&
                botones[1][1].getText().equals(simbolo) &&
                botones[2][0].getText().equals(simbolo)) {
                caso = "8";    
            }
    
    	return caso;
    }
    
    private void dibujarRayaEnFila(int fila) {
        for (int j = 0; j < 3; j++) {
            botones[fila][j].setBackground(Color.RED);  // Cambia el color de fondo de los botones ganadores
        }
    }
    
    private void dibujarRayaEnColumna(int columna) {
        for (int i = 0; i < 3; i++) {
            botones[i][columna].setBackground(Color.RED);  // Cambia el color de fondo de los botones ganadores
        }
    }
    
    private void dibujarRayaEnDiagonalPrincipal() {
        for (int i = 0; i < 3; i++) {
            botones[i][i].setBackground(Color.RED);  // Cambia el color de fondo de los botones ganadores
        }
    }
    
    private void dibujarRayaEnDiagonalSecundaria() {
        for (int i = 0; i < 3; i++) {
            botones[i][2 - i].setBackground(Color.RED);  // Cambia el color de fondo de los botones ganadores
        }
    }

    public String getGanador() {
        return ganador;
    }
}

public class Interfaz {
    public static void main(String[] args) throws InterruptedException {
    	try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar el torneo
        String[] jugadores = new String[16];
        for (int i = 0; i < 16; i++) {
            jugadores[i] = "Jugador " + (i + 1);
        }

        String campeon = iniciarTorneo(jugadores);
        JOptionPane.showMessageDialog(null, "El campeón del torneo es: " + campeon + "");
    }

    public static String iniciarTorneo(String[] jugadores) throws InterruptedException {
        
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Crear el indicador de fase antes de empezar el torneo
        Fase indicador = new Fase();

        while (jugadores.length > 1) {
            String fase;
            if (jugadores.length == 16) {
                fase = "Octavos de Final";
            } else if (jugadores.length == 8) {
                fase = "Cuartos de Final";
            } else if (jugadores.length == 4) {
                fase = "Semifinal";
            } else if (jugadores.length == 2) {
                fase = "Final";
            } else {
                fase = "Fin";
            }
            
            // Actualizar la ventana del indicador con la fase actual
            indicador.actualizarFase(fase);

            System.out.println("\n" + fase + " - Jugadores restantes: " + jugadores.length);
            String[] ganadores = new String[jugadores.length / 2];
            Thread[] juegos = new Thread[jugadores.length / 2];
            GatoConInterfaz[] partidas = new GatoConInterfaz[jugadores.length / 2];
            CountDownLatch latch = new CountDownLatch(jugadores.length / 2);

            int numFilas = (jugadores.length / 2 > 2) ? 2 : 1;
            int numColumnas = jugadores.length / (2 * numFilas);
            int espacioHorizontal = screenWidth / (numColumnas + 1);
            int espacioVertical = screenHeight / (numFilas + 1);

            for (int i = 0; i < jugadores.length / 2; i++) {
                int fila = i / numColumnas;
                int columna = i % numColumnas;
                int x = (columna + 1) * espacioHorizontal - 150;
                int y = (fila + 1) * (espacioVertical + 50) - 250;

                partidas[i] = new GatoConInterfaz(jugadores[2 * i], jugadores[2 * i + 1], x, y, latch, fase);
                juegos[i] = new Thread(partidas[i]);
                juegos[i].start();
            }

            for (int i = 0; i < juegos.length; i++) {
                juegos[i].join();
                ganadores[i] = partidas[i].getGanador();
            }

            jugadores = ganadores;
        }
        
        indicador.actualizarFase("");
        return jugadores[0];
    }
}