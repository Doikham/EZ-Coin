import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ThatApplication extends JFrame implements KeyListener, MouseListener{
    private JLabel                  contentpane;
    private MyLabel                 JetpackLabel;
    private JButton                 startButton, closeButton, highscoreButton;
    private JTextField              coinText;
    private int coin;
    private MySoundEffect           themeSoundThat;
    private boolean gameRun =       true;
    private boolean hit, laserHit = false;
    private ArrayList <Comet> comets = new ArrayList<>();
    private ArrayList <Laser> lasers = new ArrayList<>();

    public ThatApplication(int level, String backgroundImg, String themeSound)
    {
        AddComponents(backgroundImg, themeSound);
        //setCoinThread();
        addKeyListener(this);
        addMouseListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                themeSoundThat.stop();
            }
        });
        while(gameRun) {
            createComet(level);
            repaint();
            try { Thread.sleep(1000-level*100); } 
            catch (InterruptedException e) { e.printStackTrace(); }
            createCoin(level);
            repaint();
            try { Thread.sleep(1000-level*100); } 
            catch (InterruptedException e) { e.printStackTrace(); }
            repaint();
        }
        themeSoundThat.stop();
    }

    public void AddComponents(String backgroundImg, String themeSound)
    {
        setTitle("EZ Coin Collector: Beware your step!");
        setBounds(100, 100, 1300, 750);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );        
        coin = 0;
        
        setContentPane(contentpane = new JLabel());
        
        JOptionPane.showMessageDialog(new JFrame(), "Hold and release the mouse to use laser!" , "Hello!",
                JOptionPane.INFORMATION_MESSAGE );
        
        themeSoundThat = new MySoundEffect(themeSound);
        themeSoundThat.playLoop();
       
        MyImageIcon background = new MyImageIcon(backgroundImg);
        contentpane.setIcon(background.resize(1500,800));
        contentpane.setLayout(null);

        JetpackLabel = new MyLabel();
        contentpane.add(JetpackLabel);
        
        JPanel control  = new JPanel();
        control.setBounds(1150,0,150,30);
        coinText = new JTextField("0", 5);		
	coinText.setEditable(false);
        control.add(new JLabel("Coin : "));
        control.add(coinText);
                
        contentpane.add(control, BorderLayout.NORTH);
        
        //repaint();
        validate();
    }

    public void createComet(int level){
        Thread cometThread = new Thread() {
            @Override
            public void run()
            {
                int speed = 50;
                switch(level){
                    case 0: speed = 100; 
                    break;
                    case 1: speed = 80; 
                    break;
                    case 2: speed = 60; 
                    break;
                    case 3: speed = 35; 
                    break;
                    case 4: speed = 15; 
                    break;
                }
                comets.add(new Comet(level));
                Comet comet = comets.get(comets.size()-1);
                contentpane.add(comet);
                repaint();
                boolean done = false;
                while (!done && gameRun) { 
                    comet.updateLocation();
                    cometCollision(comet);
                    if(comet.getCurX() < 0) done = true;
                    try { Thread.sleep(speed); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                comets.remove(0);
                contentpane.remove(comet);
                repaint();
            }
        };
        cometThread.start();
    }
    
    public void createCoin(int level){
        Thread coinThread = new Thread() {
            public void run()
            {
                int speed = 50;
                switch(level){
                    case 0: speed = 100; 
                    break;
                    case 1: speed = 80; 
                    break;
                    case 2: speed = 60; 
                    break;
                    case 3: speed = 35; 
                    break;
                    case 4: speed = 15; 
                    break;
                }                
                Coin coin = new Coin(level);
                contentpane.add(coin);
                repaint();
                boolean done = false;
                while (!done && gameRun) { 
                    coin.updateLocation();
                    coinCollision(coin);
                    if(coin.getCurX() < 0) done = true;
                    try { Thread.sleep(speed); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                contentpane.remove(coin);
                hit = false;
                repaint();
            }
        };
        coinThread.start();
    }
    
    public void createLaser(){
        Thread laserThread = new Thread() {
            public void run()
            {
                int speed = 50;
                lasers.add(new Laser(JetpackLabel.curX, JetpackLabel.curY));
                Laser laser = lasers.get(lasers.size()-1) ;
                contentpane.add(laser);
                repaint();
                boolean done = false;
                while (!done && gameRun) { 
                    laser.updateLocation();
                    for(int i = 0; i < comets.size();i++)
                    {
                        laserCollision(laser,comets.get(i));
                    }
                    if(laser.getCurX() > 1300) done = true;
                    try { Thread.sleep(speed); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                lasers.remove(0);
                contentpane.remove(laser);
                laserHit = false;
                repaint();
            }
        };
        laserThread.start();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {            
            case KeyEvent.VK_UP:
                JetpackLabel.updateLocation(0);
                break;
            case KeyEvent.VK_DOWN:
                JetpackLabel.updateLocation(1);
                break;
            case KeyEvent.VK_LEFT:
                JetpackLabel.updateLocation(2);
                break;
            case KeyEvent.VK_RIGHT:
                JetpackLabel.updateLocation(3);
                break;          
            default:
                break;                 
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    synchronized public void cometCollision(Comet obs)
    {
	if ( JetpackLabel.getBounds().intersects(obs.getBounds()) )
        {
            gameRun = false;
            contentpane.remove(JetpackLabel);
            JOptionPane.showMessageDialog( new JFrame(), "Unfortunately, we will miss you! Your have collected " + coin + " coin", "Chicken Dinner", JOptionPane.INFORMATION_MESSAGE );                         
            setVisible(false);
            System.exit(-1);
        }
    }
    
    synchronized public void coinCollision(Coin obs)
    {
	if ( !hit && JetpackLabel.getBounds().intersects(obs.getBounds()) )
        {           
            contentpane.remove(obs);   
            coin++;
            coinText.setText(Integer.toString(coin));
            hit = true;
        }
    }
    
    synchronized public void laserCollision(Laser obs, Comet comet)
    {
	if ( !laserHit && comet.getBounds().intersects(obs.getBounds()) )
        {
            JOptionPane.showMessageDialog( new JFrame(), "HITTTTTTT", "Hit", JOptionPane.INFORMATION_MESSAGE );
            laserHit = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        createLaser();
        repaint();
//        try { Thread.sleep(1000-level*100); } 
//        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}

class MyLabel extends myJLabel
{
    public MyLabel()
    {
        super();
        curX = 100;
        curY = 400;
        width = 90;
        height = 150;
        Jetpack = new MyImageIcon("Resources/jetpackboy.png").resize(width,height);
        setHorizontalAlignment(JLabel.CENTER);
        setIcon(Jetpack);
        setBounds(curX, curY, width, height);
    }
    
    synchronized public void updateLocation(int a) {
        switch(a) {
            case 0: if (curY > 0)  
                        curY -= 20;  
                    break;
            case 1: if (curY < 750-height) 
                        curY += 20; 
                    break;
            case 2: if (curX > 0) 
                        curX -= 20;
                    break;
            case 3: if (curX < 1300-width) 
                        curX += 20;
                    break;
        }
        setLocation(curX, curY);
    }
}

abstract class myJLabel extends JLabel {
    protected int curX, curY;
    protected int width, height;
    protected ImageIcon Jetpack;
    
    public myJLabel() { }
    public int getCurX(){ return curX; }
    public int getCurY(){ return curY; }
}

class Comet extends myJLabel {
   
    private MyImageIcon cometLabel;

    
    public Comet(int level) {
        super();
        width = 200; height = 100;
        curY = (int)(Math.random() * 650);      
        cometLabel = new MyImageIcon("Resources/comet.png").resize(width, height);
        curX = 1350;
        setIcon(cometLabel);
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(curX, curY, width, height);
    }
    
    public void updateLocation() {       
        curX -= 20;    
        setLocation(curX, curY);
    }
}

class Coin extends myJLabel {
    
    private MyImageIcon coinLabel;
    
    public Coin(int level) {
        super();
        width = 100; height = 100;
        curY = (int)(Math.random() * 650);      
        coinLabel = new MyImageIcon("Resources/coin.png").resize(width, height);
        curX = 1350;
        setIcon(coinLabel);
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(curX, curY, width, height);
    }
    
    public void updateLocation() {       
        curX -= 20;    
        setLocation(curX, curY);
    }
}

class Laser extends myJLabel {
    
    private MyImageIcon laserLabel;
    
    public Laser(int x, int y) {
        super();
        width = 150; height = 100;
        curY = y;      
        laserLabel = new MyImageIcon("Resources/laser.png").resize(width, height);
        curX = x;
        setIcon(laserLabel);
        //setHorizontalAlignment(JLabel.CENTER);
        setBounds(curX, curY, width, height);
    }
    
    public void updateLocation() {       
        curX += 20;    
        setLocation(curX, curY);
    }
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
