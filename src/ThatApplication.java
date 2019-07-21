import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ThatApplication extends JFrame implements KeyListener{
    private JLabel              contentpane;
    private MyLabel             JetpackLabel;
    private JButton             startButton, closeButton, highscoreButton;
    private JTextField          coinText;
    private int coin;
    private MySoundEffect       themeSound;
    private boolean gameRun =   true;

    public ThatApplication(int level, String backgroundImg, String themeSound)
    {
        AddComponents(backgroundImg, themeSound);
        //setCoinThread();
        addKeyListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //themeSound.stop();
            }
        });
        while(gameRun) {
            setComet(level);
            repaint();
            try { Thread.sleep((100)); } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        setVisible(false);
        //themeSound.stop();
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
        
        JOptionPane.showMessageDialog(new JFrame(), "Let's start!" , "Hello!",
                JOptionPane.INFORMATION_MESSAGE );
        
        //themeSound = new MySOundEffect
       
        MyImageIcon background = new MyImageIcon(backgroundImg);
        contentpane.setIcon(background.resize(1500,800));
        contentpane.setLayout(null);

        JetpackLabel = new MyLabel();
        contentpane.add(JetpackLabel);
        
        JPanel control  = new JPanel();
        control.setBounds(1350,0,150,30);
        coinText = new JTextField("0", 5);		
	coinText.setEditable(false);
        control.add(new JLabel("Coin : "));
        control.add(coinText);
                
        contentpane.add(control, BorderLayout.NORTH);
        
        repaint();
        validate();
    }

    public void setComet(int level){
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
                Comet comet = new Comet(level);
                contentpane.add(comet);
                repaint();
                boolean done = false;
                while (!done && gameRun) { 
                    comet.updateLocation();
                    //collision(comet);
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
        switch (ke.getKeyCode()) {  //main character, call MyToggleLabel.updateLocation();           
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    synchronized public void collision(Comet obs)
    {
//	if ( character.getBounds().intersects(obs.getBounds()) )
//        {
//            if(obs.isMovingLeft() || obs.isTwoSided()) {
//                if(character.getWidth()+character.getCurX() > obs.getCurX() + 10) {
//                    if(character.getCurY() < obs.getCurY()) {
//                        while(character.getCurY() + character.getHeight() > obs.getCurY()){
//                            character.updateLocation(3);
//                        } 
//                    }
//                    else {
//                        for (int i = 0; i < 3; i++) character.updateLocation(4);
//                    }
//                }
//                else {
//                    gameRunning = false;
//                    contentpane.remove(character);
//                    JOptionPane.showMessageDialog( new JFrame(), "You Died! Your score is " + score, "Game Ended", JOptionPane.INFORMATION_MESSAGE );
//                }
//            }
//            if(!obs.isMovingLeft() || obs.isTwoSided()){
//                if(character.getCurX()< obs.getCurX() + obs.getWidth() - 10) {
//                    if(character.getCurY() < obs.getCurY()) {
//                        while(character.getCurY() + character.getHeight() > obs.getCurY()){
//                            character.updateLocation(3);
//                        } 
//                    }
//                    else {
//                        for (int i = 0; i < 3; i++) character.updateLocation(4);
//                    }
//                }
//                else {
//                    gameRun = false;
//                    contentpane.remove(character);
//                    JOptionPane.showMessageDialog( new JFrame(), "You Died! Your score is " + score, "Game Ended", JOptionPane.INFORMATION_MESSAGE );
//                }
//            }
//	}
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
                        curY -= 10;  
                    break;
            case 1: if (curY < 750-height) 
                        curY += 10; 
                    break;
            case 2: if (curX > 0) 
                        curX -= 10;
                    break;
            case 3: if (curX < 1300-width) 
                        curX += 10;
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
        curY = (int)(Math.random() * 750);
        curX = 1350;
        cometLabel = new MyImageIcon("Resources/comet.png").resize(width, height);
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(curX, curY, width, height);
    }
    
    public void updateLocation() {       
        if(true)curX -= 20;    
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
