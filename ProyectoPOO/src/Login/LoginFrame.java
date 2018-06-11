/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import gui.PrincipalFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Rafael
 */
public class LoginFrame extends JFrame{
    JPanel panelsito;
    JPanel bordeEast;
    JPanel bordeWest;
    JPanel bordeNorth;
    JPanel bordeSouth;
    JPanel pnlLogIn;
    JPanel pnlUsuario;
    JPanel pnlPassword;
    JLabel lblUser;
    JTextField txtUser;
    JLabel lblpassword;
    JPasswordField txtPassword;
    JLabel lblTipoUsuario;
    JComboBox cboxTipoUsuario;
    JButton btnLogIn;
    JButton btnExit;
    JLabel titulo;
    ActionListener listenerLogIn;
    Imagen in;
    Imagen out;
    
    public LoginFrame(String title){
        super(title);
        super.setLayout(new BorderLayout());
        
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(290,340);
        super.setLocationRelativeTo(null);
        //super.setResizable(false);
        
        //PANEL QUE TENDRA EL BOX LAYOUT Y LOS BORDES
        panelsito = new JPanel();
        panelsito.setLayout(new BoxLayout(panelsito, BoxLayout.PAGE_AXIS));
       
        //PANEL DENTRO DE PANELSITO QUE TENDRA BOTON ACEPTAR Y JCOMBOBOX
        pnlLogIn = new JPanel();
        pnlLogIn.setLayout(new FlowLayout(FlowLayout.LEFT));
        //PANEL USUARIO DENTRO DE PANELSITO
        pnlUsuario = new JPanel();
        pnlUsuario.setLayout(new FlowLayout(FlowLayout.LEFT));
        //PANEL CONTRASEÑA DENTRO DE PANELSITO
        pnlPassword = new JPanel();
        pnlPassword.setLayout(new FlowLayout(FlowLayout.LEFT));
        //pnl EAST
        bordeEast = new JPanel();
        //bordeEast.setBackground(Color.yellow);
        bordeEast.setPreferredSize(new Dimension(70, 300));
        //PNL WEST
        bordeWest = new JPanel();
        //bordeWest.setBackground(Color.red);
        bordeWest.setPreferredSize(new Dimension(70, 300));
        //PNL NORTH
        bordeNorth = new JPanel();
        
        ImageIcon ico = new ImageIcon("src/img/user.png");
        JLabel lblImage = new JLabel(ico);
        //titulo.setFont(new Font("Tahoma", 1, 34));
        bordeNorth.add(lblImage);
        /*titulo = new JLabel("Exámenes");
        titulo.setFont(new Font("Comic Sans MS", 1, 34));
        bordeNorth.add(titulo);
        titulo.setForeground(Color.MAGENTA);*/
        //bordeNorth.setBackground(Color.green);
        bordeNorth.setPreferredSize(new Dimension(400, 128));
        //PNL SOUTH   
        bordeSouth = new JPanel();
        //bordeSouth.setBackground(Color.black);
        //bordeSouth.setPreferredSize(new Dimension(400, 55));
        //SET ACTION LISTENER PARA BOTON Y TXT PASSWORD 
        listenerLogIn = new ActionListener() {
                            @Override
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("CLICKED BTNLOGIN");
                if(txtPassword.getText().equals("0000") && txtUser.getText().equals("admin")){
                            System.out.println("LOGRO ENTRAR AL IF");
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    LoginFrame.this.setVisible(false);
                                    PrincipalFrame ventana = new PrincipalFrame();
                                }
                            });
                        }else{
                            JOptionPane.showMessageDialog(LoginFrame.this,
                                    "Usuario o contraseña incorrectos", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                
                
                
                }
        };
        
        
        //Label y texfield de nombre
        lblUser = new JLabel("Nombre de usuario:");
        lblUser.setAlignmentX(LEFT_ALIGNMENT);
        txtUser = new JTextField();
        //Label y texfield de password
        lblpassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();
        txtPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                    listenerLogIn.actionPerformed(new ActionEvent(ke.getSource(), WIDTH, "enter pressed"));
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        
        //El mágico boton de aceptar
        in = new Imagen("/img/sign-in.png");
        out = new Imagen("/img/error.png");
        btnLogIn = new JButton("Log In");
        btnExit = new JButton("Cancelar");
        //COMBO BOX MAGICO PARA TIPO USUARIO y SU LABEL
        //ADDS A PNLOGIN
        pnlLogIn.add(in,BorderLayout.WEST);
        pnlLogIn.add(out,BorderLayout.EAST);
        //pnlLogIn.add(Box.createHorizontalStrut(2));
        
        //ADDS A PANELSITO
        pnlUsuario.add(lblUser);
        panelsito.add(pnlUsuario);
        panelsito.add(txtUser);
        panelsito.add(Box.createVerticalStrut(15)); 
        pnlPassword.add(lblpassword);
        panelsito.add(pnlPassword);
        panelsito.add(txtPassword);
        panelsito.add(Box.createVerticalStrut(15));
        panelsito.add(pnlLogIn);
        
        //SET ACTION LISTENER BTN LOGIN
        in.addActionListener(listenerLogIn);
        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        //ADDS A SUPER
        
        super.add(bordeEast, BorderLayout.EAST);
        super.add(bordeWest, BorderLayout.WEST);
        super.add(bordeNorth, BorderLayout.NORTH);
        super.add(bordeSouth, BorderLayout.SOUTH);
        super.add(panelsito, BorderLayout.CENTER);
        
        
        
        
        super.setVisible(true);
        
    }
}
