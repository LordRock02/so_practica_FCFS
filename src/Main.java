package src;

import src.logica.Cola;
import src.logica.Nodo;
import src.logica.Proceso;

import java.util.ArrayList;

public class Main {
    public static void main(String args []){
        Cola<Proceso> colaProcesos = new Cola<Proceso>();
        ArrayList<Proceso> procesos;
        for(int i=0; i<5; i++){
            colaProcesos.insertar(new Proceso(i));
        }
        System.out.println(colaProcesos.getTamano());
        procesos = colaProcesos.listar();
        System.out.println("\n\nse atendio al proceso:\n\n");
        System.out.println(colaProcesos.atender().getNombreProceso());;
        System.out.println("tamano " + colaProcesos.getTamano()+"\n\n");
        procesos = colaProcesos.listar();
        for(int i=0; i<procesos.size(); i++){
            System.out.println(procesos.get(i).getNombreProceso());
        }
    }




}
