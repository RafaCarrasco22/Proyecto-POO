/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Login.Imagen;
import controlador.Database;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Rafael
 */
public class payDialog extends JDialog{
    
    private JButton pay;
    private JLabel info;
    private JTextField monto;
    private JLabel msg;
    private JPanel pnl;
    private JPanel pnl2;
    private JPanel pnlNor;
    private JLabel extra;
    private Imagen pago;
    public JTextField id;
    
    public JLabel name;
    public JLabel appat;
    public JLabel apmat;
    public JLabel deuda;
    
    Database cc= new Database();
    Connection cn= cc.conexion();
    PreparedStatement ps;    
    ResultSet rs;
    
    public payDialog(JFrame parent){
        super(parent,true);
        setTitle("Pagar");
        setSize(280,240);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        pago = new Imagen("/img/pago.png");
        pnlNor = new JPanel();
        pnl = new JPanel();
        pnl2 = new JPanel();
        
        id = new JTextField();
        extra = new JLabel("El socio tiene un adeudo de : ");
        info = new JLabel("Ingrese la cantidad a pagar: ");
        monto = new JTextField(4);
        
        msg = new JLabel("Socio: ");
        name = new JLabel();
        appat = new JLabel();
        apmat = new JLabel();
        deuda = new JLabel();
        deuda.setFont(new Font("Verdana", Font.BOLD, 14));
        deuda.setForeground(Color.BLUE);
        extra.setFont(new Font("Verdana", Font.BOLD, 14));
        extra.setForeground(Color.BLUE);
        
        
        pago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int res =0;
                if(isNumeric(monto.getText())){
                    Integer m = Integer.parseInt(monto.getText());
                    Integer n = Integer.parseInt(deuda.getText());
                    if(m > n){
                        JOptionPane.showMessageDialog(null, "Usted no debe tanto"); 
                    }else{
                        if(m%4!=0){
                            JOptionPane.showMessageDialog(null, "El costo de aspersor es de $4/n/nEl monto debe ser múltiplo de 4"); 
                        }else{
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "El monto a pagar  $ "+monto.getText()+"\n \n¿Es correcto?","Alerta",dialogButton);
                            if(dialogResult==0){
                                String vd = null;
                                try {
                                    cc.conexion();
                                    ps = cn.prepareStatement("SELECT * FROM socios WHERE id = ?");
                                    ps.setString(1, id.getText());
                                    rs = ps.executeQuery();


                                    if(rs.next()){
                                        vd = rs.getString("adeudo");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No existe un socio con ese ID");
                                    }
                                    //cn.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Integer value = Integer.parseInt(vd);
                                //Integer value2 = Integer.parseInt(monto.getText());
                                Integer value3 = value-m;
                                Integer value4 = (value3)/4;
                                String valor = String.valueOf(value3);
                                String val = String.valueOf(value4);

                                //Actualización de datos
                                try{
                                    cc.conexion();
                                    ps = cn.prepareStatement("UPDATE socios SET aspers=?,adeudo=? WHERE id=?");
                                    ps.setString(1, val);
                                    ps.setString(2, valor);
                                    ps.setString(3, id.getText());



                                    res = ps.executeUpdate();

                                    if(res > 0){
                                        JOptionPane.showMessageDialog(null, "Modificación realizada con éxito");
                                        monto.setText(null);
                                        dispose();
                                        String dineruqui=null;
                                        ///consulta
                                        try {
                                            cc.conexion();
                                            ps = cn.prepareStatement("SELECT * FROM money");
                                            rs = ps.executeQuery();


                                            if(rs.next()){
                                                dineruqui = rs.getString("dinero");
                                            } else {
                                                JOptionPane.showMessageDialog(null, "No existe un socio con ese ID");
                                            }
                                            //cn.close();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        Integer gg = Integer.parseInt(dineruqui);
                                        Integer dato = gg+m;
                                        String XXX = String.valueOf(dato);
                                        
                                        //agregar
                                        try{
                                            cc.conexion();
                                            ps = cn.prepareStatement("UPDATE money SET dinero=?");
                                            ps.setString(1, XXX);
                                            



                                            res = ps.executeUpdate();

                                            if(res > 0){
                                                JOptionPane.showMessageDialog(null, "Modificación realizada con éxito");
                                                monto.setText(null);
                                                dispose();
                                            }

                                            //cn.close();

                                        } catch(Exception e){
                                            System.err.println(e);
                                        }

                                        
                                        
                                        
                                        
                                        
                                        
                                    }

                                    //cn.close();

                                } catch(Exception e){
                                    System.err.println(e);
                                }
                                
                                
                                
                                
                            
                            }else{
                                JOptionPane.showMessageDialog(null, "Verifique el monto"); 
                            }
                    }
                            
                    }          
                         
                }else{
                   JOptionPane.showMessageDialog(null, "Ingrese solo digitos"); 
                }
            }
        });
        
        //String deud = deuda.getText();
        //System.out.println(deuda.get);
///        Integer deu = Integer.parseInt(deud);
        /*if(deu == 0){
            JOptionPane.showMessageDialog(null, "Usted NO tiene adeudos");

        }*/
        
        /*try {          
            cc.conexion();             
            ps = cn.prepareStatement("SELECT * FROM socios WHERE id = ?");            
            ps.setString(1, id.getText());
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
            
        }*/
        
        
        pnlNor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        pnlNor.add(msg);
        pnlNor.add(name);
        pnlNor.add(appat);
        pnlNor.add(apmat);
        //pnlNor.add(deuda);
        add(pnlNor , BorderLayout.NORTH);
        pnl.add(extra);
        pnl.add(deuda);
        //pnl.add(new JSeparator(JSeparator.HORIZONTAL));
        pnl.add(info);
        pnl.add(monto);
        pnl.add(pago);
        add(pnl);
        
        add(pnl);
        setResizable(false);
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
