package hmi;

import java.util.ArrayList;

import race.Runner;
import race.Sex;

/**
 * see ModelTableArrivee
 */
@SuppressWarnings("serial")
public class TableStartModel extends TableParticipantsModel {
    public static final int DOSSARD_COLUM = 0;
    public static final int SEXE_COLUM = 1;
    public static final int NOM_COLUM = 2;
    public static final int PRENOM_COLUM = 3;
    public static final int CLUB_COLUM = 4;
    public static final int AGE_COLUM = 5;

    /** */
    private final String[] columnTitles = { "Dossard", "Sexe", "Nom", "Prenom", "Club", "Age" };

    /** */
    public TableStartModel() {
        this.data = new ArrayList<Runner>();
    }

    public void updateData(ArrayList<Runner> dataEntries) {
        this.data = dataEntries;
        this.fireTableDataChanged();
    }

    public void addRow(Runner c) {
        data.add(0, c);
        fireTableRowsInserted(0, 0);
    }

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

    /** */
    public int getRowCount() {
        return data.size();
    }

    /** */
    public int getColumnCount() {
        return columnTitles.length;
    }

    /** */
    public Runner getValueAt(int row) {
        return data.get(row);
    }

    /** */
    public Object getValueAt(int row, int column) {

        switch (column) {
        case DOSSARD_COLUM:
            return data.get(row).getBibNb();
        case SEXE_COLUM:
            return data.get(row).getSex();
        case NOM_COLUM:
            return data.get(row).getName();
        case PRENOM_COLUM:
            return data.get(row).getFirstName();
        case AGE_COLUM:
            return data.get(row).getAge();
        case CLUB_COLUM:
            return data.get(row).getClub();
        default:
            return null;
        }
    }

    /** */
    public String getColumnName(int column) {
        return columnTitles[column];
    }

    /** */
    public Class<?> getColumnClass(int column) {
        switch (column) {
        case DOSSARD_COLUM:
        case AGE_COLUM:
            return Integer.class;
        case SEXE_COLUM:
        case NOM_COLUM:
        case PRENOM_COLUM:
        case CLUB_COLUM:
            return String.class;
        default:
            return null;
        }
    }

    /** */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /** */
    public void setValueAt(Object value, int row, int column) {
        switch (column) {
        case DOSSARD_COLUM:
            data.get(row).setBibNb((Integer) value);
            break;
        case SEXE_COLUM:
            data.get(row).setSex((Sex) value);
            break;
        case NOM_COLUM:
            data.get(row).setName((String) value);
            break;
        case PRENOM_COLUM:
            data.get(row).setFirstName((String) value);
            break;
        case AGE_COLUM:
            data.get(row).setAge((Integer) value);
            break;
        case CLUB_COLUM:
            data.get(row).setClub((String) value);
            break;
        default:
            break;
        }
    }

}
