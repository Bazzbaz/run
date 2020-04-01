package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import race.Race;
import race.Runner;
import race.Sex;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class TableParticipants extends JPanel {

    /** */
    private TableParticipantsModel model;
    private JTable table;
    private Runner newRun;

    public TableParticipantsModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    // runner info
    private JSpinner bibNb = new JSpinner();
    private JComboBox sex = new JComboBox(Sex.values());
    private JTextField name = new JTextField("name");;
    private JTextField firstName = new JTextField("first-name");
    private JTextField birthday = new JTextField("01/10/1977");
    private JCheckBox hasPaid = new JCheckBox("paid");
    // contacts
    private JTextField address = new JTextField("");
    private JTextField city = new JTextField("");
    private JTextField email = new JTextField("");
    // club
    private JTextField licence = new JTextField("");
    private JTextField club = new JTextField("");
    private JCheckBox certifOk = new JCheckBox("doctor");
    // apply race
    private JTextField raceName = new JTextField("10 Km");
    private JButton apply = new JButton("Add / Modify runner");
    private JButton delete = new JButton("delete runner");

    private HmiMain vue;
    private JPanel panelToolBar;
    private JPanel panelInformation = new JPanel();
    private JPanel panelName = new JPanel();
    private JPanel panelAddress = new JPanel();
    private JPanel panelClub = new JPanel();
    private JPanel panelApply = new JPanel();
    private JPanel panelRaceName = new JPanel();
    private JPanel panelApplyButton = new JPanel();
    private final JButton btNew = new JButton("New runer");
    private final JLabel lblNewLabel = new JLabel("");
    private final JLabel lblNewLabel_1 = new JLabel("");
    private final JLabel lblNewLabel_2 = new JLabel("");
    private final JLabel lblNewLabel_3 = new JLabel("cty");

    
    int getFirstBibAvailable() {
        return getListBibAvailable().get(0);
    }

    ArrayList<Integer> getListBibAvailable() {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 1; i < 1001; ++i) {
            ret.add(i);
        }
        for (Runner r : vue.getParticipants()) {
            ret.remove((Integer)r.getBibNb());
        }
        return ret;
    }

    /** */
    public TableParticipants(HmiMain pView) {
        vue = pView;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        model = new TableParticipantsModel();
        model.updateData(vue.getParticipants());
        if (true) // for WindowsBuilder
            table = new JTable(model);
        else {
            table = new JTable();
        }
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int modelRow = table.getRowSorter().convertRowIndexToModel(row);
                    updateToolBarFields(model.getValueAt(modelRow));
                }
            }
        });
        
        table.createDefaultColumnsFromModel();
        table.getColumnModel().getColumn(TableParticipantsModel.DOSSARD_COLUM).setMaxWidth(50);
        table.getColumnModel().getColumn(TableParticipantsModel.SEXE_COLUM).setCellEditor(new DefaultCellEditor(new JComboBox<Object>(Sex.values())));
        table.getColumnModel().getColumn(TableParticipantsModel.SEXE_COLUM).setMaxWidth(40);
        table.getColumnModel().getColumn(TableParticipantsModel.NOM_COLUM).setPreferredWidth(80);
        table.getColumnModel().getColumn(TableParticipantsModel.PRENOM_COLUM).setPreferredWidth(80);
        table.getColumnModel().getColumn(TableParticipantsModel.COURSE_COLUM).setPreferredWidth(80);
        table.getColumnModel().getColumn(TableParticipantsModel.AGE_COLUM).setMaxWidth(40);
        table.getColumnModel().getColumn(TableParticipantsModel.CERTIF_COLUM).setPreferredWidth(30);
        table.getColumnModel().getColumn(TableParticipantsModel.CLUB_COLUM).setPreferredWidth(20);
        table.getColumnModel().getColumn(TableParticipantsModel.LICENCE_COLUM).setPreferredWidth(60);
        table.getColumnModel().getColumn(TableParticipantsModel.VILLE_COLUM).setPreferredWidth(100);
        table.getColumnModel().getColumn(TableParticipantsModel.ADRESSE_COLUM).setPreferredWidth(100);
        table.getColumnModel().getColumn(TableParticipantsModel.NAISSANCE_COLUM).setPreferredWidth(80);
        table.getColumnModel().getColumn(TableParticipantsModel.PAYE_COLUM).setPreferredWidth(80);
        table.getColumnModel().getColumn(TableParticipantsModel.COURSE_COLUM).setCellRenderer(new ServiceCellRenderer());

        // Table sorter
        TableRowSorter<TableParticipantsModel> sorter = new TableRowSorter<TableParticipantsModel>(model);
        // Unset the sorter for column
        sorter.setSortable(TableParticipantsModel.DOSSARD_COLUM, true);
        table.setRowSorter(sorter);
        table.addMouseListener(new PopupListener());
        add(new JScrollPane(table));

        JPanel paneltoolBar = new JPanel();
        add(paneltoolBar);
        paneltoolBar.setLayout(new BoxLayout(paneltoolBar, BoxLayout.X_AXIS));

        JToolBar toolBar = new JToolBar();
        paneltoolBar.add(toolBar);

        panelToolBar = new JPanel();
        toolBar.add(panelToolBar);
        panelToolBar.setLayout(new BoxLayout(panelToolBar, BoxLayout.X_AXIS));

        panelInformation = new JPanel();
        panelToolBar.add(panelInformation);
        panelInformation.setLayout(new BoxLayout(panelInformation, BoxLayout.Y_AXIS));

        panelInformation.add(panelName);

        name.setColumns(20);
        firstName.setColumns(20);
        birthday.setColumns(8);

        panelName.add(btNew);
        bibNb.setModel(new SpinnerNumberModel(0, null, 10000, 1));
        panelName.add(bibNb);
        panelName.add(sex);
        panelName.add(name);
        panelName.add(firstName);
        panelName.add(birthday);
        panelName.add(hasPaid);

        panelInformation.add(panelAddress);
        address.setColumns(20);
        panelAddress.add(address);
        
        panelAddress.add(lblNewLabel_3);
        city.setColumns(15);
        panelAddress.add(city);
        
        panelAddress.add(lblNewLabel_2);
        email.setColumns(20);
        panelAddress.add(email);

        panelInformation.add(panelClub);
        
        panelClub.add(lblNewLabel);
        club.setColumns(10);
        panelClub.add(club);
        
        panelClub.add(lblNewLabel_1);
        licence.setColumns(16);
        panelClub.add(licence);
        panelClub.add(certifOk);

        panelApply.setLayout(new BoxLayout(panelApply, BoxLayout.Y_AXIS));
        raceName.setColumns(10);
        panelToolBar.add(panelApply);
        panelApply.add(panelRaceName);
        panelRaceName.add(raceName);
        panelApply.add(panelApplyButton);

        panelApplyButton.setLayout(new BoxLayout(panelApplyButton, BoxLayout.Y_AXIS));
        panelApplyButton.add(apply);
        panelApplyButton.add(delete);
        
        btNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newRun = new Runner(getFirstBibAvailable());
                updateToolBarFields(newRun);
            }
        });
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        apply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runner r = buildRunnerFromFieldsToolBar();
                vue.getParticipants().add(r);
                Race course = vue.getEvt().findRace(r.raceName);
                course.participants.add( r);

                getModel().updateData(vue.getParticipants());
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F5) {
//                    int dialogResult = JOptionPane.showConfirmDialog(null, "reload file?", "Fermer l'appli course", JOptionPane.YES_NO_OPTION);
//                    if (dialogResult == JOptionPane.YES_OPTION) {
//                        vue.evt.vue.getParticipants().clear();
//                        vue.evt.read("ferrieroise");
                        System.out.println("F5");
//                    }
                }
            }
        });
    }

    // /////////////////////////////////////////////////////////////////////////
    public synchronized void addCoureur(Runner coureur) {
        model.addRow(coureur);
    }

    /** */
    public class ServiceCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setForeground(Color.BLACK);
            int modelRow = table.getRowSorter().convertRowIndexToModel(row);
            Runner r = model.getValueAt(modelRow);

            for (Race course : vue.getEvt().races) {
                if (r.getRace().equalsIgnoreCase(course.raceName)) {
                    setBackground(course.color);
                    break;
                }
            }
            return this;
        }
    }

    /**
     * Popup Listener for message action
     */
    public class PopupListener extends MouseAdapter {
        /** Mouse event Right or Middle button */
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
//                JPopupMenu popup;
                int row = table.getSelectedRow();
//                int column = table.getSelectedColumn();
                if (row != -1) {
                    row = table.getRowSorter().convertRowIndexToModel(row);
                    Runner r = model.getValueAt(row);

                    updateToolBarFields(r);
                }
            }
        }
    }

        private void updateToolBarFields(Runner r) {
            bibNb.setValue(r.getBibNb());
            sex.setSelectedItem(r.getSex());
            name.setText(r.getName());
            firstName.setText(r.getFirstName());
            birthday.setText(r.getBirthday());
            hasPaid.setSelected(r.isPaid());
            // contacts
            address.setText(r.getAddrress());
            city.setText(r.getCity());
            email.setText(r.getMail());
            // club
            licence.setText(r.getLicence());
            club.setText(r.getClub());
            certifOk.setSelected(r.isCertifOk());
            // apply race
            raceName.setText(r.getRace());
        }

        private Runner buildRunnerFromFieldsToolBar() {
            Runner r = new Runner();
            
            r.setBibNb((Integer)bibNb.getValue());
            r.setSex((Sex)sex.getSelectedItem());
            r.setName(name.getText());
            r.setFirstName(firstName.getText());
            r.setBirthday(birthday.getText());
            r.setPaid(hasPaid.isSelected());
            // contacts
            r.setAddress(address.getText());
            r.setCity(city.getText());
            r.setMail(email.getText());
            // club
            r.setLicence(licence.getText());
            r.setClub(club.getText());
            r.setCertifOk(certifOk.isSelected());
            // apply race
            r.setRaceName(raceName.getText());
            
            return r;
        }
}
