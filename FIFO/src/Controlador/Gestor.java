package controlador;

import LogicaColas.Cola;
import Modelo.Estados;
import Modelo.Convenciones;
import modelo.Proceso;
import vista.Ventana;
import static java.lang.Thread.sleep;
import java.util.ArrayList;

public class Gestor {

    private Ventana v;
    private Cola colaLlegada, colaListos, colaBloqueados, colaTerminados, colaZonaCritica;
    private int tiempo, nProcesosTerminados, nProcesos, velocidad, tiempoMaximoRafaga, tiempoMaximoLlegada, tiempoBloqueo;
    private Estados e;
    private Convenciones c;
    private ArrayList<Proceso> a;
    private ArrayList<String> gantt;
    private Proceso p;

    public Gestor(Ventana v) {
        this.v = v;
        this.colaLlegada = new Cola();
        this.colaListos = new Cola();
        this.colaBloqueados = new Cola();
        this.colaTerminados = new Cola();
        this.colaZonaCritica = new Cola();
        this.tiempo = 0;
        this.nProcesosTerminados = 0;
        this.nProcesos = 0;
        this.velocidad = 1000;
        this.tiempoMaximoRafaga = 50;
        this.tiempoMaximoLlegada = 10;
        this.tiempoBloqueo = 10;
        this.e = new Estados();
        this.c = new Convenciones();
        this.a = new ArrayList();
        this.gantt = new ArrayList();
        actualizarConvenciones();
    }

    public void crearProceso() {
        int tiempoLlegada, tiempoFinalizacionAnterior, tiempoLlegadaRafaga;
        tiempoLlegadaRafaga = (int) (Math.random() * this.tiempoMaximoRafaga) + 5;
        if (colaLlegada.colaVacia()) {
            tiempoLlegada = (int) (Math.random() * this.tiempoMaximoLlegada) + 1;
            tiempoFinalizacionAnterior = tiempoLlegada;
            a.add(new Proceso(this.nProcesos, tiempoLlegada, tiempoLlegadaRafaga, 0, e.getEstados()[0]));
        } else {
            tiempoLlegada = (int) (Math.random() * this.tiempoMaximoLlegada) + colaLlegada.getUltimo().getTiempoLlegada();
            tiempoFinalizacionAnterior = colaLlegada.getUltimo().getTiempoFinalizacion();
            a.add(new Proceso(this.nProcesos, tiempoLlegada, tiempoLlegadaRafaga, colaLlegada.getUltimo().getTiempoFinalizacion(), e.getEstados()[0]));
        }
        if (this.nProcesos < 10) {
            gantt.add("P" + this.nProcesos + "  ");
        } else {
            gantt.add("P" + this.nProcesos + " ");
        }
        gantt();
        colaLlegada.insertar(a.get(a.size() - 1));
        this.nProcesos++;
    }

    public void gantt() {
        String s = "";
        for (int i = 0; i <= this.tiempo; i++) {
            s += c.getConvenciones()[5];
        }
        gantt.set(gantt.size() - 1, gantt.get(gantt.size() - 1) + s);
    }

    public boolean bloquearProceso(int id) {
        p = colaListos.bloquearProceso(id);
        if (p != null) {
            p.setEstado(e.getEstados()[4]);
            a.get(p.getId()).setEstado(e.getEstados()[4]);
            p.setTiempoBloqueo(this.tiempoBloqueo);
            colaBloqueados.insertar(p);
            return true;
        }
        p = colaZonaCritica.bloquearProceso(id);
        if (p != null) {
            p.setEstado(e.getEstados()[4]);
            a.get(p.getId()).setEstado(e.getEstados()[4]);
            p.setTiempoBloqueo(this.tiempoBloqueo);
            colaBloqueados.insertar(p);
            return true;
        }
        return false;
    }

    public void actualizarConvenciones() {
        String s;
        s = "Tiempo de llegada:       " + c.getConvenciones()[0] + "\n"
                + "Proceso en espera:       " + c.getConvenciones()[1] + "\n"
                + "Proceso en zona critica: " + c.getConvenciones()[2] + "\n"
                + "Proceso terminado:       " + c.getConvenciones()[3] + "\n"
                + "Proceso bloqueado:       " + c.getConvenciones()[4] + "\n"
                + "Tiempo muerto:           " + c.getConvenciones()[5] + "";
        v.getjTextArea6().setText(s);
    }

