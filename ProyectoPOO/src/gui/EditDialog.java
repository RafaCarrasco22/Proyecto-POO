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
public class EditDialog extends JDialog{
    
    private Imagen find;
    private JTextField inp;
    private JLabel inf;
    private JPanel pnl;
    private JPanel pnlData;
    
    private JTextField name;
    private JTextField appat;
    private JTextField apmat;
    
    private JLabel nombre;
    private JLabel apa;
    private JLabel ama;
    
    PreparedStatement ps;    
    ResultSet rs;
    
    
    public EditDialog(JFrame parent){
        super(parent, true);
        setTitle("Editar Socio");
        setSize(350,180);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        find = new Imagen("/img/find.png");
        inp = new JTextField(3);
        inf = new JLabel("Ingresa el ID del socio: ");
        pnl = new JPanel();
        pnlData = new JPanel();
        
        nombre = new JLabel("Nombre de Socio:");
        apa = new JLabel("Apellido Paterno:  ");
        ama = new JLabel("Apellido Materno: ");
        name = new JTextField(7);
        appat = new JTextField(7);
        apmat = new JTextField(7);
        
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
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pnlData.add(nombre);
                    pnlData.add(name);
                    pnlData.add(apa);
                    pnlData.add(appat);
                    pnlData.add(ama);
                    pnlData.add(apmat);
                }else{
                    JOptionPane.showMessageDialog(null, "El ID es numérico");
                }
            }
        });
        
        pnl.add(inf);
        pnl.add(inp);
        pnl.add(find);
        add(pnl, BorderLayout.NORTH);
        add(pnlData);
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
