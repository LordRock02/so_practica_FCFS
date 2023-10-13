package src.logica;

import java.util.ArrayList;

public class Lista {
  private Nodo nodoCajero;
  private int tamano = 0;

  public Lista() {
    this.nodoCajero = new Nodo(tamano);
    this.nodoCajero.setSiguiente(nodoCajero);
  }

  public void insertar() {
    tamano++;
    Nodo nodoNuevo = new Nodo(tamano);
    Nodo nodoAuxiliar = nodoCajero.getSiguiente();
    while (nodoAuxiliar.getSiguiente() != nodoCajero) {
      nodoAuxiliar = nodoAuxiliar.getSiguiente();
    }
    nodoAuxiliar.setSiguiente(nodoNuevo);
    nodoNuevo.setSiguiente(nodoCajero);
 
  }

  public ArrayList<Nodo> listarNodos(){
    ArrayList<Nodo> nodos = new ArrayList<Nodo>();
    Nodo nodoAuxiliar = nodoCajero.getSiguiente();
    while (nodoAuxiliar != nodoCajero) {
      nodos.add(nodoAuxiliar);
      nodoAuxiliar = nodoAuxiliar.getSiguiente();
    }
    return nodos;
  }

  public void atender(){
    if(nodoCajero.getSiguiente() != nodoCajero){

      Nodo nodoAtendido = nodoCajero.getSiguiente();

      int cantFacturas = nodoAtendido.getCantFacturas();

      if(cantFacturas>5){
        nodoAtendido.setCantFacturas(nodoAtendido.getCantFacturas()-5);
        nodoCajero.setSiguiente(nodoAtendido.getSiguiente());

        Nodo nodoAuxiliar = nodoCajero.getSiguiente();
        while (nodoAuxiliar.getSiguiente() != nodoCajero) {
          nodoAuxiliar = nodoAuxiliar.getSiguiente();
        }
        nodoAtendido.setSiguiente(nodoAuxiliar.getSiguiente());
        nodoAuxiliar.setSiguiente(nodoAtendido);;

      }
      else{
        nodoCajero.setSiguiente(nodoAtendido.getSiguiente());
        this.tamano--;
      }
    }
  }

  public int getTamano() {
    return tamano;
  }
}