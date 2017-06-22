package imtpmd.jb_app_imtpmd;

/**
 * Created by Bram on 19-6-2017.
 */

public class Course {
    private String vak;
    private String ec;
    private String behaald;

    public Course(String vak, String ec, String behaald) {
        this.vak = vak;
        this.ec = ec;
        this.behaald = behaald;
    }

    public String getBehaald() {
        return behaald;
    }

    public void setBehaald(String behaald) {
        this.behaald = behaald;
    }

    public Course(String vak, String ec) {
        this.vak = vak;

        this.ec = ec;
    }

    public String getVak() {
        return vak;
    }

    public void setVak(String vak) {
        this.vak = vak;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    @Override
    public String toString() {
        return ("Vak/project: " + vak + "\n" + "Punten: " + ec + "\n" + "Behaald: " + behaald);
    }
}
