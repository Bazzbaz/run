package hmi;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import race.Runner;
import race.Sex;

@SuppressWarnings("serial")
public class TableFinish extends JPanel {

    /** */
    private TableFinishModel model;
    private JTable table;
    private PanelRace vue;

    /** */
    public TableFinish(PanelRace pView) {
        vue = pView;

        model = new TableFinishModel();
        model.updateData(vue.getRace().results);
        table = new JTable(model);
        table.createDefaultColumnsFromModel();
        table.getColumnModel().getColumn(TableFinishModel.RANK_COLUM).setMaxWidth(30);
        table.getColumnModel().getColumn(TableFinishModel.TIME_COLUM).setPreferredWidth(60);
        table.getColumnModel().getColumn(TableFinishModel.BIB_NB_COLUM).setMaxWidth(30);
        table.getColumnModel().getColumn(TableFinishModel.SEX_COLUM)
                .setCellEditor(new DefaultCellEditor(new JComboBox(Sex.values())));
        table.getColumnModel().getColumn(TableFinishModel.SEX_COLUM).setMaxWidth(30);
        table.getColumnModel().getColumn(TableFinishModel.NAME_COLUM).setPreferredWidth(80);
        table.getColumnModel().getColumn(TableFinishModel.FNAME_COLUM).setPreferredWidth(80);

        // Cell render
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            columns.nextElement().setCellRenderer(new ServiceCellRenderer());
        }

        // Table sorter
        TableRowSorter<TableFinishModel> sorter = new TableRowSorter<TableFinishModel>(model);
        table.setRowSorter(sorter);

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem modifyRang = new JMenuItem("Erreur: modifier rang");
        JMenuItem modifyTemps = new JMenuItem("Erreur: modifier temps");
        JMenuItem notArrive = new JMenuItem("Erreur: pas arrivé");
        JMenuItem replaceDossard = new JMenuItem("Erreur: remplacer par un autre dossard");
        modifyRang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(vue,
                // "Right-click performed on table and choose modifyRang");
                Runner c = getSelectedCoureur();
                if (c != null) {
                    c.setRank(0);
                }
                updateUI();
            }
        });
        modifyTemps.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(vue,
                // "Right-click performed on table and choose modifyTemps");
                Runner selected = getSelectedCoureur();
                if (selected == null)
                    return;
                if (selected != null) {
                    String stringValue = (String) JOptionPane.showInputDialog(null, "", "abandon sinon",
                            JOptionPane.QUESTION_MESSAGE, null, null, selected.getTime());
                    selected.setTime(stringValue);
                    vue.getRace().ModifyRand();
                }
                getModel().fireTableDataChanged();
                updateUI();
            }
        });
        notArrive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runner selected = getSelectedCoureur();
                if (selected == null)
                    return;

                // remise a zero
                selected.setRank(0);
                selected.setTime("0:00:00");
                // remet dans liste de depart
                vue.getTablerStart().getModel().addRow(selected);
                vue.getTablerStart().updateUI();
                vue.getTableFinish().getModel().remove(selected);
                vue.getTableFinish().updateUI();
                // re calcul les arrivées
                vue.getRace().ModifyRand();
                getModel().fireTableDataChanged();
                updateUI();
            }
        });
        replaceDossard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runner selected = getSelectedCoureur();
                if (selected == null)
                    return;
                int size = vue.getTablerStart().getModel().data.size();
                Integer list[] = new Integer[size];
                if (vue.getTablerStart().getModel().data.size() > 0) {
                    int i = 0;
                    for (Runner c : vue.getTablerStart().getModel().data) {
                        list[i] = c.getBibNb();
                        i++;
                    }

                } else {
                    return;
                }
                int toReplace = (Integer) JOptionPane.showInputDialog(null, "", "", JOptionPane.PLAIN_MESSAGE, null,
                        list, 0);
                for (Runner replaced : vue.getRace().participants) {
                    if (replaced.getBibNb() == toReplace) {
                        replaced.setRank(selected.getRank());
                        replaced.setTime(selected.getTime());
                        selected.setRank(0);
                        selected.setTime("0:00:00");

                        vue.getTablerStart().getModel().addRow(selected);
                        vue.getTablerStart().updateUI();
                        vue.getTablerStart().getModel().remove(replaced);
                        vue.getTablerStart().updateUI();
                        vue.getTableFinish().getModel().addRow(replaced);
                        vue.getTableFinish().updateUI();
                        vue.getTableFinish().getModel().remove(selected);
                        vue.getTableFinish().updateUI();

                        vue.getRace().ModifyRand();
                        getModel().fireTableDataChanged();
                        updateUI();
                        break;
                    }
                }
            }
        });
        // popupMenu.add(modifyRang);
        popupMenu.add(modifyTemps);
        popupMenu.add(notArrive);
        popupMenu.add(replaceDossard);
        table.setComponentPopupMenu(popupMenu);

        // table.addMouseListener(new PopupListener());
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JScrollPane(table));
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

            int modelRow = table.getRowSorter().convertRowIndexToModel(row);
            Runner selectedMessage = model.getValueAt(modelRow);
            if (selectedMessage.getRank() > 3) {
                setBackground(Color.WHITE);
            } else if (selectedMessage.getRank() == 3) {
                setBackground(new Color(255, 255, 200));
            } else if (selectedMessage.getRank() == 2) {
                setBackground(new Color(255, 255, 120));
            } else if (selectedMessage.getRank() == 1) {
                setBackground(new Color(255, 255, 0));
            }
            return this;
        }
    }

    private Runner getSelectedCoureur() {
        int row = table.getSelectedRow();
        if (row != -1) {
            row = table.getRowSorter().convertRowIndexToModel(row);
            Runner selectedCoureur = model.getValueAt(row);
            // System.out.println(selectedCoureur);
            return selectedCoureur;
        }
        return null;
    }

    public TableFinishModel getModel() {
        return model;
    }

}
