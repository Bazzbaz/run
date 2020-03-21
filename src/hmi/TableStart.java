package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import race.Race;
import race.Runner;
import race.Sex;

@SuppressWarnings("serial")
public class TableStart extends JPanel {

    /** */
    private TableStartModel model;
    private JTable table;
    private PanelRace vue;

    /** */
    public TableStart(Race course, PanelRace view) {
        vue = view;

        model = new TableStartModel();
        model.updateData(course.participants);
        table = new JTable(model);
        table.createDefaultColumnsFromModel();
        table.getColumnModel().getColumn(TableStartModel.DOSSARD_COLUM).setMaxWidth(50);
        table.getColumnModel().getColumn(TableStartModel.SEXE_COLUM)
                .setCellEditor(new DefaultCellEditor(new JComboBox(Sex.values())));
        table.getColumnModel().getColumn(TableStartModel.SEXE_COLUM).setMaxWidth(30);
        table.getColumnModel().getColumn(TableStartModel.NOM_COLUM).setPreferredWidth(60);
        table.getColumnModel().getColumn(TableStartModel.PRENOM_COLUM).setPreferredWidth(60);
        table.getColumnModel().getColumn(TableStartModel.AGE_COLUM).setMaxWidth(30);
//        table.getColumnModel().getColumn(ModelTableDepart.CLUB_COLUM).setPreferredWidth(20);

        // Cell render
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            columns.nextElement().setCellRenderer(new ServiceCellRenderer());
        }

        // Table sorter
        TableRowSorter<TableStartModel> sorter = new TableRowSorter<TableStartModel>(model);
//        sorter.setSortable(ModelTableArrivee.TEMPS_COLUM, true);
        table.setRowSorter(sorter);

        table.addMouseListener(new PopupListener());
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

//        table.setFillsViewportHeight(true);
//        // Enables row selection mode and disable column selection mode.
//        table.setRowSelectionAllowed(true);
//        table.setColumnSelectionAllowed(false);
        table.setSelectionModel(new ForcedListSelectionModel());
        table.setRowSelectionInterval(0, 0);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int slectedRow = table.getSelectedRow();
                if ((slectedRow >= 0) && (slectedRow < model.getRowCount())) {
                    int modelRow = table.getRowSorter().convertRowIndexToModel(slectedRow);
                    Runner c = model.getValueAt(modelRow);
                    vue.getDossard().setValue(c);
//                    vue.t_dossard.repaint();
                }
                // else select what?
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int modelRow = table.getRowSorter().convertRowIndexToModel(row);
                    Runner c = model.getValueAt(modelRow);
                    // your valueChanged overridden method
                    c.setRank(vue.getRace().getRang());
                    c.setTime(vue.getChrono().getChronoTime());
                    vue.getTableFinish().getModel().addRow(c);
                    vue.getTablerStart().getModel().remove(c);
                    vue.getTablerStart().updateUI();
                }
            }
        });
    }

    public void selectRow(Runner c) {
        for (int row = 0; row < model.getRowCount(); row++) {
            Runner cRow = model.getValueAt(table.getRowSorter().convertRowIndexToModel(row));
            if (cRow == c) {
//                setBackground(Color.CYAN); // color the row
                table.setRowSelectionInterval(row, row);
            }
        }

    }

    // /////////////////////////////////////////////////////////////////////////
    public synchronized void addCoureur(Runner coureur) {
        model.addRow(coureur);
    }

    public class ServiceCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setForeground(Color.BLACK);
            Runner c = model.getValueAt(table.getRowSorter().convertRowIndexToModel(row));
            int slectedRow = table.getSelectedRow();
            // last row has been remove
            if (model.data.size() == slectedRow && model.data.size() > 0) {
                slectedRow--;
            }
            //
            if (slectedRow == row) { //
                setBackground(Color.CYAN); // color the row
                vue.getDossard().setValue(c); // set spinner to selected row
            } else {
                setBackground(Color.WHITE);
            }

            return this;
        }
    }

    /** */
    private class PopupListener extends MouseAdapter {
        /** Mouse event Right or Middle button */
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
//                JPopupMenu popup;
                int row = table.getSelectedRow();
//                int column = table.getSelectedColumn();
                if (row != -1) {
                    row = table.getRowSorter().convertRowIndexToModel(row);
                    Runner c = model.getValueAt(row);
                }
            }
        }
    }

    public JTable getTable() {
        return table;
    }

    public TableStartModel getModel() {
        return model;
    }

    public class ForcedListSelectionModel extends DefaultListSelectionModel {

        public ForcedListSelectionModel() {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void removeSelectionInterval(int index0, int index1) {
        }

    }

}
