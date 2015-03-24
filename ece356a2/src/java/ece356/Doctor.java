/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

/**
 *
 * @author Nathan
 */
public class Doctor {  
    private String doctorAlias;
    private String doctorName;
    private String gender;
    
    private int yearsLicensed;
    private double averageStarRating;
    private int numOfReviews;
    private String email;

    public Doctor() {
    }
    public Doctor(String doctorAlias, String doctorName, String gender, 
            double averageStarRating, int numOfReviews) {
        this.doctorAlias = doctorAlias;
        this.doctorName = doctorName;
        this.gender = gender;
        this.averageStarRating = averageStarRating;
        this.numOfReviews = numOfReviews;
    }
    public Doctor(String doctorAlias, String doctorName, String gender, 
            double averageStarRating, int numOfReviews, int yearsLicensed) {
        this.doctorAlias = doctorAlias;
        this.doctorName = doctorName;
        this.gender = gender;
        this.averageStarRating = averageStarRating;
        this.numOfReviews = numOfReviews;
        this.yearsLicensed = yearsLicensed;
    }
    public Doctor(String doctorAlias, String doctorName, String gender, 
            double averageStarRating, int numOfReviews, int yearsLicensed,String email) {
        this.doctorAlias = doctorAlias;
        this.doctorName = doctorName;
        this.gender = gender;
        this.averageStarRating = averageStarRating;
        this.numOfReviews = numOfReviews;
        this.yearsLicensed = yearsLicensed;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getNumOfReviews() {
        return numOfReviews;
    }

    public String getDoctorAlias() {
        return doctorAlias;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getGender() {
        return gender;
    }

    public int getYearsLicensed() {
        return yearsLicensed;
    }

    public double getAverageStarRating() {
        return averageStarRating;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDoctorAlias(String doctorAlias) {
        this.doctorAlias = doctorAlias;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYearsLicensed(int yearsLicensed) {
        this.yearsLicensed = yearsLicensed;
    }

    public void setAverageStarRating(double averageStarRating) {
        this.averageStarRating = averageStarRating;
    }

    public void setNumOfReviews(int numOfReviews) {
        this.numOfReviews = numOfReviews;
    }
    

}
