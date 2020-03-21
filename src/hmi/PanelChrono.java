package hmi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import race.Race;
import race.Runner;

@SuppressWarnings("serial")
public class PanelChrono extends JPanel {
    boolean go = false;
    private PanelRace panelRace;

    public Race getRace() {
        return panelRace.getRace();
    }

    public TableStart getRaceTablerStart() {
        return panelRace.getTablerStart();
    }

    public TableFinish getRaceTableFinish() {
        return panelRace.getTableFinish();
    }

    private JTextField jtextTime = new JTextField("Horloge", 5);;
    private JTextField jtextChrono = new JTextField("0:00:00", 5);
//    ImageIcon oui = new ImageIcon(HmiMain.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png"));
//    ImageIcon non = new ImageIcon(HmiMain.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png"));
    private final JButton btStartRace = new JButton("START");
    private final JButton btEndRace = new JButton("END");

    private LocalTime beginingTime = LocalTime.now();

    private javax.swing.Timer timerForTime = new javax.swing.Timer(1000, new ClockListenerHeure());
    private javax.swing.Timer timerForChrono = new javax.swing.Timer(1000, new ClockListenerChrono());
    private final Component horizontalGlue = Box.createHorizontalGlue();
    private final Component horizontalGlue_1 = Box.createHorizontalGlue();
    private final Component horizontalGlue_2 = Box.createHorizontalGlue();
    private final Panel panel_0 = new Panel();
    private final Panel panel_1 = new Panel();
    private final Panel panel_2 = new Panel();
    private final Panel panel_4 = new Panel();
    private final Panel panelGlue1 = new Panel();
    private final Panel panelGlue2 = new Panel();
    private final Panel panelGlue0 = new Panel();
    private final Component horizontalGlue_3 = Box.createHorizontalGlue();
    private final JButton saveResultat = new JButton("SAVE");

    public PanelChrono(PanelRace pPanelCourse) {
        panelRace = pPanelCourse;

        setMaximumSize(new Dimension(32767, 52));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(panel_0);
        panel_0.setLayout(new BoxLayout(panel_0, BoxLayout.X_AXIS));
        panel_0.add(jtextTime);
        jtextTime.setEditable(false);
        jtextTime.setFont(new Font("sansserif", Font.PLAIN, 24));
        panel_0.add(horizontalGlue);
        // startCourse.setIcon();

        JLabel labelChrono = new JLabel("Temps: ");
        labelChrono.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                String newDebut = (String) JOptionPane.showInputDialog(null, "", "new chrono",
                        JOptionPane.QUESTION_MESSAGE, null, null, "");
                ModifyChrono(newDebut);
            }
        });
        panel_0.add(labelChrono);
        labelChrono.setFont(new Font("sansserif", Font.PLAIN, 24));
        panel_0.add(jtextChrono);
        jtextChrono.setEditable(false);
        jtextChrono.setFont(new Font("sansserif", Font.PLAIN, 24));

        add(panelGlue0);
        panelGlue0.add(horizontalGlue_1);

        add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        panel_1.add(btStartRace);

        add(panelGlue1);
        panelGlue1.add(horizontalGlue_2);

        add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));
        panel_2.add(btEndRace);
        btEndRace.setEnabled(go);
        btEndRace.setToolTipText("tous les coureurs non arrivés auront temps=abandon");

        add(panelGlue2);

        panelGlue2.add(horizontalGlue_3);

        add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));

        // Button save results
        saveResultat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PrintWriter writer;
                try {
                    writer = new PrintWriter(getRace().raceName + ".csv", "UTF-8");
                    for (Runner c : getRaceTableFinish().getModel().data) {
                        c.saveResultat(writer);
                    }
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        panel_4.add(saveResultat);

        // Button end of race
        btEndRace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                go = false;
                stopChrono();
                btEndRace.setEnabled(go);
                for (Runner c : getRaceTablerStart().getModel().data) {
                    c.setRank(getRace().getRang());
                    c.setTime(Runner.ABANDON);
                    getRaceTableFinish().getModel().addRow(c);
                }
                getRaceTablerStart().getModel().data.clear();
                getRaceTablerStart().getModel().fireTableDataChanged();
                getRaceTablerStart().updateUI();
            }
        });

        // Button start of race
        btStartRace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                go = true;
                startChrono();
                stopHeure();
                btStartRace.setEnabled(!go);
                btEndRace.setEnabled(go);

            }
        });

        timerForTime.start();
    }

    /*** Event timerForTime ****************************************************/
    class ClockListenerHeure implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            jtextTime.setText(df.format(Calendar.getInstance().getTime()));
        }
    }

    public void restartHeure() {
        timerForTime.start();
    }

    public void stopHeure() {
        timerForTime.stop();
    }

    /*** Event timerForChrono ***************************************************/
    class ClockListenerChrono implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            updateChrono();
        }
    }

    public void startChrono() {
        beginingTime = LocalTime.now();
        timerForChrono.start();
    }

    public void ModifyChrono(String newDebut) {
        beginingTime = LocalTime.parse(newDebut); // "10:15:30"
        jtextTime.setText(newDebut);
    }

    public void stopChrono() {
        timerForChrono.stop();
    }

    private void updateChrono() {
        String tps = getChronoTime();
        jtextChrono.setText(tps);
    }

    /** return timeChrono - beginingTime */
    public String getChronoTime() {
        LocalTime now = LocalTime.now();
        Duration duration = Duration.between(beginingTime, now);
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        return String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
    }

}
