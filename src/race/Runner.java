package race;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class Runner implements Comparable<Runner> {
    public static String ABANDON = "abandon";

    // runner info
    public int bibNb = 0;
    public Sex sex = Sex.M;
    public String name = "Onyme";
    public String firstName = "Anne";
    public String birthday = "01/10/1977";
    public int age = 0;
    public String raceName = "";
    public boolean hasPaid = true;
    // club
    public String licence = "";
    public String club = "SC";
    public boolean certifOk = true;
    // contacts
    public String address = "";
    public String city = "";
    public String email = "";
    // results
    public String time = "00:00:00";
    public int rank = 0;

    public Runner(String nom, String prenom, String adresse, String ville, String naissance, String sexe,
            String licence, String club, String certifOk) {
        super();
        this.name = nom;
        this.firstName = prenom;
        this.address = adresse;
        this.city = ville;
        this.birthday = naissance;
        this.sex = Sex.valueOf(sexe.trim());
        this.licence = licence;
        this.club = club;
        this.certifOk = Boolean.parseBoolean(certifOk);

        try {

            String str[] = naissance.trim().split("/");
            int dd = Integer.parseInt(str[0]);
            int mm = Integer.parseInt(str[1]);
            int yy = Integer.parseInt(str[2]);
            LocalDate b = LocalDate.of(yy, mm, dd);
            this.age = Period.between(b, LocalDate.now()).getYears();
        } catch (Exception e) {
            System.out.println(naissance);
            System.out.println(e.getMessage());
        }
    }

    public Runner() {
        // TODO Auto-generated constructor stub
    }

    public int getBibNb() {
        return bibNb;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddrress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public Sex getSex() {
        return sex;
    }

    public String getLicence() {
        return licence;
    }

    public String getClub() {
        return club;
    }

    public boolean isCertifOk() {
        return certifOk;
    }

    public String getTime() {
        return this.time;
    }

    public int getRank() {
        return this.rank;
    }

    public String getRace() {
        return raceName;
    }

    public boolean isPaid() {
        return hasPaid;
    }

    public void setBibNb(int dossard) {
        this.bibNb = dossard;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public void setFirstName(String prenom) {
        this.firstName = prenom;
    }

    public void setAddress(String adresse) {
        this.address = adresse;
    }

    public void setCity(String ville) {
        this.city = ville;
    }

    public void setAge() {
        try {

            String str[] = birthday.trim().split("/");
            int dd = Integer.parseInt(str[0]);
            int mm = Integer.parseInt(str[1]);
            int yy = Integer.parseInt(str[2]);
            LocalDate b = LocalDate.of(yy, mm, dd);
            this.age = Period.between(b, LocalDate.now()).getYears();
        } catch (Exception e) {
            System.out.println(birthday);
            System.out.println(e.getMessage());
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNaissance(String naissance) {
        this.birthday = naissance;
    }

    public void setSex(Sex sexe) {
        this.sex = sexe;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setCertifOk(boolean certifOk) {
        this.certifOk = certifOk;
    }

    public void setTime(String temps) {
        this.time = temps;
    }

    public void setRank(int rang) {
        this.rank = rang;
    }

    public void setRaceName(String course) {
        this.raceName = course;
    }

    public void setPaid(boolean paye) {
        this.hasPaid = paye;
    }

    @Override
    public String toString() {
        return String.format("%03d", bibNb);
        // return " Coureur [prenom=" + prenom + ", nom=" + nom +
        // ", adresse=" + adresse + ", ville=" + ville + ", age=" + age +
        // ", naissance=" + naissance
        // + ", sexe=" + sexe + ", licence=" + licence + ", club=" + club +
        // ", certifOk=" + certifOk + "]\n";
    }

    public void save(PrintWriter writer) {
        writer.println(bibNb + "," + sex + "," + name + "," + firstName + "," + birthday + "," + raceName + ","
                + hasPaid + "," + licence + "," + club + "," + certifOk + "," + address + "," + city + "," + email + ","
                + time + "," + rank);
    }

    public void saveResultat(PrintWriter writer) {
        writer.println(rank + "," + time + "," + sex + "," + name + "," + firstName + "," + bibNb + "," + birthday + ","
                + raceName + "," + hasPaid + "," + licence + "," + club + "," + certifOk + "," + address + "," + city
                + "," + email);
    }

    @Override
    public int compareTo(Runner arg0) {
        // "%d:%02d:%02d"
        String strTemps0 = arg0.getTime();
        if (strTemps0.equalsIgnoreCase(ABANDON)) {
            return -1;
        }
        String str[] = strTemps0.split(":");
        int h = Integer.parseInt(str[0]);
        int mm = Integer.parseInt(str[1]);
        int ss = Integer.parseInt(str[2]);
        LocalTime temps0 = LocalTime.of(h, mm, ss);

        // "%d:%02d:%02d"
        String strTemps1 = this.getTime();
        if (strTemps1.equalsIgnoreCase(ABANDON)) {
            return 1;
        }
        str = strTemps1.split(":");
        h = Integer.parseInt(str[0]);
        mm = Integer.parseInt(str[1]);
        ss = Integer.parseInt(str[2]);
        LocalTime thisTime = LocalTime.of(h, mm, ss);

        return thisTime.compareTo(temps0);
    }

}
