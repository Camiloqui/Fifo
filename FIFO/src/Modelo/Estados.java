package Modelo;

public class Estados {

    private String[] estados;

    public Estados() {
        this.estados = new String[5];
        inicializarEstados();
    }

    public void inicializarEstados() {
        estados[0] = "Llegando";
        estados[1] = "En Espera";
        estados[2] = "En Zona Critica";
        estados[3] = "Terminado";
        estados[4] = "Bloqueado";
    }

    public String[] getEstados() {
        return estados;
    }

}
