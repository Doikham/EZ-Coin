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
    private JLabel                  drawpane, levelLabel, backgroundLabel, soundLabel;
    private String levelString[] =  {"Baby","Teen","Adult","Master","God"};
    private int level = 0;
    private JComboBox               backgroundBox;
    private String bgString[] =     {"BG2","MUIC","Earth","Rick","RM"};
    private String bg =             "Resources/bg2.jpg";
    private JList                   soundList;
    private String soundString[] =  {"RM","Panjabi MC","Star Wars","Tequila","งัดถั่งงัด"};
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
        contentpane.setLayout(null);
    }
    
    public void AddComponents(){
        
        themeSound = new MySoundEffect("Resources/rm theme.wav");
        themeSound.playLoop();
        backgroundSetting = new MyImageIcon("Resources/bg_earth.jpg").resize(contentpane.getWidth(), contentpane.getHeight());
        drawpane = new JLabel();
        drawpane.setIcon(backgroundSetting);
        
        //Level Label
        levelLabel = new JLabel("Choose your skill: ");
        levelLabel.setBounds(675, 190, 400, 60);
        levelLabel.setVisible(true);
        contentpane.add(levelLabel);
        
        //Level Radio
        JRadioButton Button[] = new JRadioButton[5];
        ButtonGroup Radio = new ButtonGroup();
        for(int i=0; i<5; i++){
            Button[i] = new JRadioButton(levelString[i]);
            Radio.add(Button[i]);
            Button[i].setBounds(980, 200 + (30 * i), 120, 30);
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
        backgroundLabel = new JLabel("Choose your battlefield:");
        backgroundLabel.setBounds(100, 470, 330, 60);
        backgroundLabel.setVisible(true);
        contentpane.add(backgroundLabel);
        
        //Background Combobox
        backgroundBox = new JComboBox(bgString);
        backgroundBox.setBounds(420, 485, 225, 45);
        backgroundBox.setVisible(true);
        contentpane.add(backgroundBox);
        backgroundBox.setSelectedIndex(0);
        backgroundBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                switch (backgroundBox.getSelectedIndex()) {
                    case 0:
                        bg = "/Resources/bg2.jpg";
//                        bgIcon = new MyImageIcon("bg1.png").resize(bgimgsizeW, bgimgsizeH);
//                        DisplayBgIcon.setIcon(bgIcon);
                        break;
                    case 1:
                        bg = "/Resources/bg_muic.jpg";                       
                        break;
                    case 2:
                        bg = "/Resources/bg_earth.jpg";
                        break;
                    case 3:
                        bg = "/Resources/bg_rick.jpg";
                        break;
                    case 4:
                        bg = "/Resources/bg_rm.jpg";
                        break;                  
                    default: {
                    }
                    break;
                }
            }
        });
        
        //Sound Label
        soundLabel = new JLabel("Select Character Here :");
        soundLabel.setVisible(true);
        contentpane.add(soundLabel);
        
        //Sound List
        JList soundList = new JList(soundString);
        soundList.setVisibleRowCount(5);
        soundList.setBounds(475, 200, 140, 175);
        contentpane.add(soundList);
        soundList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        soundList.setSelectedIndex(0);
        soundList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    switch (soundList.getSelectedIndex()) {
                        case 0:
                            sound = "Resource/rm theme.wav";
//                            charL = "charL1.png";
//                            charR = "charR1.png";
//                            charIcon = new MyImageIcon("charR1.png");
//                            DisplayCharIcon.setIcon(charIcon);
//                            charTextField1.setText("Have you ever been ");
//                            charTextField2.setText("to Nong Kye?!?!?!");
                            break;
                        case 1:
                            sound = "resources/Panjabi MC.wav";
                            break;
                        case 2:
                            sound = "Resources/Star Wars.wav";
                            break;
                        case 3:
                            sound = "Resources/Tequila.wav";
                            break;
                        case 4:
                            sound = "Resources/งัดถั่งงัด.wav";
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        
        startButton = new JButton("Start");
        startButton.setBounds(775, 480, 250, 130);
        startButton.setVisible(true);
        contentpane.add(startButton);
        startButton.addActionListener(new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent ae) {
                startCollect();
            }
        });
        
        exitButton = new JButton("Exit");
        exitButton.setBounds(775, 630, 250, 130);
        exitButton.setVisible(true);
        contentpane.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Good Bye", "That's it", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        contentpane.add(drawpane);
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
