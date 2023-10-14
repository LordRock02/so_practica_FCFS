package src.logica;

import java.util.ArrayList;

public class Cola<T>{
    private Nodo<T> cabeza;

    private int tamano = 0;

    public Cola(){
    }

    public void insertar(T dato){
        Nodo<T> nodo = new Nodo<T>(dato, this.tamano++);
        nodo.setSiguiente(cabeza);
        if(this.cabeza==null){
            this.cabeza = nodo;
            this.cabeza.setSiguiente(nodo);
        }else{
            Nodo<T> nodoAuxiliar = this.cabeza;
            while(nodoAuxiliar.getSiguiente()!=this.cabeza){
                nodoAuxiliar=nodoAuxiliar.getSiguiente();
            }
            nodoAuxiliar.setSiguiente(nodo);
        }
    }
    public T atender(){
        Nodo<T> nodoAtendido = this.cabeza;
        Nodo<T> nodoAuxiliar = this.cabeza.getSiguiente();
        if(this.cabeza.getSiguiente()==this.cabeza){
            this.cabeza=null;
        }else{
            while(nodoAuxiliar.getSiguiente()!=this.cabeza){
                nodoAuxiliar=nodoAuxiliar.getSiguiente();
            }
            this.cabeza=nodoAtendido.getSiguiente();
            nodoAuxiliar.setSiguiente(this.cabeza);
        }
        tamano--;
        return nodoAtendido.getDato();
    }
    public ArrayList<T> listar(){
        ArrayList<T> lista = new ArrayList<T>();
        Nodo<T> nodoAuxiliar = this.cabeza;
        if(this.cabeza!=null){
            do{
                lista.add(nodoAuxiliar.getDato());
                nodoAuxiliar = nodoAuxiliar.getSiguiente();
            }while(nodoAuxiliar!=this.cabeza);
        }
        return lista;
    }
    public int getTamano(){
        return this.tamano;
    }

}
