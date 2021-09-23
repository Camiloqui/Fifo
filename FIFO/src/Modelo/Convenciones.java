package Modelo;

public class Convenciones {

    private String[] convenciones;

    public Convenciones() {
        this.convenciones = new String[6];
        inicializarEstados();
    }

    public void inicializarEstados() {
        convenciones[0] = "-";
        convenciones[1] = "'";
        convenciones[2] = "*";
        convenciones[3] = "+";
        convenciones[4] = "!";
        convenciones[5] = "°";
    }

    public String[] getConvenciones() {
        return convenciones;
    }

}
