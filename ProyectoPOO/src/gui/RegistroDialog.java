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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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
public class RegistroDialog extends JDialog{
    
    /*private String name;
    private String appat;
    private String apmat;
    private Integer money;
    private Integer asp;
    */
    private JTextField name;
    private JTextField appat;
    private JTextField apmat;
    
    private JLabel nombre;
    private JLabel apa;
    private JLabel ama;
    
    private JPanel pnl;
    private JButton btnOk;
    private Imagen save;
    private Imagen cancel;
    
    PreparedStatement ps;
    ResultSet rs;
    
    
    
    public RegistroDialog(JFrame parent){
        super(parent, true);
        setTitle("Registro Socio");
        setSize(250,180);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        Database cc= new Database();
        Connection cn= cc.conexion();
        
        pnl = new JPanel();
        save = new Imagen("/img/ok-mark.png");
        cancel = new Imagen("/img/error.png");
        btnOk = new JButton("Cancelar");
        
        nombre = new JLabel("Nombre de Socio:");
        apa = new JLabel("Apellido Paterno:  ");
        ama = new JLabel("Apellido Materno: ");
        name = new JTextField(7);
        appat = new JTextField(7);
        apmat = new JTextField(7);
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(name.getText().length()==0 || appat.getText().length()==0 || apmat.getText().length()==0){
                    JOptionPane.showMessageDialog(null, "Hay campos vacíos", "Alerta",JOptionPane.WARNING_MESSAGE);
                }else{
                    if(isNumeric(name.getText())){
                        System.out.println("numeros");
                    }
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Los datos ingresados:\nNombre:                 "+name.getText()+"\nApellido Paterno:  "+appat.getText()+"\nApellido Materno: "+apmat.getText()+"\n \n¿Son correctos?","Alerta",dialogButton);
                    if(dialogResult == 0){
                         try {
                             
                            cc.conexion();
                            ps = cn.prepareStatement("insert into socios (nombre,ap_pat,ap_mat,aspers,adeudo) values(?,?,?,?,?)");
                            ps.setString(1, name.getText());
                            ps.setString(2, appat.getText());
                            ps.setString(3, apmat.getText());
                            ps.setString(4, "0");
                            ps.setString(5, "0");
                            
                            int res = ps.executeUpdate();
            
                            if(res > 0){
                                JOptionPane.showMessageDialog(null, "Persona Guardada");
                                name.setText(null);
                                appat.setText(null);
                                apmat.setText(null);
                                
                            } else {
                                 JOptionPane.showMessageDialog(null, "Error al Guardar persona");
                                 
                            }

                            cn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(RegistroDialog.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Revise sus datos");
                    }
                }
                
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
       
        pnl.add(nombre);
        pnl.add(name);
        pnl.add(apa);
        pnl.add(appat);
        pnl.add(ama);
        pnl.add(apmat);
        pnl.add(save);
        pnl.add(cancel);
       
        
        add(pnl);
    }
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}

    }
}