    public String pintarGantt() {
        String string = "";
        for (int i = 0; i < this.nProcesos; i++) {
            String estado = a.get(i).getEstado();
            switch (estado) {
                case "Llegando": {
                    gantt.set(i, gantt.get(i) + c.getConvenciones()[0]);
                    break;
                }
                case "En Espera": {
                    gantt.set(i, gantt.get(i) + c.getConvenciones()[1]);
                    break;
                }
                case "En Zona Critica": {
                    gantt.set(i, gantt.get(i) + c.getConvenciones()[2]);
                    break;
                }
                case "Terminado": {
                    gantt.set(i, gantt.get(i) + c.getConvenciones()[3]);
                    break;
                }
                case "Bloqueado": {
                    gantt.set(i, gantt.get(i) + c.getConvenciones()[4]);
                    break;
                }
            }
            if (i < this.nProcesos - 1) {
                string = string + gantt.get(i) + "\n";
            } else {
                string = string + gantt.get(i);
            }
        }
        return string;
    }

    public void actualizarVentana() {
        v.getjTextField2().setText(Integer.toString(this.tiempo));
        v.getjTextArea1().setText(colaListos.imprimir());
        v.getjTextArea2().setText(colaBloqueados.imprimirColaBloqueados());
        v.getjTextArea3().setText(colaTerminados.imprimir());
        v.getjTextArea4().setText(colaZonaCritica.imprimirColaZonaCritica());
        v.getjTextArea5().setText("");
        v.getjTextArea5().setText(pintarGantt());
    }

    public void gestionarColaBloqueados() {
        if (!colaBloqueados.colaVacia()) {
            p = colaBloqueados.getRaiz();
            p.setTiempoBloqueo(p.getTiempoBloqueo() - 1);
            if (p.getTiempoBloqueo() == 0) {
                p.setEstado(e.getEstados()[1]);
                a.get(p.getId()).setEstado(e.getEstados()[1]);
                colaListos.insertar(colaBloqueados.atender());
                gestionarProcesosEnEspera();
            }
        }
    }

    public void gestionarProcesosLlegada() {
        if (!colaLlegada.colaVacia()) {
            for (int i = 0; i < colaLlegada.getTamanoCola(); i++) {
                p = colaLlegada.buscarProceso(i);
                p.setTiempoLlegadaCopia(p.getTiempoLlegadaCopia() - 1);
                if (p.getTiempoLlegadaCopia() == 0) {
                    if (colaZonaCritica.colaVacia()) {
                        p.setEstado(e.getEstados()[2]);
                        a.get(p.getId()).setEstado(e.getEstados()[2]);
                        colaZonaCritica.insertar(colaLlegada.atender());
                    } else {
                        p.setEstado(e.getEstados()[1]);
                        a.get(p.getId()).setEstado(e.getEstados()[1]);
                        colaListos.insertar(colaLlegada.atender());
                    }
                    i = -1;
                }
            }
        }
    }

    public void gestionarProcesosEnEspera() {
        if (colaZonaCritica.colaVacia()) {
            if (!colaListos.colaVacia()) {
                p = colaListos.getRaiz();
                p.setEstado(e.getEstados()[2]);
                a.get(p.getId()).setEstado(e.getEstados()[2]);
                colaZonaCritica.insertar(colaListos.atender());
            }
        }
    }

    public void gestionarProcesosZonaCritica() {
        if (!colaZonaCritica.colaVacia()) {
            p = colaZonaCritica.getRaiz();
            p.setTiempoRafaga(p.getTiempoRafaga() - 1);
            if (p.getTiempoRafaga() == 0) {
                p.setEstado(e.getEstados()[3]);
                a.get(p.getId()).setEstado(e.getEstados()[3]);
                colaTerminados.insertar(colaZonaCritica.atender());
                gestionarProcesosEnEspera();
                this.nProcesosTerminados++;
            }
        }
    }

    public void FIFO() {
        while (this.nProcesosTerminados < this.nProcesos) {
            this.tiempo++;
            gestionarProcesosLlegada();
            gestionarProcesosEnEspera();
            gestionarProcesosZonaCritica();
            gestionarColaBloqueados();
            actualizarVentana();
            try {
                sleep(this.velocidad);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        v.setSimulacionTerminada(true);
        v.simulacionTerminada();
    }
}
