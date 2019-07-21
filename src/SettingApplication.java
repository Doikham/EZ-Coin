import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SettingApplication extends JFrame {
    
    private JPanel                  contentpane;
    private JLabel                  drawpane, levelLabel, backgroundLabel, soundLabel, big, ney, hon, by;
    private String levelString[] =  {"Baby","Teen","Adult","Master","God"};
    private int level = 0;
    private JComboBox               backgroundBox;
    private String bgString[] =     {"Deep Space","Mahidol University International College","on Earth","Rick and Morty","Atmosphere"};
    private String bg =             "Resources/bg2.jpg";
    private JList                   soundList;
    private String soundString[] =  {"Rick and Morty Theme","Munidya - Panjabi MC","Star Wars Theme","Tequila","งัดถั่งงัด","เต่างอย"};
    private String sound =          "Resource/rm theme.wav";
    private MySoundEffect           themeSound;
    private JButton                 startButton, exitButton;
    private MyImageIcon             backgroundSetting;
    
    public SettingApplication(){
        CreatePage();
        AddComponents();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                themeSound.stop();
            }
        });
        validate();
        repaint();
    }
    
    public void CreatePage(){
        setTitle("Choose your own way, Noobs");
        setBounds(100, 100, 1500, 800);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane = (JPanel) getContentPane();
        //contentpane.setLayout(null);
    }
    
    public void AddComponents(){
        
        themeSound = new MySoundEffect("Resources/rm theme.wav");
        themeSound.playLoop();
        backgroundSetting = new MyImageIcon("Resources/bg2.jpg").resize(contentpane.getWidth(), contentpane.getHeight());
        drawpane = new JLabel();
        drawpane.setIcon(backgroundSetting);
        
        //Level Label
        levelLabel = new JLabel("Select difficulty: ");
        levelLabel.setBounds(2, 0, 300, 100);
        levelLabel.setVisible(true);
        levelLabel.setForeground(Color.WHITE);
        contentpane.add(levelLabel);
        
        //Level Radio
        JRadioButton Button[] = new JRadioButton[5];
        ButtonGroup Radio = new ButtonGroup();
        for(int i=0; i<5; i++){
            Button[i] = new JRadioButton(levelString[i]);
            Radio.add(Button[i]);
            Button[i].setBounds(150 + (100 * i), 20 , 100, 50);
            contentpane.add(Button[i]);                        
        }
        Button[0].setSelected(true);
        Button[0].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                level = 0;
            }
        });
        Button[1].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                level = 1;
            }
        });
        Button[2].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                level = 2;
            }
        });
        Button[3].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                level = 3;
            }
        });
        Button[4].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                level = 4;
            }
        });
        
        //Background Label
        backgroundLabel = new JLabel("Select location:");
        backgroundLabel.setBounds(2, 100, 330, 60);
        backgroundLabel.setVisible(true);
        backgroundLabel.setForeground(Color.WHITE);
        contentpane.add(backgroundLabel);
        
        //Background Combobox
        backgroundBox = new JComboBox(bgString);
        backgroundBox.setBounds(150, 100, 225, 45);
        backgroundBox.setVisible(true);
        contentpane.add(backgroundBox);
        backgroundBox.setSelectedIndex(0);
        backgroundBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                switch (backgroundBox.getSelectedIndex()) {
                    case 0:
                        bg = "Resources/bg2.jpg";
//                        bgIcon = new MyImageIcon("bg1.png").resize(bgimgsizeW, bgimgsizeH);
//                        DisplayBgIcon.setIcon(bgIcon);
                        break;
                    case 1:
                        bg = "Resources/bg_muic.jpg";                       
                        break;
                    case 2:
                        bg = "Resources/bg_earth.jpg";
                        break;
                    case 3:
                        bg = "Resources/bg_rick.jpg";
                        break;
                    case 4:
                        bg = "Resources/bg_rm.jpg";
                        break;                  
                    default: {
                    }
                    break;
                }
            }
        });
        
        //Sound Label
        soundLabel = new JLabel("Select song:");
        soundLabel.setBounds(2,200,140,30);
        soundLabel.setVisible(true);
        soundLabel.setForeground(Color.WHITE);
        contentpane.add(soundLabel);
        
        //Sound List
        JList soundList = new JList(soundString);
        soundList.setVisibleRowCount(5);
        soundList.setBounds(150, 200, 140, 155);
        contentpane.add(soundList);
        soundList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        soundList.setSelectedIndex(0);
        soundList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    switch (soundList.getSelectedIndex()) {
                        case 0:
                            sound = "Resources/rm theme.wav";
                            themeSound.stop();
                            themeSound = new MySoundEffect(sound);
                            themeSound.playLoop();
                            break;
                        case 1:
                            sound = "Resources/Panjabi MC.wav";
                            themeSound.stop();
                            themeSound = new MySoundEffect(sound);
                            themeSound.playLoop();
                            break;
                        case 2:
                            sound = "Resources/Star Wars.wav";
                            themeSound.stop();
                            themeSound = new MySoundEffect(sound);
                            themeSound.playLoop();
                            break;
                        case 3:
                            sound = "Resources/Tequila.wav";
                            themeSound.stop();
                            themeSound = new MySoundEffect(sound);
                            themeSound.playLoop();
                            break;
                        case 4:
                            sound = "Resources/งัดถั่งงัด.wav";
                            themeSound.stop();
                            themeSound = new MySoundEffect(sound);
                            themeSound.playLoop();
                            break;
                        case 5:
                            sound = "Resources/เต่างอย.wav";
                            themeSound.stop();
                            themeSound = new MySoundEffect(sound);
                            themeSound.playLoop();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        
        startButton = new JButton("Start");
        startButton.setBounds(100, 480, 200, 100);
        startButton.setVisible(true);
        contentpane.add(startButton);
        startButton.addActionListener(new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent ae) {
                startCollect();
            }
        });
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(100, 630, 200, 100);
        exitButton.setVisible(true);
        contentpane.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Good Bye", "That's it", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        by = new JLabel("Made by:");
        by.setBounds(1100,490,300,100);
        by.setFont(new Font(by.getFont().getName(), by.getFont().getStyle(), 40));
        by.setForeground(Color.orange);


        big = new JLabel("Sethawit Suwincharat 5980027");
        big.setBounds(1100,550,300,100);
        big.setFont(new Font(big.getFont().getName(), big.getFont().getStyle(), 20));
        big.setForeground(Color.white);

        ney = new JLabel("Neramit Singh 5980394");
        ney.setBounds(1100,600,300,100);
        ney.setFont(new Font(ney.getFont().getName(), ney.getFont().getStyle(), 20));
        ney.setForeground(Color.white);

        hon = new JLabel("Poomdanai Dechawaleekul 6080036");
        hon.setBounds(1100,650,400,100);
        hon.setFont(new Font(hon.getFont().getName(), hon.getFont().getStyle(), 20));
        hon.setForeground(Color.white);

        contentpane.add(by);
        contentpane.add(big);
        contentpane.add(ney);
        contentpane.add(hon);
        contentpane.add(drawpane);
        repaint();
        validate();
    }
    
    public void startCollect(){
        Thread game = new Thread(){
            public void run(){
                themeSound.stop();
                ThatApplication game = new ThatApplication(level, bg, sound);
            }
        };
        game.start();
        setVisible(false);
        dispose();
    }
}

class MySoundEffect {

    private java.applet.AudioClip audio_setting;

    public MySoundEffect(String filename) {
        try {
            java.io.File file3 = new java.io.File(filename);
            audio_setting = java.applet.Applet.newAudioClip(file3.toURL());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void playOnce() {
        audio_setting.play();
    }

    public void playLoop() {
        audio_setting.loop();
    }

    public void stop() {
        audio_setting.stop();
    }
}
