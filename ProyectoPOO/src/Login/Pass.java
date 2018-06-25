/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import controlador.Database;
import gui.EditDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPasswordField;

/**
 *
 * @author Rafael
 */
public class Pass extends JDialog{
    JPanel pnl;
    
    JPasswordField pw;
    JPasswordField pw1;
    JPasswordField pw2;
    
    JLabel lbl;
    JLabel lbl1;
    JLabel lbl2;
    
    Imagen ok;
    Imagen cancel;
    
    Database cc= new Database();
    Connection cn= cc.conexion();
    PreparedStatement ps;    
    ResultSet rs;
    TestEncriptarMD5 en;
    
    public Pass(JFrame parent){
        super(parent,true);
        setTitle("Cambiar Contraseña");
        setSize(280,160);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        pnl = new JPanel();
        ok = new Imagen("/img/ok-mark.png");
        cancel = new Imagen("/img/error.png");
        en = new TestEncriptarMD5();
        
        lbl = new JLabel("Contraseña Anterior  ");
        lbl1 = new JLabel("Contraseña Nueva      ");
        lbl2 = new JLabel("Confirme Contraseña");
        
        pw = new JPasswordField(10);
        pw1 = new JPasswordField(10);
        pw2 = new JPasswordField(10);
        
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                pw.setText(null);
                pw1.setText(null);
                pw2.setText(null);
            }
        });
        
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int res =0;
                String x = null;
                try {
                        cc.conexion();
                        ps = cn.prepareStatement("SELECT * FROM admin");
                        rs = ps.executeQuery();
                        if(rs.next()){
                            x = rs.getString("pw");
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "No existe un socio con ese ID");
                        }
                        //cn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                String XXX = en.encriptaEnMD5(pw.getText());
                if(x.equals(XXX) && pw1.getText().equals(pw2.getText())){
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de cambiar la contraseña?","Alerta",dialogButton);
                            if(dialogResult==0){
                            
                            try{
                                cc.conexion();
                                ps = cn.prepareStatement("UPDATE admin SET pw=?");
                                ps.setString(1, en.encriptaEnMD5(pw1.getText()));
                                


                                res = ps.executeUpdate();

                                if(res > 0){
                                    JOptionPane.showMessageDialog(null, "Contraseña Actualizada");
                                        pw.setText(null);
                                        pw1.setText(null);
                                        pw2.setText(null);


                                }

                            //cn.close();
                            
                            } catch(Exception e){
                                System.err.println(e);
                            }}else{
                            JOptionPane.showMessageDialog(null, "Revise sus datos");
                        }
                            dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");

                }
            }
        });
        
        pnl.add(lbl);
        pnl.add(pw);
        pnl.add(lbl1);
        pnl.add(pw1);
        pnl.add(lbl2);
        pnl.add(pw2);
        pnl.add(ok);
        pnl.add(cancel);
        add(pnl);
    }
}
