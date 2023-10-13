package src.logica;

public class Nodo{
  private int idNodo;
  private int cantFacturas;
  private String nombrePersona;
  private Nodo siguiente;

  public Nodo(int id){
    this.idNodo = id;
    this.nombrePersona = "Cliente #"+this.idNodo;
    this.cantFacturas = (int)(Math.random()*20+1);
  }

  public int getIdNodo() {
    return idNodo;
  }

  public void setIdNodo(int idNodo) {
    this.idNodo = idNodo;
  }

  public String getNombrePersona() {
    return nombrePersona;
  }

  public void setNombrePersona(String nombrePersona) {
    this.nombrePersona = nombrePersona;
  }

  public Nodo getSiguiente() {
    return siguiente;
  }

  public void setSiguiente(Nodo siguiente) {
    this.siguiente = siguiente;
  }

  public int getCantFacturas() {
    return cantFacturas;
  }

  public void setCantFacturas(int cantFacturas) {
    this.cantFacturas = cantFacturas;
  }
}