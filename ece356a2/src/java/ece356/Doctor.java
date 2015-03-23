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
