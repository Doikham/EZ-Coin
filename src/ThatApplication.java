import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ThatApplication extends JFrame implements KeyListener{
    private JPanel              contentpane;
    private JLabel              drawpane;
    private MyLabel             JetpackLabel;
    private JLabel              BossLabel;
    private JButton             startButton, closeButton, highscoreButton;
    private JTextField          scoreText;
    private int coin;
    private MySoundEffect       themeSound;
    private boolean gameRun =   true;

    public ThatApplication(int level, String backgoundImg, String themeSound)
    {
        AddComponents(backgoundImg, themeSound);
        setGameThread(level);
        //setCoinThread();
        addKeyListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //themeSound.stop();
            }
        });
//        while(gameRunning) {
//            createObstacle(level);
//            repaint();
//            try { Thread.sleep((int)(Math.random()*(6000 - 500 * level - score/10))); } 
//            catch (InterruptedException e) { e.printStackTrace(); }
//        }
//        setVisible(false);
//        themeSound.stop();
//        new forproject3credit();
    }

    public void AddComponents(String backgoundImg, String themeSound)
    {
        setTitle("EZ Coin Collector: Beware your step!");
        setBounds(100, 100, 1500, 800);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        
        coin = 0;
        
        contentpane = (JPanel)getContentPane();
        contentpane.setLayout(new BorderLayout());
        JOptionPane.showMessageDialog(new JFrame(), "Let's start!" , "Hello!",
                JOptionPane.INFORMATION_MESSAGE );
        
        MyImageIcon backgroundImg = new MyImageIcon("Resources/jetpack/bg2.jpg").resize(contentpane.getWidth(), contentpane.getHeight());

        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

        JetpackLabel = new MyLabel();
        drawpane.add(JetpackLabel);
        JetpackLabel.setFocusable(true);
        JetpackLabel.requestFocus();
        addKeyListener(JetpackLabel);
        repaint();

        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
    }

    public void setGameThread(int level){
        Thread cometThread = new Thread() {
            public void run()
            {
                int hard = 50;
                switch(level){
                    case 0: hard = 50; 
                    break;
                    case 1: hard = 80; 
                    break;
                    case 2: hard = 10; 
                    break;
                    case 3: hard = 120; 
                    break;
                    case 4: hard = 150; 
                    break;
                }
                Comet comet = new Comet(hard);
                contentpane.add(comet);
                repaint();
                boolean done = false;
                while (!done && gameRun) { 
                    comet.updateLocation();
                    collision(comet);
                    if(comet.getCurX() < -comet.getWidth() || comet.getCurX() > 1200) done = true;
                    try { Thread.sleep(hard); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                contentpane.remove(comet);
                repaint();
            }
        };
        cometThread.start();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class MyLabel extends JLabel implements KeyListener
{
    private int width = 150, height = 200;
    private MyImageIcon   HeroImage;
    private int HeroWidth = 250, HeroHeight = 200;
    private int HerocurX  = 90, HerocurY = 400;
    private shareInt receive;

    public MyLabel()
    {
        HeroImage = new MyImageIcon("Resources/jetpack/jetpackboy.png").resize(HeroWidth, HeroHeight);
        setHorizontalAlignment(JLabel.CENTER);
        setIcon(HeroImage);
        setHeroLocation();
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if (HerocurY > 0) HerocurY -= 20;
                else HerocurY = 0;
                break;
            case KeyEvent.VK_DOWN:
                if (HerocurY < (int)receive.height-HeroHeight-30) HerocurY += 20;
                else HerocurY = (int)receive.height-HeroHeight-30;
                break;
            default: break;
        }
        setHeroLocation();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setHeroLocation(){
        setBounds(HerocurX,HerocurY,width,height);
    }
}

class Obstacle extends customJLabel {
    
    private boolean movingLeft;
    private boolean twoSide = false;
    private MyImageIcon labelB;
    
    public Obstacle(int level) {
        super();
        width = 800; height = 40;
        curY = (int)(Math.random() * 900);
        labelL = new MyImageIcon("poleL.png").resize(width, height);
        labelR = new MyImageIcon("poleR.png").resize(width, height);
        labelB = new MyImageIcon("poleB.png").resize(width, height);
        int mL = (int)(Math.random() * 100);  // random if obj is moving left or right
        if(level > 3) {
            if(mL%4 == 0){ movingLeft = true; curX = 1200; setIcon(labelL);} 
            else if(mL%4 == 1) {movingLeft = false; curX = -width; setIcon(labelR);} 
            else if(mL%4 == 2) {twoSide = true; movingLeft = true; curX = 1200; setIcon(labelB);}
            else {twoSide = true; movingLeft = false; curX = -width; setIcon(labelB);}
        } else {
            if(mL%2 == 0){ movingLeft = true; curX = 1200; setIcon(labelL);} 
            else {movingLeft = false; curX = -width; setIcon(labelR);} 
        }
        setHorizontalAlignment(JLabel.CENTER);
        
        setBounds(curX, curY, width, height);
    }
    
    public void updateLocation() { //update Obstacle 
        
        if(movingLeft) curX -= 10; // move left according to the boolean
        else if(!movingLeft) curX += 10; // move right
        
        setLocation(curX, curY);
    }
    
    public boolean isMovingLeft() { return movingLeft; } //return true if that obs moves left
    public boolean isTwoSided() { return twoSide; }
    
    
}

class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}
