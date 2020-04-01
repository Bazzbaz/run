package hmi;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import race.Race;

@SuppressWarnings("serial")
public class PanelRace extends JPanel {
    // Attributes
    private Race race;

    private TableStart tableStart;
    private TableFinish tableFinish;
    private final PanelChrono panelTimer;
    private final PanelConfirmFinish panelValidate;

    public PanelRace(Race pRace) {
        race = pRace;
        tableStart = new TableStart(race, this);
        tableFinish = new TableFinish(this);
        panelTimer = new PanelChrono(this);
        panelValidate = new PanelConfirmFinish(this);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panelStart = new JPanel();
        add(panelStart);

        panelStart.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panelStart.setLayout(new BoxLayout(panelStart, BoxLayout.Y_AXIS));
        JPanel panelStartList = new JPanel();
        panelStartList.setLayout(new BorderLayout(0, 0));
        panelStartList.add(tableStart);

        panelStart.add(panelStartList);
        panelStart.add(Box.createVerticalStrut(10));
        panelStart.add(panelValidate);

        add(Box.createHorizontalStrut(5));

        JPanel panelArrive = new JPanel();
        add(panelArrive);
        
        panelArrive.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        panelArrive.setLayout(new BoxLayout(panelArrive, BoxLayout.Y_AXIS));
        JPanel panelArriveList = new JPanel();
        panelArriveList.setLayout(new BorderLayout(0, 0));
        panelArriveList.add(tableFinish);

        panelArrive.add(panelTimer);
        panelArrive.add(Box.createVerticalStrut(10));
        panelArrive.add(panelArriveList);

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
        return panelTimer;
    }

    public JSpinner getDossard() {
        return panelValidate.getSpBibNb();
    }

}