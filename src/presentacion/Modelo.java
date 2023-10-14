package presentacion;

import logica.Lista;
import logica.Proceso;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Modelo {

    private Lista listaTurnos = new Lista();
    private VentanaPrincipal ventanaPrincipal;

    Thread hiloAtender;
    Thread hiloPintarTabla;
    Thread hiloPintarCola;

    int cicloReloj = 200;
    boolean sistemaActivo = false;

    private int[][] coordenadas = {
            {270,470},
            {440,470},
            {610,470},
            {780,470},

            {780,270},
            {610,270},
            {440,270},
            {270,270},
            {100,270},

            {100,70},
            {270,70},
            {440,70},
            {610,70},
            {780,70}
    };

    public void iniciar(){
        getVistaPrincipal().setSize(1920, 1080);
        getVistaPrincipal().setVisible(true);
        getVistaPrincipal().setLocationRelativeTo(null);

        getVistaPrincipal().getPanelCola().setBackground(Color.red);
        getVistaPrincipal().getPanelTabla().setBackground(Color.blue);
        this.insertarClientesIniciales();

    }

    public void insertarClientesIniciales(){
        int cantClientes = (int)(Math.random()*10+1);
        for(int i=0;i<cantClientes;i++){
            this.listaTurnos.insertar();
        }
        this.pintarCola();
        this.pintarTabla();
    }

    public void iniciarAtencion(){
        hiloAtender = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo()){
                    try {
                        Thread.sleep(100); // Agregar un retraso de 200 ms entre cada elemento
                        System.out.println("Atendiendo cada 100 ms");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hiloAtender.start();
    }

    public void iniciarPintarCola(){
        hiloPintarCola = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo()){
                    try {
                        Thread.sleep(200); // Agregar un retraso de 200 ms entre cada elemento
                        System.out.println("Pintando cola cada 200 ms");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hiloPintarCola.start();
    }

    public void iniciarPintarTabla(){
        hiloPintarTabla = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo()){
                    try {
                        Thread.sleep(300); // Agregar un retraso de 200 ms entre cada elemento
                        System.out.println("Pintando tabla cada 300 ms");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hiloPintarTabla.start();
    }

    public void agregarCliente(){
        int cantidadAgregar = Integer.parseInt(this.getVistaPrincipal().getTxtCantClientes().getText());
        for(int i=0;i<cantidadAgregar;i++){
            this.listaTurnos.insertar();
        }
        this.pintarTabla();
        this.pintarCola();
    }

    public void atenderCliente(){
        int cantidadAtender = Integer.parseInt(this.getVistaPrincipal().getTxtCantAtender().getText());
        for(int i=0;i<cantidadAtender;i++){
            this.listaTurnos.atender();
        }
        this.pintarTabla();
        this.pintarCola();
    }

    public void pintarTabla(){

        ArrayList<Proceso> procesos = listaTurnos.listarNodos();
        this.getVistaPrincipal().getModelTable().setRowCount(0);

        for(int i = 0; i< procesos.size(); i++){
            Proceso proceso = procesos.get(i);
            this.getVistaPrincipal().getModelTable().addRow(new Object[]{
                    proceso.getIdNodo(),
                    proceso.getTiempoLlegada(),
                    proceso.getRafaga()
            });
        }
    }

    public void pintarCola(){
        getVistaPrincipal().getPanelCola().removeAll();

        JLabel labelCajero = new JLabel();
        labelCajero.setBounds(60,450,140,150);
        labelCajero.setOpaque(true);
        //labelCajero.setBackground(Color.blue);
        labelCajero.setIcon(new ImageIcon(getClass().getResource("/imagenes/atm.png")));

        getVistaPrincipal().getPanelCola().add(labelCajero);


        ArrayList<Proceso> procesos = listaTurnos.listarNodos();
        int cantClientes = listaTurnos.getTamano();

        if(cantClientes>14){
            cantClientes=14;
        }

        for (int i = 0; i < cantClientes; i++) {
            Proceso proceso = procesos.get(i);
            String labelText = "" +
                    "<html>" +
                    "<div style='width:80px;height:85px;display:flex;flex-direction:column;align-items:space-between;justify-content:space-between;background-color:#D9D9D9;padding:5px;border-radius:5px;'>"+
                        "<p style='text-align:center;margin:0;font-size:9px;'>"+ proceso.getIdNodo()+""+"</p>"+
                        "<img style='margin-left:40px;' src="+ "'"+getClass().getResource("/imagenes/persona.png")+"'>" + "</img>"+
                        "<p style='text-align:center;margin:0;font-size:9px;'>"+ proceso.getNombreProceso()+""+"</p>" +
                        "<p style='text-align:center;margin:0;font-size:9px;'>"+ proceso.getRafaga()+" Facturas"+"</p>" +
                    "</div>"+
                    "</html>";

            JLabel label = new JLabel(labelText);
            label.setBounds(coordenadas[i][0],coordenadas[i][1],100,110);
            label.setOpaque(true);
            label.setBackground(Color.lightGray);
            this.getVistaPrincipal().getPanelCola().add(label);
        }
        getVistaPrincipal().getPanelCola().repaint();

    }

    public boolean isSistemaActivo() {
        return sistemaActivo;
    }

    public void setSistemaActivo(boolean sistemaActivo) {
        this.sistemaActivo = sistemaActivo;
    }

    public VentanaPrincipal getVistaPrincipal() {
        if(ventanaPrincipal == null){
            ventanaPrincipal = new VentanaPrincipal(this);
        }
        return ventanaPrincipal;
    }
}
