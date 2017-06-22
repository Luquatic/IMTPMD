package imtpmd.jb_app_imtpmd.Models;

/**
 * Created by Jessey Fransen on 21/06/2017.
 */

public class CourseModel {

    int jaar;
    int periode;
    String naam;
    int ec;
    int gehaald;

    CourseModel(int jaar, int periode, String naam, int ec, int gehaald){
        this.jaar = jaar;
        this.periode = periode;
        this.naam = naam;
        this.ec = ec;
        this.gehaald = gehaald;
    }

    public String getNaam(){
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public int getPeriode() {
        return periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public int getEc() {
        return ec;
    }

    public void setEc(int ec) {
        this.ec = ec;
    }

    public int isGehaald() {
        return gehaald;
    }

    public void setGehaald(int gehaald) {
        this.gehaald = gehaald;
    }
}
