package hmi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import race.EventRaces;
import race.Race;
import race.Runner;

@SuppressWarnings("serial")
public class HmiMain extends JFrame {

    
    
    private EventRaces evt;
    private ArrayList<Runner> participants;
    static HmiMain frame;
    
    public EventRaces getEvt() {
        return evt;
    }

    public ArrayList<Runner> getParticipants() {
        return participants;
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new HmiMain();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public HmiMain() {
        setTitle("Races management");
        participants = new ArrayList<Runner>();
        evt = new EventRaces(participants);
        TableParticipants tableParticipants = new TableParticipants(this);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        //
        setBounds(100, 100, 1133, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        contentPane.add(menuBar, BorderLayout.NORTH);

        JMenu mnParticipants = new JMenu("Participants list");
        menuBar.add(mnParticipants);
        
        JMenuItem mnLoadListFrom = new JMenuItem("Add participantsfrom");
        mnLoadListFrom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // erase or add list
                evt.read("list");
                tableParticipants.getModel().updateData(participants);;
            }
        });
        mnParticipants.add(mnLoadListFrom);
        
        JMenuItem mnSaveListTo = new JMenuItem("save list to");
        mnSaveListTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("save list to ");
            }
        });
        mnParticipants.add(mnSaveListTo);

        JMenuItem mnGenerateRaces = new JMenuItem("generate races");
        mnGenerateRaces.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = (tabbedPane.getTabCount() - 1); i > 0; i--) {
                    tabbedPane.remove(i);
                }
                for (Race c : evt.races) {
                    PanelRace course = new PanelRace(c);
                    tabbedPane.addTab(c.raceName, null, course, null);
                }
            }
        });
        mnParticipants.add(mnGenerateRaces);

        JMenuItem mnCloseAllRaces = new JMenuItem("close all races");
        mnCloseAllRaces.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = (tabbedPane.getTabCount() - 1); i > 0; i--) {
                    tabbedPane.remove(i);
                }
            }
        });
        mnParticipants.add(mnCloseAllRaces);

        JMenu mnNewMenu_4 = new JMenu("Help");
        menuBar.add(mnNewMenu_4);
        JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
        mnNewMenu_4.add(mntmNewMenuItem_3);
        JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem("New check item");
        mnNewMenu_4.add(chckbxmntmNewCheckItem);
        JRadioButtonMenuItem rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("New radio item");
        mnNewMenu_4.add(rdbtnmntmNewRadioItem);

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        panel.add(tabbedPane);
        // 1st tab in participant list
        JPanel p_particpants = new JPanel();
        p_particpants.setLayout(new BoxLayout(p_particpants, BoxLayout.Y_AXIS));
        p_particpants.add(tableParticipants, BorderLayout.CENTER);
        tabbedPane.addTab("Participants", null, p_particpants, null);
        // the next tab are races
    }
}
