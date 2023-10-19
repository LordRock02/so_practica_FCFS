package presentacion;

import logica.Lista;
import logica.Proceso;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriod;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Modelo {

    int contadorReloj =0;

    int contadorCiclo =0;
    int cicloReloj = 1000;

    private Lista listaTurnos = new Lista();
    private Lista listaBloqueados = new Lista();

    private ArrayList<Object[]> procesosIngresados = new ArrayList<Object[]>();
    private VentanaPrincipal ventanaPrincipal;

    private Proceso procesoActual;

    Thread hiloAtender;
    Thread hiloPintarTabla;
    Thread hiloPintarCola;
    Thread hiloPintarDiagramaGantt;
    Thread hiloAtencionBloqueados;
    boolean sistemaActivo = false;

    private int[][] coordenadas = {
            {270,370},
            {440,370},
            {610,370},
            {780,370},

            {780,170},
            {610,170},
            {440,170},
            {270,170},
            {100,170},

            {100,10},
            {270,10},
            {440,10},
            {610,10},
            {780,10}
    };

    public TaskSeriesCollection model;

    public void iniciar(){
        getVistaPrincipal().setSize(1920, 1080);
        getVistaPrincipal().setVisible(true);
        getVistaPrincipal().setLocationRelativeTo(null);

       // getVistaPrincipal().getPanelCola().setBackground(Color.red);
        //getVistaPrincipal().getPanelTabla().setBackground(Color.blue);
        this.pintarDiagramaGantt();
        this.insertarClientesIniciales();
    }
    public void agregarCliente(){
        int cantidadAgregar = Integer.parseInt(this.getVistaPrincipal().getTxtCantClientes().getText());
        Object[] datos = new Object[9];
        Proceso proceso;
        for(int i=0;i<cantidadAgregar;i++){
            this.listaTurnos.insertar();
            this.listaTurnos.getUltimoEnLista().setTiempoLlegada(this.contadorCiclo);
            proceso = this.listaTurnos.getUltimoEnLista();
            System.out.println("proceso: " + proceso.getNombreProceso());
            datos[0]= proceso.getNombreProceso();
            datos[1]=contadorCiclo;
            datos[2]=proceso.getRafagaRestante();
            datos[3]=proceso.getTiempoComienzo();
            datos[4]=proceso.getTiempoFinal();
            datos[5]=proceso.getTiempoRetorno();
            datos[6]=proceso.getTiempoEspera();
            datos[7]="esperando";
            datos[8]=proceso.getIdProceso();
            this.procesosIngresados.add(datos);
        }
        pintarTabla();
        pintarCola();
    }


    public void insertarClientesIniciales(){
        //int cantClientes = (int)(Math.random()*10+1);
        int cantClientes = 5;
        for(int i=0;i<cantClientes;i++){
            this.listaTurnos.insertar();
            this.listaTurnos.getUltimoEnLista().setTiempoLlegada(0);
        }
        ArrayList<Proceso> procesos = this.listaTurnos.listarNodos();
        //System.out.println("INSERTARCLIENTESINICIALES tamano:" + procesos.size());
        for(int i=0; i<procesos.size(); i++){
            Object[] datos = new Object[9];
            //System.out.println("Proceso #: " + procesos.get(i).getIdProceso());
            datos[0]=procesos.get(i).getNombreProceso();
            datos[1]=procesos.get(i).getTiempoLlegada();
            datos[2]=procesos.get(i).getRafaga();
            datos[3]=procesos.get(i).getTiempoComienzo();
            datos[4]=procesos.get(i).getTiempoFinal();
            datos[5]=procesos.get(i).getTiempoRetorno();
            datos[6]=procesos.get(i).getTiempoEspera();
            datos[7]="esperando";//procesos.get(i).getEstado();
            datos[8]=procesos.get(i).getIdProceso();
            this.procesosIngresados.add(datos);
        }
        this.pintarCola();
        this.pintarTabla();
    }

    public void iniciarAtencion(){
        hiloAtender = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo()){
                    listaTurnos.imprimirLista();
                    if(!listaTurnos.isEmpty()){
                        procesoActual = listaTurnos.getProcesoCajero().getSiguiente();
                        procesoActual.setEstado("ejecutando");
                        for(int i= 0; i<procesosIngresados.size(); i++){
                            if(procesoActual.getNombreProceso() == procesosIngresados.get(i)[0]){
                                procesosIngresados.get(i)[7]=procesoActual.getEstado();
                                System.out.println("proceso :" + procesosIngresados.get(i)[0] + " estado: " + procesosIngresados.get(i)[7]);
                            }
                        }
                        String nombreProcesoActual=procesoActual.getNombreProceso();
                        procesoActual.setTiempoComienzo(contadorCiclo);
                        procesoActual.setTiempoEspera(procesoActual.getTiempoComienzo()-procesoActual.getTiempoLlegada());
                        for(int j=0; j<procesosIngresados.size(); j++){
                            if(procesoActual.getNombreProceso()==procesosIngresados.get(j)[0]){
                                procesosIngresados.get(j)[3]=procesoActual.getTiempoComienzo();
                                procesosIngresados.get(j)[6]=procesoActual.getTiempoEspera();
                            }
                        }
                        //System.out.println("Actual: "+procesoActual.getIdProceso());
                        boolean eliminadoPorBloqueo = false;
                        int limiteFor = procesoActual.getRafagaRestante();
                        //System.out.println("Limite for: "+procesoActual.getRafagaRestante());
                        while(contadorCiclo<procesoActual.getTiempoLlegada()){
                            try {
                                Thread.sleep(cicloReloj);
                                contadorCiclo++;

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //verifica que estados estan en espera
                        for(int j=0;j<procesosIngresados.size(); j++){
                            if(((int)procesosIngresados.get(j)[1])==contadorCiclo){
                                procesosIngresados.get(j)[7]="esperando";
                            }
                        }
                        for(int i=0;i<limiteFor;i++){
                            //System.out.println("Ciclo "+(i+1)+ " de proceso " +procesoActual.getIdProceso() + " rafaga "+procesoActual.getRafagaRestante());

                            try {
                                Thread.sleep(cicloReloj);
                                contadorCiclo++;
                                getVistaPrincipal().getLabelContadorCiclo().setText(contadorCiclo + "");
                                getVistaPrincipal().getPanelTabla().repaint();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for(int j=0;j<procesosIngresados.size(); j++){
                                if(((int)procesosIngresados.get(j)[1])==contadorCiclo){
                                    procesosIngresados.get(j)[7]="esperando";
                                }
                            }
                            procesoActual.setRafagaRestante(procesoActual.getRafagaRestante()-1);
                            //Proce
                            int numRandom = (int)(Math.random()*100+1);
                            if(numRandom%7 == 0 && procesoActual.getRafagaRestante()>0){//Proceso entra en bloqueo
                                //System.out.println("Proceso bloqueado");
                                procesoActual.setEstado("Bloqueado");
                                procesoActual.setTiempoFinal(contadorCiclo);
                                procesoActual.setTiempoRetorno(procesoActual.getTiempoFinal()-procesoActual.getTiempoComienzo());
                                procesoActual.setTiempoBloqueo((int)(Math.random()*6+3));
                                for(int j=0; j<procesosIngresados.size(); j++){
                                    if(procesoActual.getNombreProceso()==procesosIngresados.get(j)[0]){
                                        procesosIngresados.get(j)[4]=procesoActual.getTiempoFinal();
                                        procesosIngresados.get(j)[5]=procesoActual.getTiempoRetorno();
                                        procesosIngresados.get(j)[7]="terminado";
                                        //System.out.println("proceso bloqueado: " + procesosIngresados.get(j)[0]+"estado: "+ procesosIngresados.get(j)[7] + " tiempo: " + (int)procesosIngresados.get(j)[4]);
                                    }
                                }
                                listaTurnos.atender(procesoActual);
                                eliminadoPorBloqueo = true;
                                listaBloqueados.insertar(procesoActual);
                                //System.out.println("rompe break");
                                break;
                            }

                        }
                        System.out.println("Atiende");
                        if(!eliminadoPorBloqueo){
                            procesoActual.setTiempoFinal(contadorCiclo);
                            procesoActual.setTiempoRetorno(procesoActual.getTiempoFinal()-procesoActual.getTiempoComienzo());
                            for(int j=0; j<procesosIngresados.size(); j++){
                                if(procesoActual.getNombreProceso()==procesosIngresados.get(j)[0]){
                                    procesosIngresados.get(j)[4]=procesoActual.getTiempoFinal();
                                    procesosIngresados.get(j)[5]=procesoActual.getTiempoRetorno();
                                    procesosIngresados.get(j)[7]="terminado";
                                   // System.out.println("proceso: " + procesosIngresados.get(j)[0]+" estado: "+ procesosIngresados.get(j)[7] + " tiempo: " + (int)procesosIngresados.get(j)[4]);

                                }
                            }
                            listaTurnos.atender(procesoActual);
                        }
                    }else{
                        try {
                            Thread.sleep(cicloReloj);
                            getVistaPrincipal().getLabelContadorCiclo().setText(contadorCiclo + "");
                            contadorCiclo++;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        hiloAtender.start();
    }

    public void iniciarAtencionBloqueados(){
        hiloAtencionBloqueados = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo()){
                    try {
                        Thread.sleep(cicloReloj);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ArrayList<Proceso> procesosBloqueados = listaBloqueados.listarNodos();
                    for(int i = 0; i< procesosBloqueados.size(); i++){
                        Proceso proceso = procesosBloqueados.get(i);

                        if(proceso.getTiempoBloqueo()==1){
                            proceso.setEstado("esperando");
                            listaBloqueados.atender(proceso);
                            listaTurnos.insertar(proceso);
                            listaTurnos.getUltimoEnLista().setNombreProceso(listaTurnos.getUltimoEnLista().getNombreProceso()+"'");
                            Object[] datos = new Object[9];
                            datos[0]=listaTurnos.getUltimoEnLista().getNombreProceso();
                            datos[1]=contadorCiclo;
                            datos[2]=listaTurnos.getUltimoEnLista().getRafagaRestante();
                            datos[3]=0;
                            datos[4]=0;
                            datos[5]=0;
                            datos[6]=0;
                            datos[7]=listaTurnos.getUltimoEnLista().getEstado();
                            datos[8]=listaTurnos.getUltimoEnLista().getIdProceso();
                            procesosIngresados.add(datos);
                        }
                        else if(proceso.getTiempoBloqueo()>1){
                            proceso.setTiempoBloqueo(proceso.getTiempoBloqueo()-1);
                        }
                    }
                }
            }
        });
        hiloAtencionBloqueados.start();
    }

    public void iniciarPintarCola(){
        hiloPintarCola = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo()){
                    try {
                        Thread.sleep(200); // Agregar un retraso de 200 ms entre cada elemento

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pintarCola();
                }
            }
        });
        hiloPintarCola.start();
    }

    public void iniciarPintarTabla(){
        hiloPintarTabla = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo() ){
                    try {

                        Thread.sleep(200); // Agregar un retraso de 200 ms entre cada elemento

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pintarTabla();
                }
            }
        });
        hiloPintarTabla.start();
    }
    public void iniciarDiagramaGantt(){
        hiloPintarDiagramaGantt = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isSistemaActivo() ){
                    try {

                        Thread.sleep(contadorCiclo); // Agregar un retraso de 200 ms entre cada elemento

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pintarDiagramaGantt();
                }
            }
        });
        hiloPintarDiagramaGantt.start();
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


        this.getVistaPrincipal().getModelTablaTiempos().setRowCount(0);
        for(int i = 0; i< procesosIngresados.size(); i++){
            this.getVistaPrincipal().getModelTablaTiempos().addRow(new Object[]{
                    procesosIngresados.get(i)[0],
                    procesosIngresados.get(i)[1],
                    procesosIngresados.get(i)[2],
                    procesosIngresados.get(i)[3],
                    procesosIngresados.get(i)[4],
                    procesosIngresados.get(i)[5],
                    procesosIngresados.get(i)[6],
            });
        }

        ArrayList<Proceso> procesosBloqueados = listaBloqueados.listarNodos();
        this.getVistaPrincipal().getModelTablaBloqueado().setRowCount(0);

        for(int i = 0; i< procesosBloqueados.size(); i++){
            Proceso proceso = procesosBloqueados.get(i);
            this.getVistaPrincipal().getModelTablaBloqueado().addRow(new Object[]{
                    proceso.getIdNodo(),
                    proceso.getTiempoBloqueo(),
                    proceso.getRafagaRestante()
            });
        }
    }

    public void pintarCola(){
        getVistaPrincipal().getPanelCola().removeAll();

        JLabel labelCajero = new JLabel();
        labelCajero.setBounds(60,350,140,150);
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
                        "<p style='text-align:center;margin:0;font-size:9px;'>"+ proceso.getRafaga()+" Rafaga"+"</p>" +
                    "</div>"+
                    "</html>";

            JLabel label = new JLabel(labelText);
            label.setBounds(coordenadas[i][0],coordenadas[i][1],100,110);
            label.setOpaque(true);
            label.setBackground(Color.lightGray);
            this.getVistaPrincipal().getPanelCola().add(label);
        }
        getVistaPrincipal().getPanelCola().repaint();
        getVistaPrincipal().repaint();

    }
    public void pintarDiagramaGantt(){
        final IntervalCategoryDataset dataset = createSampleDataset();
        // create the chart...
        final JFreeChart chart = ChartFactory.createGanttChart(
                "Diagrama de Gantt", // chart title
                "procesos", // domain axis label
                "tiempo", // range axis label
                dataset, // data
                false, // include legend
                true, // tooltips
                false // urls
        );
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        DateAxis range = (DateAxis) plot.getRangeAxis();
        range.setDateFormatOverride(new SimpleDateFormat("S"));
        range.setMaximumDate(new Date(100));

        // insertando diagrama de gantt al panel
        getVistaPrincipal().getChartPanel().setChart(chart);

        //GanttRenderer personnalisÃ©..
        MyRenderer renderer = new MyRenderer(model);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        getVistaPrincipal().getChartPanel().repaint();
    }
    private IntervalCategoryDataset createSampleDataset() {
        model = new TaskSeriesCollection();
        final TaskSeries s = new TaskSeries("");
        //System.out.println("tamano :"+s.getTasks().size());
        for(int i=0; i<procesosIngresados.size(); i++){
            //System.out.println("proceso :" + procesosIngresados.get(i)[0] + " estado: " + procesosIngresados.get(i)[7]);
        }
        if(procesoActual!=null){
            ArrayList<ArrayList<Object[]>>procesos=new ArrayList<>();
            for(int i = 1; i< listaTurnos.getContador()+1; i++){
                ArrayList<Object[]> limites = new ArrayList<>();
                for(int j=0; j<procesosIngresados.size(); j++){
                    Object[] aux = new Object[5];
                    if((int)procesosIngresados.get(j)[8]==i){
                        aux[0] = procesosIngresados.get(j)[1];
                        aux[1] = procesosIngresados.get(j)[3];
                        aux[2] = procesosIngresados.get(j)[4];
                    }
                    aux[3] = procesosIngresados.get(j)[7];
                    aux[4] = "P"+procesosIngresados.get(j)[8];
                }
                procesos.add(limites);
            }
            for(int i=0; i<procesos.size(); i++){
                Task t = new Task("", new SimpleTimePeriod(0, 0));
                ArrayList<Object[]> proceso= procesos.get(i);
                for(int j=0; j<proceso.size(); j++){
                    if(((Object[])proceso.get(j))[3]=="esperando"){
                        t = new Task(""+((Object[])proceso.get(j))[4], new SimpleTimePeriod((int)((Object[])proceso.get(j))[0], contadorCiclo));
                        final Task t1 = new Task("esperando", new SimpleTimePeriod((int)((Object[])proceso.get(j))[0],contadorCiclo));
                        final Task t2 = new Task("ejecutando", new SimpleTimePeriod(0, 0));
                        t.addSubtask(t1);
                        t.addSubtask(t2);
                    }
                    if(((Object[])proceso.get(j))[3]=="ejecutando"){
                        //System.out.println("Pintando proceso en ejecucion");
                        t = new Task(""+((Object[])proceso.get(j))[4], new SimpleTimePeriod((int)((Object[])proceso.get(j))[0], contadorCiclo));
                        final Task t1 = new Task("esperando", new SimpleTimePeriod((int)((Object[])proceso.get(j))[0],(int)((Object[])proceso.get(j))[1]));
                        final Task t2 = new Task("ejecutando", new SimpleTimePeriod((int)((Object[])proceso.get(j))[1], contadorCiclo));
                        t.addSubtask(t1);
                        t.addSubtask(t2);
                    }
                    if(((Object[])proceso.get(j))[3]=="terminado"){
                        t = new Task(""+((Object[])proceso.get(j))[4], new SimpleTimePeriod((int)((Object[])proceso.get(j))[0], contadorCiclo));
                        final Task t1 = new Task("esperando", new SimpleTimePeriod((int)((Object[])proceso.get(j))[0],(int)((Object[])proceso.get(j))[1]));
                        final Task t2 = new Task("ejecutando", new SimpleTimePeriod((int)((Object[])proceso.get(j))[1], (int)((Object[])proceso.get(j))[2]));
                        t.addSubtask(t1);
                        t.addSubtask(t2);
                    }
                }
                s.add(t);
            }

            model.add(s);
        }
        //model.removeAll();
        return model;
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

    public ArrayList<Object[]> getProcesosIngresados() {
        return procesosIngresados;
    }

    public void setProcesosIngresados(ArrayList<Object[]> procesosIngresados) {
        this.procesosIngresados = procesosIngresados;
    }
}
