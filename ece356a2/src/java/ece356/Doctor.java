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
    
    private String street;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private String[] specializations;
    private int yearsLicensed;
    private int averageStarRating;

    public Doctor(String doctorAlias, String doctorName, String gender, 
            String Street, String street, String city, String province,
            String country, String postalCode, String[] specializations,
            int yearsLicensed, int averageStarRating) {
        this.doctorAlias = doctorAlias;
        this.doctorName = doctorName;
        this.gender = gender;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.specializations = specializations;
        this.yearsLicensed = yearsLicensed;
        this.averageStarRating = averageStarRating;
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

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String[] getSpecializations() {
        return specializations;
    }

    public int getYearsLicensed() {
        return yearsLicensed;
    }

    public int getAverageStarRating() {
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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setSpecializations(String[] specializations) {
        this.specializations = specializations;
    }

    public void setYearsLicensed(int yearsLicensed) {
        this.yearsLicensed = yearsLicensed;
    }

}
