import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChooseApplication extends  JFrame implements PropertyChangeListener {

    private JButton             startBtn, settingBtn;
    private JPanel              contentpane;
    private JLabel              drawpane, username, appLabel, logoLabel;
    private MyImageIcon         backgroundImg, logo;
    private MySoundEffectChoose themeSoundChoose;

    public ChooseApplication(){
        setTitle("EZ is EZ. Nothing is not EZ");
        setBounds(100, 100, 1500, 800);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentpane = (JPanel) getContentPane();
        AddComponents();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //themeSoundChoose.stop();
            }
        });
    }

    public void AddComponents() {
        themeSoundChoose = new MySoundEffectChoose("Resources/Tequila.wav");
        themeSoundChoose.playLoop();
        backgroundImg = new MyImageIcon("Resources/bg2.jpg").resize(contentpane.getWidth(), contentpane.getHeight());
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);

        logo = new MyImageIcon("Resources/coin.png").resize(275, 220);
        logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        logoLabel.setBounds(600,100,1100,400);

        appLabel = new JLabel("EZ Coin Collector");
        appLabel.setBounds(550,10,700,100);
        appLabel.setFont(new Font(appLabel.getFont().getName(), appLabel.getFont().getStyle(), 50));
        appLabel.setForeground(Color.orange);
        
        startBtn = new JButton("Start");
        startBtn.setBounds(350, 535, 350, 70);
        startBtn.setFont(new Font("Arial", Font.PLAIN, 40));
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread gameThread = new Thread() {
                    public void run() {
                        ThatApplication game = new ThatApplication(0, "Resources/bg2.jpg", "Resources/rm theme.wav");
                    }
                };
                gameThread.start();
                setVisible(false);
                themeSoundChoose.stop();
                dispose();
            }
        });

        settingBtn = new JButton("Option");
        settingBtn.setBounds(730, 535, 350, 70);

        //option_button.setLocation(350,350);
        //option_button.setPreferredSize(new Dimension(250,100));
        settingBtn.setFont(new Font("Arial", Font.PLAIN, 40));
        settingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SettingApplication option = new SettingApplication();
                option.setVisible(true);
                themeSoundChoose.stop();
                dispose();
            }
        });
        username = new JLabel("");
        username.setBounds(150, 60, 500, 300);

        contentpane.add(username);
        contentpane.add(appLabel);
        contentpane.add(logoLabel);
        contentpane.add(startBtn);
        contentpane.add(settingBtn);
        contentpane.add(drawpane);
        repaint();
        validate();

    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("startButtonActionPerformed")) {
            this.username.setText("Welcome " + event.getNewValue() + "!");
            username.setFont(new Font(username.getFont().getName(), username.getFont().getStyle(), 50));
            username.setForeground(Color.orange);
            username.setBounds(500, 15, 1200, 900);
        }
    }

}

class MySoundEffectChoose {

    private java.applet.AudioClip audio_choose;

    public MySoundEffectChoose(String filename) {
        try {
            java.io.File file2 = new java.io.File(filename);
            audio_choose = java.applet.Applet.newAudioClip(file2.toURL());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void playOnce() {
        audio_choose.play();
    }

    public void playLoop() {
        audio_choose.loop();
    }

    public void stop() {
        audio_choose.stop();
    }
}