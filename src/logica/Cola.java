package src.logica;

import java.util.ArrayList;

public class Cola<T>{
    private Nodo<T> cabeza;

    private int tamano = 0;

    Cola(){
        this.cabeza = new Nodo<T>(tamano);
        this.cabeza.setSiguiente(this.cabeza);
    }

    public void insertar(Nodo<T> nodo){
        tamano++;
        Nodo<T> nodoAuxiliar = this.cabeza;
        while(nodoAuxiliar.getSiguiente()!=this.cabeza){
            nodoAuxiliar=nodoAuxiliar.getSiguiente();
        }
        nodoAuxiliar.setSiguiente(nodo);
        nodo.setSiguiente(this.cabeza);
    }
    public Nodo<T> atender(){
        Nodo<T> nodoAtendido = this.cabeza;
        Nodo<T> nodoAuxiliar = nodoAtendido;
        if(nodoAtendido.getSiguiente()!=this.cabeza){
            this.cabeza=nodoAtendido.getSiguiente();
            while(nodoAtendido.getSiguiente()!=this.cabeza){
                nodoAuxiliar=nodoAuxiliar.getSiguiente();
            }
            nodoAuxiliar.setSiguiente(nodoAtendido);
            tamano--;
        }
        return nodoAtendido;
    }
    public ArrayList<Nodo<T>> listar(){
        ArrayList<Nodo<T>> lista = new ArrayList<Nodo<T>>();
        Nodo<T> nodoAuxiliar = this.cabeza;
        while(nodoAuxiliar.getSiguiente()!=this.cabeza){
            lista.add(nodoAuxiliar);
            nodoAuxiliar = nodoAuxiliar.getSiguiente();
        }
        return lista;
    }


}
