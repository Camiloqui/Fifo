package modelo;

public class Proceso {

    private int id;
    private int tiempoLlegada;
    private int tiempoRafaga;
    private int tiempoEspera;
    private int tiempoRetorno;
    private int tiempoFinalizacion;
    private int tiempoBloqueo;
    private int tiempoLlegadaCopia;
    private String estado;
    private Proceso siguiente;

    public Proceso(int id, int tiempoLlegada, int tiempoRafaga, int tiempoFinalizacionAnterior, String estado) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoLlegadaCopia = tiempoLlegada;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoBloqueo = 0;
        this.estado = estado;
        calcularTiempos(tiempoFinalizacionAnterior);
    }

    public void calcularTiempos(int TiempoFinalizacionAnterior) {
        if (this.tiempoLlegada > TiempoFinalizacionAnterior) {
            this.tiempoRetorno = tiempoLlegada;
            this.tiempoEspera = 0;
        } else {
            this.tiempoRetorno = TiempoFinalizacionAnterior;
            this.tiempoEspera = TiempoFinalizacionAnterior - this.tiempoLlegada;
        }
        this.tiempoFinalizacion = this.tiempoRetorno + this.tiempoRafaga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getTiempoRafaga() {
        return tiempoRafaga;
    }

    public void setTiempoRafaga(int tiempoRafaga) {
        this.tiempoRafaga = tiempoRafaga;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public int getTiempoRetorno() {
        return tiempoRetorno;
    }

    public void setTiempoRetorno(int tiempoRetorno) {
        this.tiempoRetorno = tiempoRetorno;
    }

    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public void setTiempoFinalizacion(int tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public int getTiempoBloqueo() {
        return tiempoBloqueo;
    }

    public void setTiempoBloqueo(int tiempoBloqueo) {
        this.tiempoBloqueo = tiempoBloqueo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Proceso getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Proceso siguiente) {
        this.siguiente = siguiente;
    }

    public int getTiempoLlegadaCopia() {
        return tiempoLlegadaCopia;
    }

    public void setTiempoLlegadaCopia(int tiempoLlegadaCopia) {
        this.tiempoLlegadaCopia = tiempoLlegadaCopia;
    }
}
