package src.logica;

public class Nodo{
  private int idNodo;
  private String proceso;

  private double tiempoLlegada;

  private double tiempoComienzo;

  private double tiempoFinal;

  private double tiempoRetorno;

  private double tiempoEspera;

  private double rafaga;
  private Nodo siguiente;

  private Nodo anterior;

  public Nodo(int id){
    this.idNodo = id;
    this.proceso = "Proceso #"+this.idNodo;
    this.tiempoLlegada = id;
  }

  public int getIdNodo() {
    return idNodo;
  }

  public void setIdNodo(int idNodo) {
    this.idNodo = idNodo;
  }

  public String getProceso() {
    return proceso;
  }

  public void setProceso(String proceso) {
    this.proceso = proceso;
  }

  public Nodo getSiguiente() {
    return siguiente;
  }

  public void setSiguiente(Nodo siguiente) {
    this.siguiente = siguiente;
  }

  public Nodo getAnterior(){ return this.anterior; }

  public void setAnterior(Nodo anterior){ this.anterior = anterior; }

  public void setTiempoLlegada(double tiempoLlegada){ this.tiempoLlegada = tiempoLlegada; }

  public double getTiempoLlegada() { return tiempoLlegada; }

  public void setRafaga(double rafaga) { this.rafaga = rafaga; }

  public double getRafaga() { return this.rafaga; }

  public void setTiempoComienzo(double tiempoComienzo){ this.tiempoComienzo = tiempoComienzo; }

  public void setTiempoFinal(double tiempoFinal){ this.tiempoFinal = tiempoFinal; }

  public double getTiempoFinal(){ return this.tiempoFinal; }

  public void setTiempoRetorno(double tiempoRetorno){ this.tiempoRetorno = tiempoRetorno; }

  public double getTiempoRetorno(){ return this.tiempoRetorno; }

  public void setTiempoEspera(double tiempoEspera){ this.tiempoEspera=tiempoEspera; }

}