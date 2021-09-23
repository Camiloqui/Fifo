package LogicaColas;

import modelo.Proceso;

public class Cola {

    private Proceso raiz, anterior, ultimo;
    private int tamanoCola;

    public Cola() {
        this.raiz = null;
        this.ultimo = null;
        this.tamanoCola = 0;
    }

    public void insertar(Proceso proceso) {
        proceso.setSiguiente(null);
        if (this.raiz == null) {
            this.raiz = proceso;
            this.ultimo = this.raiz;
        } else {
            this.ultimo.setSiguiente(proceso);
            this.ultimo = proceso;
        }
        this.tamanoCola++;
    }

    public String imprimir() {
        if (colaVacia()) {
            return "Cola Vacia";
        }
        Proceso temp = this.raiz;
        String cola = "";
        cola += "Proceso\t" + "Tiempo de llegada\t" + "Tiempo de rafaga\t" + "Tiempo de espera\t"
                + "Tiempo de retorno\t" + "Tiempo de finalizacion\t" + "Estado\n";
        while (temp != null) {
            if (temp.getSiguiente() != null) {
                cola += "P" + temp.getId() + "\t";
                cola += temp.getTiempoLlegada() + "\t\t";
                cola += temp.getTiempoRafaga() + "\t\t";
                cola += temp.getTiempoEspera() + "\t\t";
                cola += temp.getTiempoRetorno() + "\t\t";
                cola += temp.getTiempoFinalizacion() + "\t\t";
                cola += temp.getEstado() + "\n";
                temp = temp.getSiguiente();
            } else {
                cola += "P" + temp.getId() + "\t";
                cola += temp.getTiempoLlegada() + "\t\t";
                cola += temp.getTiempoRafaga() + "\t\t";
                cola += temp.getTiempoEspera() + "\t\t";
                cola += temp.getTiempoRetorno() + "\t\t";
                cola += temp.getTiempoFinalizacion() + "\t\t";
                cola += temp.getEstado();
                temp = temp.getSiguiente();
            }
        }
        return cola;
    }

    public String imprimirColaBloqueados() {
        if (colaVacia()) {
            return "Cola Vacia";
        }
        Proceso temp = this.raiz;
        String cola = "";
        cola += "Proceso\t" + "Tiempo de bloqueo\t" + "Estado\n";
        while (temp != null) {
            if (temp.getSiguiente() != null) {
                cola += "P" + temp.getId() + "\t";
                cola += temp.getTiempoBloqueo() + "\t\t";
                cola += temp.getEstado() + "\n";
                temp = temp.getSiguiente();
            } else {
                cola += "P" + temp.getId() + "\t";
                cola += temp.getTiempoBloqueo() + "\t\t";
                cola += temp.getEstado();
                temp = temp.getSiguiente();
            }
        }
        return cola;
    }

    public String imprimirColaZonaCritica() {
        if (colaVacia()) {
            return "Zona Critica Vacia";
        }
        Proceso temp = this.raiz;
        String cola = "";
        cola += "Proceso\t" + "Tiempo rafaga\t\t" + "Estado\n";
        cola += "P" + temp.getId() + "\t";
        cola += temp.getTiempoRafaga() + "\t\t";
        cola += temp.getEstado();
        return cola;
    }

    public Proceso atender() {
        Proceso p = this.raiz;
        this.raiz = this.raiz.getSiguiente();
        if (this.raiz == null) {
            this.ultimo = this.raiz;
        }
        this.tamanoCola--;
        return p;
    }

    public boolean colaVacia() {
        return this.raiz == null;
    }

    public Proceso buscarProceso(int j) {
        Proceso p = this.raiz;
        for (int i = 0; i < j; i++) {
            p = p.getSiguiente();
        }
        return p;
    }

    public Proceso buscarProcesoID(int id) {
        Proceso p = this.raiz;
        this.anterior = p;
        for (int i = 0; i < tamanoCola; i++) {
            if (p.getId() == id) {
                return p;
            }
            this.anterior = p;
            p = p.getSiguiente();
        }
        return null;
    }

    public Proceso bloquearProceso(int id) {
        Proceso p = buscarProcesoID(id);
        if (p != null) {
            if (p == this.raiz) {
                this.raiz = this.raiz.getSiguiente();
            } else if (p == this.ultimo) {
                anterior.setSiguiente(null);
                this.ultimo = anterior;
            } else {
                anterior.setSiguiente(p.getSiguiente());
            }
            this.tamanoCola--;
            return p;
        }
        return null;
    }

    public Proceso getRaiz() {
        return this.raiz;
    }

    public Proceso getUltimo() {
        return ultimo;
    }

    public int getTamanoCola() {
        return tamanoCola;
    }
}
