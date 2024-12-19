package Paquetón;

import java.util.Random;

class Gato implements Runnable {
    private String jugador1;
    private String jugador2;
    private String ganador;

    public Gato(String jugador1, String jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public void run() {
        // Simulación del juego de Gato
        Random random = new Random();
        int resultado = random.nextInt(2); // Resultado aleatorio: 0 o 1
        ganador = (resultado == 0) ? jugador1 : jugador2;
        
        // Simulación de duración del juego
        try {
            Thread.sleep(1000); // Cada juego toma 1 segundo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Juego entre " + jugador1 + " y " + jugador2 + ". Ganador: " + ganador);
    }

    public String getGanador() {
        return ganador;
    }
}
