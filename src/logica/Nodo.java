package src.logica;

public class Nodo<T>{

    private T dato;

    private Nodo<T> siguiente;
    private int id;
    Nodo(int id){
        this.id=id;
    }
    public void setDato(T dato){ this.dato = dato; }

    public T getDato(){ return this.dato; }

    public void setSiguiente(Nodo<T> siguiente){ this.siguiente = siguiente; }
    public Nodo<T> getSiguiente(){ return this.siguiente; }

    public void setId(int id){ this.id = id; }

    public int getId(){ return this.id; }
}
