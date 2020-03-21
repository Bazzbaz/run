package hmi;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import race.Runner;
import race.Sex;

/**
 * Comment afficher les cellules? Quel est la valeur de chaque cellule? Quel
 * type d'objet des cellules? Comment ajouter ou supprimer des ligne (des
 * coureurs)
 */
@SuppressWarnings("serial")
public class TableFinishModel extends AbstractTableModel {

    /** coureurs du tableau */
    protected ArrayList<Runner> data;

    /** */
    public static final int RANK_COLUM = 0;
    public static final int TIME_COLUM = 1;
    public static final int BIB_NB_COLUM = 2;
    public static final int SEX_COLUM = 3;
    public static final int NAME_COLUM = 4;
    public static final int FNAME_COLUM = 5;
    /** */
    private final String[] columnTitles = { "Rang", "Temps", "Dossard", "Sexe", "Nom", "Prenom", };

    /** */
    public TableFinishModel() {
        this.data = new ArrayList<Runner>();
    }

    /** mise a jour du baleau */
    public void updateData(ArrayList<Runner> dataEntries) {
        this.data = dataEntries;
        this.fireTableDataChanged();
    }

    /** Add Coureur into table */
    public void addRow(Runner c) {
        data.add(c);
        fireTableRowsInserted(0, 0);
    }

    /** Remove Coureur */
    public void remove(Runner c) {
        int i = 0;
        for (i = 0; i < data.size(); ++i) {
            if (data.get(i) == c) {
                break;
            }
        }
        data.remove(c);
        fireTableRowsDeleted(i, i);
    }

    /** How many lines */
    public int getRowCount() {
        return data.size();
    }

    /** How many column (pas de supprise) */
    public int getColumnCount() {
        return columnTitles.length;
    }

    /** Give Coueur of the line row */
    public Runner getValueAt(int row) {
        return data.get(row);
    }

    /**
     * Le plus important des methods, qu'est ce qu'on affcihe dans les cellules Give
     * sub object of class Coureur at position: row/column
     */
    public Object getValueAt(int row, int column) {

        switch (column) {
        case RANK_COLUM:
            return data.get(row).getRank();
        case TIME_COLUM:
            return data.get(row).getTime();
        case BIB_NB_COLUM:
            return data.get(row).getBibNb();
        case SEX_COLUM:
            return data.get(row).getSex();
        case NAME_COLUM:
            return data.get(row).getName();
        case FNAME_COLUM:
            return data.get(row).getFirstName();
        default:
            return null;
        }
    }

    /** print column name */
    public String getColumnName(int column) {
        return columnTitles[column];
    }

    /** what is the type of object for this column */
    public Class<?> getColumnClass(int column) {
        switch (column) {
        case BIB_NB_COLUM:
        case RANK_COLUM:
            return Integer.class;
        case SEX_COLUM:
            return Sex.class;
        case NAME_COLUM:
        case FNAME_COLUM:
        case TIME_COLUM:
            return String.class;
        default:
            return null;
        }
    }

    /** */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Modify the object a position: row/colum
     *
     * not used
     */
    public void setValueAt(Object value, int row, int column) {
        switch (column) {
        case RANK_COLUM:
            data.get(row).setTime((String) value);
            break;
        case BIB_NB_COLUM:
            data.get(row).setBibNb((Integer) value);
            break;
        case SEX_COLUM:
            data.get(row).setSex((Sex) value);
            break;
        case NAME_COLUM:
            data.get(row).setName((String) value);
            break;
        case FNAME_COLUM:
            data.get(row).setFirstName((String) value);
            break;
        case TIME_COLUM:
            data.get(row).setClub((String) value);
            break;
        default:
            break;
        }
    }
}
