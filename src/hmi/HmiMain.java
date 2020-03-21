package hmi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import race.EventRaces;
import race.Race;
import race.Runner;

@SuppressWarnings("serial")
public class HmiMain extends JFrame {

    public EventRaces evt;
    static HmiMain frame;

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
        // new runner list and event
        ArrayList<Runner> participants = new ArrayList<Runner>();
        evt = new EventRaces(participants, "05/09/2019", "list");

        // read excel file
        evt.read("list");

        //
        setBounds(100, 100, 1133, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 1, 0, 0));

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frame, "etes vous sûr de fermer?",
                        "Fermer l'appli course", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane);

        // 1st tab in participant list
        JPanel p_particpants = new JPanel();
        p_particpants.setLayout(new BoxLayout(p_particpants, BoxLayout.Y_AXIS));
        p_particpants.add(new TableParticipants(participants, this), BorderLayout.CENTER);
        tabbedPane.addTab("Participants", null, p_particpants, null);

        // next, runs are the following tab
        for (Race c : evt.races) {
            PanelRace course = new PanelRace(c);
            tabbedPane.addTab(c.raceName, null, course, null);
        }
    }
}
