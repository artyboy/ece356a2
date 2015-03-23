package ece356;

public class Patient {
    private String patientAlias;
    private String city;
    private String province;
    private String email;
    private String patientName;
    private int friendStatus;

    public Patient(String patientAlias, String city, String province,
            String email, String patientName, int friendStatus) {
        this.patientAlias = patientAlias;
        this.city = city;
        this.province = province;
        this.email = email;
        this.patientName = patientName;
        this.friendStatus = friendStatus;
    }
    
    public Patient(String patientAlias) {
        this.patientAlias = patientAlias;
    }

    public String getPatientAlias() {
        return patientAlias;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getEmail() {
        return email;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public int getFriendStatus() {
        return friendStatus;
    }
    
    public void setPatientAlias(String patientAlias){
        this.patientAlias = patientAlias;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }
  
}
