package Paquetón;

public class TorneoDeLaFuerza {
    public static void main(String[] args) throws InterruptedException {
        // Crear los 16 jugadores
        String[] jugadores = new String[16];
        for (int i = 0; i < 16; i++) {
            jugadores[i] = "Jugador " + (i + 1);
        }

        // Iniciar el torneo
        String campeon = iniciarTorneo(jugadores);
        System.out.println("\n¡El campeón del torneo es: " + campeon + "!");
    }

    public static String iniciarTorneo(String[] jugadores) throws InterruptedException {
        // Rondas del torneo
        int ronda = 1;

        while (jugadores.length > 1) {
            System.out.println("\nRonda " + ronda + " - Jugadores restantes: " + jugadores.length);
            String[] ganadores = new String[jugadores.length / 2];
            Thread[] juegos = new Thread[jugadores.length / 2];
            Gato[] partidas = new Gato[jugadores.length / 2];

            // Enfrentamientos en parejas
            for (int i = 0; i < jugadores.length / 2; i++) {
                partidas[i] = new Gato(jugadores[2 * i], jugadores[2 * i + 1]);
                juegos[i] = new Thread(partidas[i]);
                juegos[i].start();
            }

            // Esperar a que todos los juegos terminen
            for (int i = 0; i < juegos.length; i++) {
                juegos[i].join();
                ganadores[i] = partidas[i].getGanador();
            }

            // Los ganadores avanzan a la siguiente ronda
            jugadores = ganadores;
            ronda++;
        }

        return jugadores[0]; // El último jugador es el campeón
    }
}