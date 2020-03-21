package race;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EventRaces {
    String date = "";
    String comment = "";
    public ArrayList<Runner> participants;
    public ArrayList<Race> races = new ArrayList<Race>();

    public EventRaces(ArrayList<Runner> p, String date, String commentaire) {
        super();
        this.date = date;
        this.comment = commentaire;
        this.participants = p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Evenement [Date=" + date + ", comment=" + comment + "]\n");
        for (Race c : races) {
            sb.append(c);
        }
        return sb.toString();
    }

    public void save(String filename) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename + ".csv", "UTF-8");
            for (Race c : races) {
                c.save(writer);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void read(String filename) {
        BufferedReader reader;
        int nbCourse = 0;
        try {
            reader = new BufferedReader(new FileReader(filename + ".csv"));
            reader.readLine();// bib number, name,...
            String line;
            while ((line = reader.readLine()) != null) {
                String myLine = line.replace(";", " ;");
                System.out.println(myLine);

                StringTokenizer tk = new StringTokenizer(myLine, ";");

                // new runner with empty fields
                Runner coureur = new Runner();

                String dossard = tk.nextToken().trim();
                if (dossard.isEmpty()) {
                    // strange line stop here
                    continue;
                }
                coureur.bibNb = Integer.parseInt(dossard);
                try {
                    coureur.sex = Sex.valueOf(tk.nextToken().trim());
                } catch (Exception e) {
                    continue;
                }
                coureur.name = tk.nextToken().trim();
                if (coureur.name.isEmpty()) {
                    // strange line stop here
                    continue;
                }
                coureur.firstName = tk.nextToken().trim();
                coureur.birthday = tk.nextToken().trim();
                if (!coureur.birthday.isEmpty()) {
                    coureur.setAge();
                }
                coureur.raceName = tk.nextToken().trim();

                try {
                    // Optional fields
                    coureur.hasPaid = !tk.nextToken().trim().isEmpty(); // !empty => has paid
                    coureur.licence = tk.nextToken().trim();
                    coureur.club = tk.nextToken().trim();
                    coureur.certifOk = Boolean.parseBoolean(tk.nextToken().trim());
                    coureur.address = tk.nextToken().trim();
                    coureur.city = tk.nextToken().trim();
                    coureur.email = tk.nextToken().trim();
                } catch (Exception e) {

                }

                Race course = null;
                for (Race c : races) {
                    if (c.raceName.equalsIgnoreCase(coureur.raceName)) {
                        course = c;
                    }
                }

                if (course == null) {
                    course = new Race(coureur.raceName);
                    races.add(course);
                    switch (nbCourse) {
                    case 0:
                        course.color = Color.white;
                        break;
                    case 1:
                        course.color = Color.lightGray;
                        break;
                    case 2:
                        course.color = Color.green;
                        break;
                    }
                    nbCourse++;
                }

                this.participants.add(coureur);
                course.participants.add(coureur);
                coureur.setRaceName(course.raceName);

                // next
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
