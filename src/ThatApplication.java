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
    private JButton             startButton, closeButton, highscoreButton;
    private JTextField          coinText;
    private int coin;
    private MySoundEffect       themeSound;
    private boolean gameRun =   true;

    public ThatApplication(int level, String backgroundImg, String themeSound)
    {
        AddComponents(backgroundImg, themeSound);
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

    public void AddComponents(String backgroundImg, String themeSound)
    {
        setTitle("EZ Coin Collector: Beware your step!");
        setBounds(100, 100, 1500, 800);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        
        coin = 0;
        
        contentpane = (JPanel)getContentPane();
        JOptionPane.showMessageDialog(new JFrame(), "Let's start!" , "Hello!",
                JOptionPane.INFORMATION_MESSAGE );
        
        MyImageIcon background = new MyImageIcon(backgroundImg);

        drawpane = new JLabel();
        drawpane.setIcon(background.resize(1500,800));
        drawpane.setLayout(null);

        JetpackLabel = new MyLabel();
        drawpane.add(JetpackLabel);
        addKeyListener(JetpackLabel);
        
        JPanel control  = new JPanel();
        control.setBounds(1050,0,150,30);
        coinText = new JTextField("0", 5);		
	coinText.setEditable(false);
        control.add(new JLabel("Coin : "));
        control.add(coinText);
                
        contentpane.add(control, BorderLayout.NORTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        repaint();
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
//                    comet.updateLocation();
//                    collision(comet);
//                    if(comet.getCurX() < -comet.getWidth() || comet.getCurX() > 1200) done = true;
//                    try { Thread.sleep(hard); } 
//                    catch (InterruptedException e) { e.printStackTrace(); }
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

class MyLabel extends JLabel implements KeyListener
{
    private int width = 150, height = 200;
    private MyImageIcon   HeroImage;
    private int HeroWidth = 250, HeroHeight = 200;
    private int HerocurX  = 90, HerocurY = 400;

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
                //if (HerocurY < (int)receive.height-HeroHeight-30) HerocurY += 20;
                //else HerocurY = (int)receive.height-HeroHeight-30;
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

abstract class customJLabel extends JLabel {
    protected int curX, curY;
    protected int width, height;
    protected MyImageIcon labelL, labelR;
    
    public customJLabel() { }
    public int getCurX(){ return curX; }
    public int getCurY(){ return curY; }
}

class Comet extends customJLabel {
    
    private boolean movingLeft;
    private boolean twoSide = false;
    private MyImageIcon labelB;
    
    public Comet(int level) {
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
