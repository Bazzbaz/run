package hmi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import race.Race;
import race.Runner;

@SuppressWarnings("serial")
public class PanelConfirmFinish extends JPanel {
    // Main race panel
    private PanelRace panelRace;

    // swing components
    private final JSpinner spBibNb = new JSpinner();;
    private final JButton finish = new JButton("Finish");

    public PanelConfirmFinish(PanelRace pPanelCourse) {
        panelRace = pPanelCourse;

        setMaximumSize(new Dimension(32767, 42));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(Box.createHorizontalStrut(5));
        add(spBibNb);
        spBibNb.setModel(new SpinnerModel(getPanelRace().participants, getTableStart()));
        spBibNb.setFont(new Font("sansserif", Font.PLAIN, 24));
        spBibNb.setValue(getPanelRace().participants.get(0));

        add(Box.createHorizontalStrut(20));
        add(finish);
        finish.setBackground(new Color(144, 238, 144));

        add(Box.createHorizontalStrut(5));

        JFormattedTextField tf = ((JSpinner.DefaultEditor) spBibNb.getEditor()).getTextField();
        tf.setEditable(true);

        spBibNb.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent arg0) {
            }

            public void inputMethodTextChanged(InputMethodEvent arg0) {
                System.out.println("TODO spinnerBibNb inputMethodTextChanged ");
            }
        });

        finish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                for (Runner c : getPanelRace().participants) {
                    if (c.getBibNb() == ((Runner) spBibNb.getValue()).getBibNb()) {
                        c.setRank(getPanelRace().getRang());
                        c.setTime(getChrono().getChronoTime());
                        getTableFinish().getModel().addRow(c);
                        getTableStart().getModel().remove(c);
                        getTableStart().updateUI();
                        break;
                    }
                }
            }
        });

    }
    

    public Race getPanelRace() {
        return panelRace.getRace();
    }

    public TableStart getTableStart() {
        return panelRace.getTablerStart();
    }

    public TableFinish getTableFinish() {
        return panelRace.getTableFinish();
    }

    public PanelChrono getChrono() {
        return panelRace.getChrono();
    }

    public JSpinner getSpBibNb() {
        return spBibNb;
    }
}
