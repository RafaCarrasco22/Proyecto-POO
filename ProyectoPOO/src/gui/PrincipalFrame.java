/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controlador.Database;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class PrincipalFrame extends JFrame{
    
    Database cc= new Database();
    Connection cn= cc.conexion();
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private JTable table;
    private String[] busca = {"Nombre","Apellido Paterno"};
    private JComboBox ListaBuscar;
    private JButton btnFind;
    private JPanel pnlExtra;
    private JPanel pnl;
    
    private JTextField input;
    private JLabel txt;
    
    private AboutDialog aboutDlg;
    private RegistroDialog regDlg;
    private EditDialog edtDlg;
    
    public PrincipalFrame(){
        super("Sociedad de riego \"LA RAYA\"");
        super.setSize(800,600);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setJMenuBar(createMenu());
        
        pnlExtra = new JPanel();
        pnl = new JPanel();
        btnFind = new JButton("Buscar");
        ListaBuscar = new JComboBox(busca);
        input = new JTextField(10);
        txt = new JLabel("Campo de búsqueda: ");
        
        pnlExtra.add(txt);
        pnlExtra.add(ListaBuscar);
        pnlExtra.add(input);
        pnlExtra.add(btnFind);
        
        aboutDlg = new AboutDialog(this);
        regDlg = new RegistroDialog(this);
        edtDlg = new EditDialog(this);
        edtDlg.setVisible(false);
        
        
        super.add(pnlExtra);
        
        
        initComponents();
        mostrar();
        
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }
    
    private JMenuBar createMenu(){
        JMenuBar menu = new JMenuBar();
        
        JMenu mmArchivo = new JMenu("Archivo");
        JMenuItem nnRegistro = new JMenuItem("Registrar");
        nnRegistro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        nnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String nombre;
                String aPaterno;
                String aMaterno;
                //String noControl = JOptionPane.showInputDialog("Nombre del socio");
                String contraseniaSocio;
                Integer asp;
                Integer adeudo;
                regDlg.setVisible(true);
                /*Pendiente agregar también la lista de Materias, y con ellas calcular el promedio*/
            }    
        });
                
        JMenuItem nnEditar =  new JMenuItem("Editar");
        nnEditar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        nnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                edtDlg.setVisible(true);
            }
        });
        JMenuItem nnEliminar = new JMenuItem("Eliminar");
        nnEliminar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        nnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 System.out.println("Ventana Delete");
            }
        });
        
        JMenuItem nnCorte = new JMenuItem("Corte de Caja");
        nnCorte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 System.out.println("Ventana Corte");
            }
        });
        JMenuItem nnSalir = new JMenuItem("Salir");
        
        mmArchivo.add(nnRegistro);
        mmArchivo.add(nnEditar);
        mmArchivo.add(nnEliminar);
        mmArchivo.addSeparator();
        mmArchivo.add(nnCorte);
        mmArchivo.addSeparator();
        mmArchivo.add(nnSalir);  
        nnSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        nnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        
        JMenu mmAiuda = new JMenu("Ayuda");
        
        JMenuItem nnAcerca = new JMenuItem("Acerca de...");
        nnAcerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        nnAcerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                aboutDlg.setVisible(true);
            }
        });
        mmAiuda.add(nnAcerca);
        menu.add(mmArchivo);
        menu.add(mmAiuda);
        
        return menu;
    }
    
    
    private void mostrar() {        
        DefaultTableModel modelo = new DefaultTableModel();               
        ResultSet rs = Database.getTabla("select * from socios");
        modelo.setColumnIdentifiers(new Object[]{"id","Nombre", "Apellido Pat","Apellido Mat","Cant Aspersores","Adeudo"});
        try {
            while (rs.next()) {
                // añade los resultado a al modelo de tabla
                modelo.addRow(new Object[]{rs.getString("id"),rs.getString("nombre"), rs.getString("ap_pat"), rs.getString("ap_mat"),
                rs.getString("aspers"), rs.getString("adeudo")});
            }            
            // asigna el modelo a la tabla
            table.setModel(modelo);            
        } catch (Exception e) {
            System.out.println(e);
        }
        
       

    }
    
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id","Nombre", "Ap.Paterno", "Ap.Materno", "Cant.Aspersores","Adeudo"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id","Nombre", "Ap.Paterno", "Ap.Materno", "Cant.Aspersores","Adeudo"
            }
        ));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        //pack();
        pnl = new JPanel();
        pnl.add(jTable1);
        pnl.add(jScrollPane1);
        pnl.add(jScrollPane2);
        super.add(pnl);
    }
    
}
