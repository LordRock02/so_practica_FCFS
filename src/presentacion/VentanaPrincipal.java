package presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private Modelo modelo;
    private ControladorVentanaPrincipal control;

    private JPanel panelCola;
    private JPanel panelTabla;
    private JTextField txtCantClientes;
    private JTextField txtCantAtender;
    private JButton btnAgregarClientes;
    private JButton btnAtender;
    private JButton btnIniciarDetener;
    DefaultTableModel modelTablaTiempos,modelTablaBloqueado;
    JTable tablaTiempos;
    JTable tablaBloqueados;

    Font fontButton;
    Font fontLabel;

    JScrollPane scrollPaneTiempos,scrollPaneBloqueados;
    JLabel labelCajero;
    JLabel labelAccion;
    public VentanaPrincipal(Modelo modelo) {
        super("Sistema Cola Cajero");
        this.modelo=modelo;
        // Establecer el título de la ventana
        inicializarComponentes();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        capturarEventos();
    }

    public Modelo getModelo() {
        return modelo;
    }

    public ControladorVentanaPrincipal getControl() {
        if(control == null){
            control = new ControladorVentanaPrincipal(this);
        }
        return control;
    }

    public void inicializarComponentes(){

        this.fontButton = new Font("Roboto", Font.BOLD, 15);
        this.fontLabel = new Font("Roboto", Font.BOLD, 25);

        //Paneles

        this.panelCola = new JPanel();
        this.panelCola.setBounds(0,0,1000,720);
        this.getPanelCola().setLayout(null);

        this.panelTabla = new JPanel();
        this.panelTabla.setBounds(1000,0,280,720);
        this.getPanelTabla().setLayout(null);


        //Botones

        this.btnAtender = new JButton("ATENDER");
        this.btnAtender.setBounds(1480,750,120,40);
        this.btnAtender.setFont(fontButton);

        this.btnAgregarClientes = new JButton("AGREGAR");
        this.btnAgregarClientes.setBounds(1480,800,120,40);
        this.btnAgregarClientes.setFont(fontButton);

        this.btnIniciarDetener = new JButton("INICIAR");
        this.btnIniciarDetener.setBounds(1450,850,120,40);
        this.btnIniciarDetener.setFont(fontButton);

        //TextFields

        this.txtCantAtender = new JTextField();
        this.txtCantAtender.setBounds(1420,750,50,40);
        this.txtCantAtender.setHorizontalAlignment(JTextField.CENTER);
        this.txtCantAtender.setText("1");
        this.txtCantAtender.setFont(this.fontButton);

        this.txtCantClientes = new JTextField();
        this.txtCantClientes.setBounds(1420,800,50,40);
        this.txtCantClientes.setHorizontalAlignment(JTextField.CENTER);
        this.txtCantClientes.setText("1");
        this.txtCantClientes.setFont(this.fontButton);

        //Label
        this.labelCajero = new JLabel();
        this.labelCajero.setBounds(60,450,140,150);
        this.labelCajero.setOpaque(true);
        this.labelCajero.setIcon(new ImageIcon(getClass().getResource("/imagenes/atm.png")));

        this.labelAccion = new JLabel();
        this.labelAccion.setBounds(1490,900,120,40);
        this.labelAccion.setText("Accion");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        //Tabla cola principal

        modelTablaTiempos = new DefaultTableModel();

        modelTablaTiempos.addColumn("Proceso");
        modelTablaTiempos.addColumn("T.Llegada");
        modelTablaTiempos.addColumn("Rafaga");
        modelTablaTiempos.addColumn("T.Comienzo");
        modelTablaTiempos.addColumn("T.Final");
        modelTablaTiempos.addColumn("T.Retorno");
        modelTablaTiempos.addColumn("T.Espera");

        tablaTiempos = new JTable(modelTablaTiempos);
        tablaTiempos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaTiempos.setBackground(Color.white);

        TableColumnModel columnModelTiempos = tablaTiempos.getColumnModel();

        for (int i = 0; i < tablaTiempos.getColumnCount(); i++) {
            columnModelTiempos.getColumn(i).setPreferredWidth(100);
            tablaTiempos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPaneTiempos = new JScrollPane(tablaTiempos);
        scrollPaneTiempos.setBounds(1150,40,703,400);

        //Tabla bloqueados

        modelTablaBloqueado = new DefaultTableModel();

        modelTablaBloqueado.addColumn("Proceso");
        modelTablaBloqueado.addColumn("T.Llegada");
        modelTablaBloqueado.addColumn("Rafaga");

        tablaBloqueados = new JTable(modelTablaBloqueado);
        tablaBloqueados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaBloqueados.setBackground(Color.white);

        TableColumnModel columnModelBloqueados = tablaTiempos.getColumnModel();

        for (int i = 0; i < tablaBloqueados.getColumnCount(); i++) {
            columnModelBloqueados.getColumn(i).setPreferredWidth(100);
            tablaBloqueados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPaneBloqueados = new JScrollPane(tablaBloqueados);
        scrollPaneBloqueados.setBounds(1400,500,228,200);


        // Agregar elementos a los paneles

        this.panelTabla.add(this.scrollPaneTiempos);
        this.panelTabla.add(this.scrollPaneBloqueados);
        this.panelTabla.add(this.btnAtender);
        this.panelTabla.add(this.btnAgregarClientes);
        this.panelTabla.add(this.btnIniciarDetener);
        this.panelTabla.add(this.txtCantClientes);
        this.panelTabla.add(this.txtCantAtender);

        this.panelCola.add(this.labelCajero);
        this.panelTabla.add(this.labelAccion);

        this.add(panelCola);
        this.add(panelTabla);
    }

    public DefaultTableModel getModelTable() {
        return modelTablaTiempos;
    }

    public JPanel getPanelCola() {
        return panelCola;
    }

    public JPanel getPanelTabla() {
        return panelTabla;
    }

    public JTextField getTxtCantClientes() {
        return txtCantClientes;
    }

    public JTextField getTxtCantAtender() {
        return txtCantAtender;
    }

    public JLabel getLabelAccion() {
        return labelAccion;
    }

    public JButton getBtnIniciarDetener() {
        return btnIniciarDetener;
    }

    private void capturarEventos() {
        this.btnAtender.addActionListener(getControl());
        this.btnAgregarClientes.addActionListener(getControl());
        this.btnIniciarDetener.addActionListener(getControl());
    }
}
