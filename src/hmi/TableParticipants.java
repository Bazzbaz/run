package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import race.Race;
import race.Runner;
import race.Sex;

@SuppressWarnings("serial")
public class TableParticipants extends JPanel {

    /** */
    private TableParticipantsModel model;
    private JTable table;
    private HmiMain vue;

    /** */
    public TableParticipants(ArrayList<Runner> participants, HmiMain pView) {
        vue = pView;

        model = new TableParticipantsModel();
        model.updateData(participants);
        table = new JTable(model);
        table.createDefaultColumnsFromModel();
        table.getColumnModel().getColumn(TableParticipantsModel.DOSSARD_COLUM).setMaxWidth(50);
        table.getColumnModel().getColumn(TableParticipantsModel.SEXE_COLUM)
                .setCellEditor(new DefaultCellEditor(new JComboBox(Sex.values())));
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

        table.getColumnModel().getColumn(TableParticipantsModel.COURSE_COLUM)
                .setCellRenderer(new ServiceCellRenderer());

        // Table sorter
        TableRowSorter<TableParticipantsModel> sorter = new TableRowSorter<TableParticipantsModel>(model);
        // Unset the sorter for column
        sorter.setSortable(TableParticipantsModel.DOSSARD_COLUM, true);
        table.setRowSorter(sorter);

        table.addMouseListener(new PopupListener());
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JScrollPane(table));

//        table.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_F5) {
//                    int dialogResult = JOptionPane.showConfirmDialog(null, "reload file?", "Fermer l'appli course", JOptionPane.YES_NO_OPTION);
//                    if (dialogResult == JOptionPane.YES_OPTION) {
//                        vue.evt.participants.clear();
//                        vue.evt.read("ferrieroise");
//                        System.out.println("F5");
//                    }
//                }
//            }
//        });
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
            Runner c = model.getValueAt(modelRow);

            for (Race course : vue.evt.races) {
                if (c.getRace().equalsIgnoreCase(course.raceName)) {
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
                    Runner selectedMessage = model.getValueAt(row);
                }
            }
        }
    }

}
