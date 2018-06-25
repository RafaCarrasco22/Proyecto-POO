/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Login.Imagen;
import controlador.Database;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Rafael
 */
public class Edit2 extends JDialog{
    private Imagen find;
    private Imagen modify;
    private Imagen cancel;
    
    public JTextField inp;

    
    private JLabel inf;
    private JPanel pnl;
    private JPanel pnlData;
    private JPanel pnlBtns;
    
    public JTextField name;
    public JTextField appat;
    public JTextField apmat;
    
    private JLabel nombre;
    private JLabel apa;
    private JLabel ama;
    
   
    public String inid;

    public String getInid() {
        return inid;
    }

    public void setInid(String inid) {
        this.inid = inid;
    }
    
  
    
    
    
    PreparedStatement ps;    
    ResultSet rs;
    
    public Edit2(JFrame parent){
        super(parent, true);
        setTitle("Editar Socio");
        setSize(280,240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
     
        
        find = new Imagen("/img/find.png");
        modify = new Imagen("/img/save.png");
        cancel = new Imagen("/img/error.png");
        inp = new JTextField(3);
        
        //inp.setText(inid);
        //System.out.println(getInid());
        
        inf = new JLabel("Ingresa el ID del socio: ");
        pnl = new JPanel();
        pnlData = new JPanel();
        pnlBtns = new JPanel();
        
        //inp.setText("11");
        
        //System.out.println(id);
        //System.out.println(getAp());
        //System.out.println(getAm());
        //System.out.println(getN());
        
        nombre = new JLabel("Nombre de Socio:");
        apa = new JLabel("Apellido Paterno:  ");
        ama = new JLabel("Apellido Materno: ");
        name = new JTextField(7);
        
        name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt){
                namekeyTyped(evt);
            }
        });
        appat = new JTextField(7);
       
        appat.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt){
                namekeyTyped(evt);
            }
        });
        apmat = new JTextField(7);
       
        apmat.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt){
                namekeyTyped(evt);
            }
        });
        
        Database cc= new Database();
        Connection cn= cc.conexion();
        
        
        
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isNumeric(inp.getText())){
                    try {
                        cc.conexion();
                        ps = cn.prepareStatement("SELECT * FROM socios WHERE id = ?");
                        ps.setString(1, inp.getText());
                        rs = ps.executeQuery();
                        if(rs.next()){
                            name.setText(rs.getString("nombre"));
                            appat.setText(rs.getString("ap_pat"));
                            apmat.setText(rs.getString("ap_mat"));
                        } else {
                            JOptionPane.showMessageDialog(null, "No existe un socio con ese ID");
                        }
                        //cn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "El ID es numérico");
                }
                setSize(280, 241);
            }
        });
        modify.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            int res =0;
                            if(name.getText().length()==0 || appat.getText().length()==0 || apmat.getText().length()==0){
                                JOptionPane.showMessageDialog(null, "Hay campos vacíos", "Alerta",JOptionPane.WARNING_MESSAGE);
                                if(name.getText().length()==0){
                                    JOptionPane.showMessageDialog(null, "No ingresó nombre");
                                }
                                if(appat.getText().length()==0){
                                    JOptionPane.showMessageDialog(null, "No ingresó apellido paterno");
                                }
                                if(apmat.getText().length()==0){
                                    JOptionPane.showMessageDialog(null, "No ingresó apellido materno");
                                }
                            }else{
                                if(existeusuario(name.getText())!=0 && existeap(appat.getText())!=0 && existeam(apmat.getText())!=0){
                                                    JOptionPane.showMessageDialog(null, "El usuario ya existe");

                        }else{
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Los datos ingresados:\nNombre:                 "+name.getText()+"\nApellido Paterno:  "+appat.getText()+"\nApellido Materno: "+apmat.getText()+"\n \n¿Son correctos?","Alerta",dialogButton);
                            if(dialogResult==0){
                            
                            try{
                            cc.conexion();
                            ps = cn.prepareStatement("UPDATE socios SET nombre=?,ap_pat=?,ap_mat=? WHERE id=?");
                            ps.setString(1, name.getText());
                            ps.setString(2, appat.getText());
                            ps.setString(3, apmat.getText());
                            ps.setString(4, inp.getText());
                            

                            res = ps.executeUpdate();

                            if(res > 0){
                                JOptionPane.showMessageDialog(null, "Persona Modificada");
                                    inp.setText(null);
                                    name.setText(null);
                                    appat.setText(null);
                                    apmat.setText(null);
                                    
                                 dispose();
                            }

                            //cn.close();
                            
                        } catch(Exception e){
                            System.err.println(e);
                        }}else{
                        JOptionPane.showMessageDialog(null, "Revise sus datos");
                    }}
                        }}
                    });
        cancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            inp.setText(null);
                            name.setText(null);
                            appat.setText(null);
                            apmat.setText(null);
                            dispose();
                        }
                    });
        pnlData.add(nombre);
        pnlData.add(name);
        pnlData.add(apa);
        pnlData.add(appat);
        pnlData.add(ama);
        pnlData.add(apmat);
        pnlBtns.add(modify);
        pnlBtns.add(cancel);
        pnl.add(inf);
        pnl.add(inp);
        pnl.add(find);
        add(pnl, BorderLayout.NORTH);
        add(pnlData);
        
        add(pnlBtns,BorderLayout.SOUTH);
    }
    
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    public void namekeyTyped(KeyEvent evt) {     
         char c=evt.getKeyChar();
          if(Character.isDigit(c)) { 
              getToolkit().beep(); 
              evt.consume(); 
              JOptionPane.showMessageDialog(null, "No se aceptan números");
          }   
     }
     
     public int existeusuario(String name){

        Database cc= new Database();
        Connection cn= cc.conexion();
        //String sql = "SELECT COUNT(id) FROM socios WHERE nombre = ?";

        try {
            cc.conexion();    
            ps = cn.prepareStatement("SELECT COUNT(id) FROM socios WHERE nombre = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
     	    if(rs.next()){
         	return rs.getInt(1);
 	    }
	return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RegistroDialog.class.getName()).log(Level.SEVERE, null, ex);
                          //JOptionPane.showMessageDialog(null, "Repetidos");
            return 1;
        }
    }
     
     public int existeap(String name){

        Database cc= new Database();
        Connection cn= cc.conexion();
        //String sql = "SELECT COUNT(id) FROM socios WHERE ap_pat = ?";

        try {
            cc.conexion();    
            ps = cn.prepareStatement("SELECT COUNT(id) FROM socios WHERE ap_pat = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
     	    if(rs.next()){
         	return rs.getInt(1);
 	    }
	return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RegistroDialog.class.getName()).log(Level.SEVERE, null, ex);
                          //JOptionPane.showMessageDialog(null, "Repetidos");
            return 1;
        }
    }
     
     public int existeam(String name){

        Database cc= new Database();
        Connection cn= cc.conexion();
        //String sql = "SELECT COUNT(id) FROM socios WHERE ap_mat = ?";

        try {
            cc.conexion();    
            ps = cn.prepareStatement("SELECT COUNT(id) FROM socios WHERE ap_mat = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
     	    if(rs.next()){
         	return rs.getInt(1);
 	    }
	return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RegistroDialog.class.getName()).log(Level.SEVERE, null, ex);
                          //JOptionPane.showMessageDialog(null, "Repetidos");
            return 1;
        }
    }

    void setInp(String a) {
        this.inid = a;
    }
}

