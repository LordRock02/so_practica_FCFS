package src.logica;

public class Proceso{

    private String nombreProceso;
    private int estado;
    private double tiempoLlegada;
    private double rafaga;
    private double tiempoComienzo;
    private double tiempoFinal;
    private double tiempoRetorno;
    private double tiempoEspera;

    public static int NUEVO = 0, LISTO = 1, EJECUCION = 2, BLOQUEADO = 3, TERMINADO = 4;

    Proceso(int id) {
        this.nombreProceso="Proceso# " + id;
        this.estado = NUEVO;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(double tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public double getRafaga() {
        return rafaga;
    }

    public void setRafaga(double rafaga) {
        this.rafaga = rafaga;
    }

    public double getTiempoComienzo() {
        return tiempoComienzo;
    }

    public void setTiempoComienzo(double tiempoComienzo) {
        this.tiempoComienzo = tiempoComienzo;
    }

    public double getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(double tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public double getTiempoRetorno() {
        return tiempoRetorno;
    }

    public void setTiempoRetorno(double tiempoRetorno) {
        this.tiempoRetorno = tiempoRetorno;
    }

    public double getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(double tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
}
