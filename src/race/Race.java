package race;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Race {

    private int rank = 0;
    public String raceName;
    public ArrayList<Runner> participants = new ArrayList<Runner>();
    public ArrayList<Runner> results = new ArrayList<Runner>();

    public Color color;

    public Race(String typeDeCourse) {
        super();
        raceName = typeDeCourse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  \nCourse [nomDeLaCourse=" + raceName + "]\n");
        for (Runner c : participants) {
            sb.append(c);
        }
        return sb.toString();
    }

    public void save(PrintWriter writer) {
        for (Runner c : participants) {
            c.save(writer);
        }
    }

    public int getRang() {
        rank++;
        return rank;
    }

    public void ModifyRand() {
        Collections.sort(results);
        rank = 0;
        for (Runner c : results) {
            rank++;
            c.setRank(rank);
        }
    }
}
