/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzayoyunuprojesi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author asus
 */
class Ates {

    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}

public class Oyun extends JPanel implements KeyListener, ActionListener {
Timer timer=new Timer(3,this);
    private int gecen_sure = 0;
    private int harcana_ates = 0;
    private int atesdirY = 1;
    private int topX = 0;
    private int topdirX=2;
    private  int uzaygemisiX=0;
    private int dirUzayX=20;
    private BufferedImage image;
    private ArrayList<Ates> atesler = new ArrayList<Ates>();
    
    public boolean KontrolEt()
    {
        for(Ates ates: atesler)
        {
            if(new Rectangle(ates.getX(),ates.getY(),10, 20).intersects(new Rectangle(topX, 0, 20, 20)))
            {
                return true;
            }
        }
        return false;
    }

    public Oyun() {
        try {
            image= ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        gecen_sure+=5;
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.GREEN);
        g.fillOval(topX, 0, 20, 20);
        g.drawImage(image, uzaygemisiX,490,image.getWidth()/10,image.getHeight()/10,this);
        
        for(Ates ates: atesler)
        {
            if(ates.getY()<0)
            {
                atesler.remove(ates);
            }
        }
         g.setColor(Color.BLUE);
         for(Ates ates: atesler)
        {
           g.fill3DRect(ates.getX(),ates.getY(), 10, 20,true);
        }
         if(KontrolEt())
         {
             timer.stop();
             String mesaj="Kazandınız.. \n"+
                     "Harcanan Ates:"+harcana_ates+
                     "\nGeçen süre:"+gecen_sure/1000.0 +"saniye";
             JOptionPane.showMessageDialog(this, mesaj);
             System.exit(0);
                     
         }
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void keyTyped(KeyEvent e) {
       
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
         int c=e.getKeyCode();
        if(c==KeyEvent.VK_LEFT)
        {   
            if(uzaygemisiX<=0)
            {
                uzaygemisiX=0;
            }
        else
            {
                uzaygemisiX-=dirUzayX;
            }
        
    }
          if(c==KeyEvent.VK_RIGHT)
        {   
            if(uzaygemisiX>=750)
            {
                uzaygemisiX=750;
            }
        else
            {
                uzaygemisiX+=dirUzayX;
            }    
    }
          else if(c==KeyEvent.VK_SPACE)
          {
              atesler.add(new Ates(uzaygemisiX+15,475));
              harcana_ates++;
          }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      for(Ates ates: atesler)
      {
          ates.setY(ates.getY()-atesdirY);
      }
        
        
        topX+=topdirX;
        if(topX>=750)
        {
            topdirX=-topdirX;
        }
           if(topX<0)
        {
            topdirX=-topdirX;
        }
           repaint();
    }

}
