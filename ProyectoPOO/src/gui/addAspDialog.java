/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Login.Imagen;
import controlador.Database;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Rafael
 */
public class addAspDialog extends JDialog{
    
    private JTextField input;
    private JLabel dia;
    private JLabel cant;
    private JPanel pnl;
    private JPanel pnlNor;
    private SpinnerNumberModel sp;
    private Imagen ok;
    private Imagen cancel;
    
    Database cc= new Database();
    Connection cn= cc.conexion();
    PreparedStatement ps;    
    ResultSet rs;

    public JLabel name;
    public JLabel appat;
    public JLabel apmat;
    private JLabel socio;
    public JLabel id;
    
    public addAspDialog(JFrame parent){
        super(parent , true);
        setTitle("Agregar Aspersores");
        setSize(440,130);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        pnl = new JPanel();
        pnlNor = new JPanel();
        socio = new JLabel("Socio: ");
        dia = new JLabel("Cantidad de días: ");
        cant = new JLabel("Aspersores:     ");
        input = new JTextField(3);
        
        ok = new Imagen("/img/ok-mark.png");
        cancel = new Imagen("/img/error.png");
       
        id = new JLabel();
        name = new JLabel();
        appat = new JLabel();
        apmat = new JLabel();
        
        Integer value = new Integer(1);
        Integer min = new Integer(1);
        Integer max = new Integer(7);
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        JSpinner a = new JSpinner(model);

        
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int res =0;
                if(isNumeric(input.getText())){
                    String c = input.getText();
                    Integer b = Integer.parseInt(c);
                    String vd = null;
                    if( (b <= 50)){
                        if(b>0){
                            try {
                                cc.conexion();
                                ps = cn.prepareStatement("SELECT * FROM socios WHERE id = ?");
                                ps.setString(1, id.getText());
                                rs = ps.executeQuery();
                                
                                
                                if(rs.next()){
                                    vd = rs.getString("aspers");
                                } else {
                                    JOptionPane.showMessageDialog(null, "No existe un socio con ese ID");
                                }
                                //cn.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Integer value = Integer.parseInt(vd);
                            
                            Integer cant = (Integer) a.getValue() * (b);
                            cant+=value;
                            Integer fin = cant *4;
                            System.out.println(cant);
                            String valor = String.valueOf(cant);
                            String money = String.valueOf(fin);
                           
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "La cantidad de aspersores:   "+(b*(Integer)a.getValue()) +"\nLa cantidad de dias:                 "+a.getValue()+ "\n \n¿Son correctos?","Alerta",dialogButton);
                            if(dialogResult==0){
                            
                            
                            
                            //Actualización de datos
                            try{
                                cc.conexion();
                                ps = cn.prepareStatement("UPDATE socios SET aspers=?,adeudo=? WHERE id=?");
                                ps.setString(1, valor);
                                ps.setString(2, money);
                                ps.setString(3, id.getText());
                                


                                res = ps.executeUpdate();

                                if(res > 0){
                                    JOptionPane.showMessageDialog(null, "Modificación realizada con éxito");
                                    input.setText(null);
                                    a.setValue(1);
                                    dispose();
                                }

                                //cn.close();

                            } catch(Exception e){
                                System.err.println(e);
                            }}else{
                                JOptionPane.showMessageDialog(null, "Verifique los datos");

                            }
                            
                            
                            
                        }else{
                            
                            JOptionPane.showMessageDialog(null, "No puede solicitar menos de 0 aspersores");

                        }
                         
                    }else{
                        JOptionPane.showMessageDialog(null, "No puede solicitar más de 50 aspersores");

                    }
                    
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Verifique sus datos");
                }
            }
        });
        
        cancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            a.setValue(1);
                            input.setText(null);
                            dispose();
                        }
                    });
       
        pnl.add(dia);
        pnl.add(a);
        pnl.add(cant);
        pnl.add(input);
        pnl.add(ok);
        pnl.add(cancel);
        
        pnlNor.add(socio);
        pnlNor.add(name);
        pnlNor.add(appat);
        pnlNor.add(apmat);
        pnlNor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        add(pnlNor, BorderLayout.NORTH);
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
