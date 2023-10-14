package logica;

import java.util.ArrayList;

public class Lista {
  private Proceso procesoCajero;
  private int tamano = 0;

  public Lista() {
    this.procesoCajero = new Proceso(tamano);
    this.procesoCajero.setSiguiente(procesoCajero);
  }

  public void insertar() {
    tamano++;
    Proceso procesoNuevo = new Proceso(tamano);
    Proceso procesoAuxiliar = procesoCajero.getSiguiente();
    while (procesoAuxiliar.getSiguiente() != procesoCajero) {
      procesoAuxiliar = procesoAuxiliar.getSiguiente();
    }
    procesoAuxiliar.setSiguiente(procesoNuevo);
    procesoNuevo.setSiguiente(procesoCajero);
 
  }

  public ArrayList<Proceso> listarNodos(){
    ArrayList<Proceso> procesos = new ArrayList<Proceso>();
    Proceso procesoAuxiliar = procesoCajero.getSiguiente();
    while (procesoAuxiliar != procesoCajero) {
      procesos.add(procesoAuxiliar);
      procesoAuxiliar = procesoAuxiliar.getSiguiente();
    }
    return procesos;
  }

  public void atender(){
    if(procesoCajero.getSiguiente() != procesoCajero){

      Proceso procesoAtendido = procesoCajero.getSiguiente();

      int cantFacturas = procesoAtendido.getRafaga();

      if(cantFacturas>5){
        procesoAtendido.setRafaga(procesoAtendido.getRafaga()-5);
        procesoCajero.setSiguiente(procesoAtendido.getSiguiente());

        Proceso procesoAuxiliar = procesoCajero.getSiguiente();
        while (procesoAuxiliar.getSiguiente() != procesoCajero) {
          procesoAuxiliar = procesoAuxiliar.getSiguiente();
        }
        procesoAtendido.setSiguiente(procesoAuxiliar.getSiguiente());
        procesoAuxiliar.setSiguiente(procesoAtendido);;

      }
      else{
        procesoCajero.setSiguiente(procesoAtendido.getSiguiente());
        this.tamano--;
      }
    }
  }

  public int getTamano() {
    return tamano;
  }
}