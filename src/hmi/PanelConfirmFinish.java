package hmi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
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

    private PanelRace panelRace;
    private JSpinner spinnerBibNb;

    public JSpinner getSpinnerBibNb() {
        return spinnerBibNb;
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

    public PanelConfirmFinish(PanelRace pPanelCourse) {
        setMaximumSize(new Dimension(32767, 62));
        panelRace = pPanelCourse;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Panel panel_1 = new Panel();
        add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));

        this.spinnerBibNb = new JSpinner();
        spinnerBibNb.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent arg0) {
            }

            public void inputMethodTextChanged(InputMethodEvent arg0) {
                System.out.println("TODO spinnerBibNb inputMethodTextChanged ");
            }
        });
        panel_1.add(spinnerBibNb);
        getSpinnerBibNb().setModel(new SpinnerModel(getPanelRace().participants, getTableStart()));
        getSpinnerBibNb().setValue(getPanelRace().participants.get(0));
        getSpinnerBibNb().setMinimumSize(new Dimension(100, 100));
        getSpinnerBibNb().setFont(new Font("sansserif", Font.PLAIN, 32));
        // t_dossard.setMinimumSize(60);
        getSpinnerBibNb().setFont(new Font("sansserif", Font.PLAIN, 24));

        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinnerBibNb.getEditor()).getTextField();
        tf.setEditable(true);

        Component horizontalGlue = Box.createHorizontalGlue();
        add(horizontalGlue);

        Panel panel = new Panel();
        add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        // Bouton ARRIVEE
        JButton finish = new JButton("Finish");
        panel.add(finish);
//        arrivee.setIcon(new ImageIcon(HmiMain.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/capslock-icon.png")));
        finish.setIgnoreRepaint(true);
        finish.setMinimumSize(new Dimension(200, 200));
        finish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                for (Runner c : getPanelRace().participants) {
                    if (c.getBibNb() == ((Runner) getSpinnerBibNb().getValue()).getBibNb()) {
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
}
