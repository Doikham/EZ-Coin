import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainApplication extends JFrame {

    private JPanel              contentpane;
    private JLabel              drawpane, logoLabel, nameLabel, appLabel;
    private MyImageIcon         backgroundImg, logo;
    private JButton             startButton;
    private JTextField          textView;
    private MySoundEffectIntro  themeSoundMain;

    public static void main(String[] args) { new MainApplication().setVisible(true); }

    public MainApplication()
    {
        setTitle("EZ Coin Collector");
        setBounds(100, 100, 1500, 800);
        setResizable(false);
        setVisible(true);

        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        validate();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                themeSoundMain.stop();
            }
        });

        contentpane = (JPanel) getContentPane();
        AddComponents();
    }

    public void AddComponents()
    {
        themeSoundMain = new MySoundEffectIntro("Resources/rm theme.wav");
        themeSoundMain.playLoop();
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

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(250,515,300,100);
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), nameLabel.getFont().getStyle(), 50));
        nameLabel.setForeground(Color.orange);

        textView = new JTextField(30);
        textView.setBounds(450, 525, 350, 70);
        textView.setFont(new Font("Sans serif", Font.PLAIN, 20));
        textView.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                firePropertyChange("textViewKeyReleased", "", textView.getText());
            }

        });

        startButton = new JButton("Next");
        startButton.setBounds(850, 525, 250, 70);
        startButton.setFont(new Font("Sans serif", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startButtonActionPerformed(evt);
            }

            private void startButtonActionPerformed(ActionEvent evt) {
                themeSoundMain.stop();
                ChooseApplication frame2 = new ChooseApplication();
                addPropertyChangeListener(frame2);
                frame2.setVisible(true);
                setVisible(false);
                firePropertyChange("startButtonActionPerformed", "", textView.getText());

            }
        });
        contentpane.add(appLabel);
        contentpane.add(nameLabel);
        contentpane.add(logoLabel);
        contentpane.add(textView);
        contentpane.add(startButton);
        contentpane.add(drawpane);
        repaint();
        validate();
    }
}

class MySoundEffectIntro {

    private java.applet.AudioClip audio_main;

    public MySoundEffectIntro(String filename) {
        try {
            java.io.File file1 = new java.io.File(filename);
            audio_main = java.applet.Applet.newAudioClip(file1.toURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playOnce() {
        audio_main.play();
    }

    public void playLoop() {
        audio_main.loop();
    }

    public void stop() {
        audio_main.stop();
    }
}