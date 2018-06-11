package Login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Rafael
 */
public class Imagen extends JButton{
    public Imagen(String path){
       super();
       this.cagarIcono(path);
    }
    private void cagarIcono(String Path){
        URL url = System.class.getResource(Path);
        ImageIcon im = new ImageIcon(url);
        super.setIcon(im);
    }
}
