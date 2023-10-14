package logica;

public class Proceso {
  private Proceso siguiente;
  private String nombreProceso;
  private int idProceso,tiempoLlegada,rafaga,tiempoComienzo,tiempoFinal,tiempoRetorno,tiempoEspera;

  public Proceso(int id){
    this.idProceso = id;
    this.rafaga= (int)(Math.random()*20+1);
    this.tiempoLlegada = (int)(Math.random()*10+1);
  }

  public int getIdNodo() {
    return idProceso;
  }

  public void setIdProceso(int idProceso) {
    this.idProceso = idProceso;
  }

  public Proceso getSiguiente() {
    return siguiente;
  }

  public void setSiguiente(Proceso siguiente) {
    this.siguiente = siguiente;
  }

  public String getNombreProceso() {
    return nombreProceso;
  }

  public void setNombreProceso(String nombreProceso) {
    this.nombreProceso = nombreProceso;
  }

  public int getIdProceso() {
    return idProceso;
  }

  public int getTiempoLlegada() {
    return tiempoLlegada;
  }

  public void setTiempoLlegada(int tiempoLlegada) {
    this.tiempoLlegada = tiempoLlegada;
  }

  public int getRafaga() {
    return rafaga;
  }

  public void setRafaga(int rafaga) {
    this.rafaga = rafaga;
  }

  public int getTiempoComienzo() {
    return tiempoComienzo;
  }

  public void setTiempoComienzo(int tiempoComienzo) {
    this.tiempoComienzo = tiempoComienzo;
  }

  public int getTiempoFinal() {
    return tiempoFinal;
  }

  public void setTiempoFinal(int tiempoFinal) {
    this.tiempoFinal = tiempoFinal;
  }

  public int getTiempoRetorno() {
    return tiempoRetorno;
  }

  public void setTiempoRetorno(int tiempoRetorno) {
    this.tiempoRetorno = tiempoRetorno;
  }

  public int getTiempoEspera() {
    return tiempoEspera;
  }

  public void setTiempoEspera(int tiempoEspera) {
    this.tiempoEspera = tiempoEspera;
  }
}