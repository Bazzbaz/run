package hmi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import race.Race;

@SuppressWarnings("serial")
public class PanelRace extends JPanel {
    private Race race;
    private TableStart tableStart;
    private TableFinish tableFinish;
    private PanelChrono chrono;
    private PanelConfirmFinish panelConfirmFinish;

    public PanelRace(Race pCourse) {
        race = pCourse;

        JPanel panelDepart = new JPanel();
        panelDepart.setLayout(new BoxLayout(panelDepart, BoxLayout.Y_AXIS));

        JPanel panelListeDepart = new JPanel();
        panelDepart.add(panelListeDepart);
        panelListeDepart.setLayout(new BorderLayout(0, 0));
        this.tableStart = new TableStart(race, this);
        panelListeDepart.add(getTablerStart());
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(panelDepart);

        panelConfirmFinish = new PanelConfirmFinish(this);
        panelDepart.add(panelConfirmFinish);

        // vertical struct
        add(Box.createVerticalStrut(20));

        JPanel panelArrivee = new JPanel();
        JPanel panelTimer = new JPanel();
        JPanel panelListeArrivee = new JPanel();
        panelTimer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        this.chrono = new PanelChrono(this);
        this.tableFinish = new TableFinish(this);

        panelTimer.add(getChrono());
        panelListeArrivee.setLayout(new BorderLayout(0, 0));
        panelListeArrivee.add(getTableFinish());
        panelArrivee.setLayout(new BoxLayout(panelArrivee, BoxLayout.Y_AXIS));
        panelArrivee.add(panelTimer);
        panelArrivee.add(panelListeArrivee);
        add(panelArrivee);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    public Race getRace() {
        return race;
    }

    public TableStart getTablerStart() {
        return tableStart;
    }

    public TableFinish getTableFinish() {
        return tableFinish;
    }

    public PanelChrono getChrono() {
        return chrono;
    }

    public JSpinner getDossard() {
        return panelConfirmFinish.getSpinnerBibNb();
    }

}