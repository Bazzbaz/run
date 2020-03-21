package hmi;

import java.util.ArrayList;

import javax.swing.AbstractSpinnerModel;

import race.Runner;

@SuppressWarnings("serial")
public class SpinnerModel extends AbstractSpinnerModel {

    Runner fake;
    int currentValue = 0;
    /** */
    protected ArrayList<Runner> data;
    protected TableStart listeDepart;

    /** */
    public SpinnerModel(ArrayList<Runner> dataEntries, TableStart pTableDepart) {
        currentValue = 0;
        this.fake = new Runner();

        this.data = dataEntries;
        listeDepart = pTableDepart;
    }

    @Override
    public Object getNextValue() {
        if ((++currentValue) >= data.size()) {
            currentValue = data.size() - 1;
        }
        if (data.size() > 0) {
            listeDepart.selectRow(data.get(currentValue));
            return data.get(currentValue);
        }
        return fake;
    }

    @Override
    public Object getPreviousValue() {
        if ((--currentValue) < 0) {
            currentValue = 0;
        }
        if (data.size() > 0) {
            listeDepart.selectRow(data.get(currentValue));
            return data.get(currentValue);
        }
        return fake;
    }

    @Override
    public Object getValue() {
        if ((currentValue >= 0) && (currentValue < data.size())) {
            return data.get(currentValue);
        }
        return fake;
    }

    @Override
    public void setValue(Object arg0) {
        currentValue = -1;
        if (arg0 instanceof String) {
            int dossard = Integer.parseInt((String) arg0);
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getBibNb() == dossard) {
                    currentValue = i;
                    listeDepart.selectRow(data.get(currentValue));
                    break;
                }
            }

            // user input not in depart table
            if (-1 == currentValue) {
                if (data.size() > 0) {
                    // set first runner
                    setValue(data.get(0));
                } // else fake runner is set
            }

        } else if (arg0 instanceof Runner) {
            Runner argC = (Runner) arg0;
            int dossard = argC.getBibNb();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getBibNb() == dossard) {
                    currentValue = i;
                    listeDepart.selectRow(data.get(currentValue));
                    break;
                }
            }
        }
        fireStateChanged();
    }

}
