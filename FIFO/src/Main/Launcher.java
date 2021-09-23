package Main;

import vista.Ventana;

public class Launcher {

    public static void main(String[] args) {
        try {
            Ventana v = new Ventana();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
