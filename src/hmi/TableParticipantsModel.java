package hmi;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import race.Runner;
import race.Sex;

/**
 * see ModelTableArrivee
 */
@SuppressWarnings("serial")
public class TableParticipantsModel extends AbstractTableModel {
    public static final int DOSSARD_COLUM = 0;
    public static final int SEXE_COLUM = 1;
    public static final int NOM_COLUM = 2;
    public static final int PRENOM_COLUM = 3;
    public static final int COURSE_COLUM = 4;
    public static final int AGE_COLUM = 5;
    public static final int CERTIF_COLUM = 6;
    public static final int CLUB_COLUM = 7;
    public static final int LICENCE_COLUM = 8;
    public static final int VILLE_COLUM = 9;
    public static final int ADRESSE_COLUM = 10;
    public static final int NAISSANCE_COLUM = 11;
    public static final int PAYE_COLUM = 12;

    /** */
    private final String[] columnTitles = { "Dossard", "Sexe", "Nom", "Prenom", "Type de course", "Age", "Certif",
            "Club", "Licence", "Ville", "Adresse", "Naissance", "Payé" };
    /** */
    protected ArrayList<Runner> data;

    /** */
    public TableParticipantsModel() {
        // this.data = new ArrayList<Coureur>();
    }

    public ArrayList<Runner> getData() {
        return data;
    }

    public void updateData(ArrayList<Runner> dataEntries) {
        this.data = dataEntries;
        this.fireTableDataChanged();
    }

    public void addRow(Runner c) {
        data.add(0, c);
        fireTableRowsInserted(0, 0);
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
        case COURSE_COLUM:
            return data.get(row).getRace();
        case AGE_COLUM:
            return data.get(row).getAge();
        case CERTIF_COLUM:
            return data.get(row).isCertifOk();
        case CLUB_COLUM:
            return data.get(row).getClub();
        case LICENCE_COLUM:
            return data.get(row).getLicence();
        case VILLE_COLUM:
            return data.get(row).getCity();
        case ADRESSE_COLUM:
            return data.get(row).getAddrress();
        case NAISSANCE_COLUM:
            return data.get(row).getBirthday();
        case PAYE_COLUM:
            return data.get(row).isPaid();
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
        return getValueAt(0, column).getClass();
    }

    /** */
    public boolean isCellEditable(int row, int column) {
        return true;
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
        case COURSE_COLUM:
            data.get(row).setRaceName((String) value);
            break;
        case AGE_COLUM:
            data.get(row).setAge((Integer) value);
            break;
        case CERTIF_COLUM:
            data.get(row).setCertifOk((Boolean) value);
            break;
        case PAYE_COLUM:
            data.get(row).setPaid((Boolean) value);
            break;
        case CLUB_COLUM:
            data.get(row).setClub((String) value);
            break;
        case LICENCE_COLUM:
            data.get(row).setLicence((String) value);
            break;
        case VILLE_COLUM:
            data.get(row).setCity((String) value);
            break;
        case ADRESSE_COLUM:
            data.get(row).setAddress((String) value);
            break;
        case NAISSANCE_COLUM:
            data.get(row).setBirthday((String) value);
            break;

        default:
            break;
        }
    }
}
