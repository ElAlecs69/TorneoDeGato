package Paquetón;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Fase extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel etiquetaFase;

		public Fase() {
        setTitle("Fase del Torneo");
        setSize(300, 100);
        setLocation(830,20);  // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Evitar que se cierre
        setLayout(new BorderLayout());
        setUndecorated(true);

        etiquetaFase = new JLabel("fase", SwingConstants.CENTER);
        etiquetaFase.setForeground(Color.WHITE);  // Color del texto
        etiquetaFase.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaFase.setBorder(new LineBorder(Color.GRAY, 2, true));
        etiquetaFase.setBackground(new Color(50, 50, 50));  // Fondo oscuro
        etiquetaFase.setOpaque(true);

        add(etiquetaFase, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(30, 30, 30));  // Fondo oscuro
        setVisible(true);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Aquí puedes forzar la finalización del programa si es necesario
                System.out.println("Ventana cerrándose");
                dispose();
                System.exit(0);  // Forzar el cierre completo del programa
            }
        });
    }
    

    // Método para actualizar la fase del torneo
		public void actualizarFase(String fase) {

			if (fase.equals("")) {
				System.out.println("Cerrando la ventana de fase...");
		        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Establece que se cierre solo esta ventana
		        dispose();  // Cierra y libera los recursos de la ventana
		        return;
		    }

		    // Para las fases iniciales, simplemente actualiza el texto sin cambiar el tamaño
		    if (fase.equals("Octavos de Final")) {
		        etiquetaFase.setText(fase);  // Actualiza el texto del JLabel existente
		        etiquetaFase.setFont(new Font("Arial", Font.BOLD, 35));  // Cambia el tamaño de la fuente si es necesario
		        setSize(300, 100);  // Restablece el tamaño de la ventana a su valor inicial
		        setLocation(830, 20);  // Restablece la ubicación
		    }

		    else if (fase.equals("Cuartos de Final")) {
		        etiquetaFase.setText(fase);  // Actualiza el texto del JLabel existente
		        etiquetaFase.setFont(new Font("Arial", Font.BOLD, 35));  // Cambia el tamaño de la fuente si es necesario
		        setSize(300, 100);  // Restablece el tamaño de la ventana a su valor inicial
		        setLocation(835, 20);
		    }

		    // Para las fases avanzadas, cambia el diseño de la ventana
		    else if (fase.equals("Semifinal")) {
		        getContentPane().removeAll();  // Limpia los componentes anteriores

		        // Actualiza el JLabel con el nuevo texto y tamaño
		        etiquetaFase.setText(fase);
		        etiquetaFase.setFont(new Font("Arial", Font.BOLD, 125));  // Cambia la fuente para fases grandes
		        etiquetaFase.setBorder(new LineBorder(Color.GRAY, 2, true));

		        // Cambia el tamaño y posición de la ventana
		        setSize(600, 200);  // Tamaño mayor para las fases importantes
		        setLocation(695, 75);  // Posición ajustada

		        // Vuelve a añadir el JLabel actualizado
		        add(etiquetaFase, BorderLayout.CENTER);
		    }

		    else if (fase.equals("Final")) {
		        getContentPane().removeAll();  // Limpia los componentes anteriores

		        // Actualiza el JLabel con el nuevo texto y tamaño
		        etiquetaFase.setText(fase);
		        etiquetaFase.setFont(new Font("Arial", Font.BOLD, 225));  // Cambia la fuente para fases grandes
		        etiquetaFase.setBorder(new LineBorder(Color.GRAY, 2, true));

		        // Cambia el tamaño y posición de la ventana
		        setSize(600, 200);  // Tamaño mayor para las fases importantes
		        setLocation(675, 75);  // Posición ajustada

		        // Vuelve a añadir el JLabel actualizado
		        add(etiquetaFase, BorderLayout.CENTER);
		    }

		    // Actualiza la ventana para reflejar los cambios
		    revalidate();
		    repaint();
		}
}