package hmi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import race.Race;
import race.Runner;
import java.awt.Component;

@SuppressWarnings("serial")
public class PanelChrono extends JPanel {
    // Attributes
    private boolean go = false;

    // Main race panel
    private PanelRace panelRace;

    // swing components
    private LocalTime startTime = LocalTime.now();
    private final JTextField txtClock = new JTextField("Horloge", 5);
    private final JLabel lbElapsedTime = new JLabel(" elapsed time:");
    private final JTextField txtElapsedTime = new JTextField("0:00:00", 5);
    private final JButton btStartRace = new JButton(" Start ");
    private final JButton btEndRace = new JButton(" End ");
    private final JButton btSaveResults = new JButton(" Save ");

    // Timers
    private javax.swing.Timer timerForTime = new javax.swing.Timer(1000, new ClockListenerHeure());
    private javax.swing.Timer timerForChrono = new javax.swing.Timer(1000, new ClockListenerChrono());

    public PanelChrono(PanelRace pPanelCourse) {
        panelRace = pPanelCourse;

        setMaximumSize(new Dimension(32767, 52));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(Box.createHorizontalStrut(5));
        add(txtClock);
        txtClock.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtClock.setBackground(new Color(135, 206, 235));
        txtClock.setEditable(false);
        txtClock.setFont(new Font("sansserif", Font.PLAIN, 24));

        add(lbElapsedTime);
        lbElapsedTime.setFont(new Font("sansserif", Font.PLAIN, 24));

        add(txtElapsedTime);
        txtElapsedTime.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        txtElapsedTime.setBackground(new Color(144, 238, 144));
        txtElapsedTime.setEditable(false);
        txtElapsedTime.setFont(new Font("sansserif", Font.PLAIN, 24));

        add(Box.createHorizontalStrut(20));
        add(btStartRace);
        add(Box.createHorizontalStrut(20));
        add(btEndRace);
        btEndRace.setEnabled(go);
        btEndRace.setToolTipText("All not arrived runners will be abandoned");

        add(Box.createHorizontalStrut(20));
        add(btSaveResults);
        add(Box.createHorizontalStrut(5));

        // Reset beginning time
        txtClock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                boolean ok = false;
                while (!ok) {
                    try {
                        String newDebut = (String) JOptionPane.showInputDialog(null,
                                "Change clock from " + txtClock.getText() + " to", "New starting time",
                                JOptionPane.QUESTION_MESSAGE, null, null, txtClock.getText());
                        ModifyChrono(newDebut);
                        ok = true;
                    } catch (NullPointerException | DateTimeParseException e) {
                    }
                }
            }
        });

        // Button start of race
        btStartRace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                go = true;
                startChrono();
                stopclock();
                btStartRace.setEnabled(!go);
                btEndRace.setEnabled(go);
            }
        });

        // Button end of race
        btEndRace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                        "Are you sur to end the race?\nThe rest of runners give up (DNF)", "End of race",
                        JOptionPane.YES_NO_OPTION)) {
                    go = false;
                    stopChrono();
                    btEndRace.setEnabled(go);
                    for (Runner c : getRaceTablerStart().getModel().data) {
                        c.setRank(getRace().getRang());
                        c.setTime(Runner.DNF);
                        getRaceTableFinish().getModel().addRow(c);
                    }
                    getRaceTablerStart().getModel().data.clear();
                    getRaceTablerStart().getModel().fireTableDataChanged();
                    getRaceTablerStart().updateUI();
                }

            }
        });

        // Button save results
        btSaveResults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PrintWriter writer;
                try {
                    writer = new PrintWriter(getRace().raceName + ".csv", "UTF-8");
                    for (Runner c : getRaceTableFinish().getModel().data) {
                        c.saveResultat(writer);
                    }
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        timerForTime.start();
    }

    /*** Event timerForClock ****************************************************/
    class ClockListenerHeure implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            txtClock.setText(df.format(Calendar.getInstance().getTime()));
        }
    }

    public void restartHeure() {
        timerForTime.start();
    }

    public void stopclock() {
        timerForTime.stop();
    }

    /*** Event timerForChrono ***************************************************/
    class ClockListenerChrono implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            updateChrono();
        }
    }

    public void startChrono() {
        startTime = LocalTime.now();
        timerForChrono.start();
    }

    public void ModifyChrono(String newDebut) {
        startTime = LocalTime.parse(newDebut); // "10:15:30"
        txtClock.setText(newDebut);
    }

    public void stopChrono() {
        timerForChrono.stop();
    }

    private void updateChrono() {
        String tps = getChronoTime();
        txtElapsedTime.setText(tps);
    }

    /** return timeChrono - beginingTime */
    public String getChronoTime() {
        LocalTime now = LocalTime.now();
        Duration duration = Duration.between(startTime, now);
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        return String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
    }

    public boolean isGo() {
        return go;
    }

    private Race getRace() {
        return panelRace.getRace();
    }

    private TableStart getRaceTablerStart() {
        return panelRace.getTablerStart();
    }

    private TableFinish getRaceTableFinish() {
        return panelRace.getTableFinish();
    }

}
