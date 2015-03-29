package ece356;

import static ece356.PatientDBAO.getConnection;
import static ece356.PatientDBAO.pwd;
import static ece356.PatientDBAO.url;
import static ece356.PatientDBAO.user;
import java.sql.*;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



public class PatientDBAO {
    public static final String host = "eceweb.uwaterloo.ca";
    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";// + host + ":3306/";
    public static final String nid = "nhleung";
    public static final String user = "user_" + nid;
    public static final String pwd = "artemis93db356";

    public static void testConnection()
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static Connection getConnection()
            throws ClassNotFoundException, SQLException, NamingException {
        Class.forName("com.mysql.jdbc.Driver");
        InitialContext cxt = new InitialContext();
        if (cxt == null) {
        throw new RuntimeException("Unable to create naming context!");
        }
        Context dbContext = (Context) cxt.lookup("java:comp/env");
        DataSource ds = (DataSource) dbContext.lookup("jdbc/myDatasource");
        if (ds == null) {
        throw new RuntimeException("Data source not found!");
        }
        Connection con = ds.getConnection();
        //Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = null;
        try {
            con.createStatement();
            stmt = con.createStatement();
            stmt.execute("USE ece356db_nhleung");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
    
   public static ArrayList<Patient> searchPatient(HttpServletRequest request, String userAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pStmt = null;
       // Statement pStmt = con.createStatement();
        ArrayList<Patient> patient;
        Patient savedQuery = new Patient();
        try {
            con = getConnection();
            HttpSession session = request.getSession(true);
            String sql = "SELECT * FROM Patients WHERE TRUE ";  
            String patientAlias = request.getParameter("Palias");
            if(patientAlias == null){
                patientAlias = (((Patient)session.getAttribute("patientQuery"))!= null) ? 
                        ((Patient)session.getAttribute("patientQuery")).getPatientAlias(): "" ;
            }else{
                savedQuery.setPatientAlias(patientAlias);
            }
            if(patientAlias.length()!=0){
                sql+="AND pat_alias = ? ";
            }
            String patientProvince = request.getParameter("Pprovince");
            
            if(patientProvince == null){
                patientProvince = (((Patient)session.getAttribute("patientQuery")) != null) ? 
                        ((Patient)session.getAttribute("patientQuery")).getProvince(): "" ;
            }else{
                savedQuery.setProvince(patientProvince);
            }
            if(patientProvince.length()!=0 && !"Black".equals(patientProvince)){
                sql+="AND province = ? ";
            }
            String city = request.getParameter("Pcity");
            if(city == null){
                city = (((Patient)session.getAttribute("patientQuery"))!= null) ? 
                        ((Patient)session.getAttribute("patientQuery")).getCity(): "" ;
            }else{
                savedQuery.setCity(city);
            }
            if(city.length() != 0){
                sql+="AND city = ? ";
            }
            session.setAttribute("patientQuery",savedQuery);
             sql += "AND pat_alias != ?";
            sql+=";";
            
           pStmt = con.prepareStatement(sql);
            int start = 0;
            if(patientAlias.length() != 0){
                pStmt.setString(++start, request.getParameter("Palias"));
            }
            if(patientProvince.length() != 0 && !"Black".equals(patientProvince)){
                pStmt.setString(++start, request.getParameter("Pprovince"));
            }
            if(city.length() != 0){
                pStmt.setString(++start, request.getParameter("Pcity"));
            }
            pStmt.setString(++start, userAlias);
            ResultSet rs = pStmt.executeQuery();
            patient = new ArrayList<>();
            while (rs.next()) {
                int friendStatus;
                String checkStatusOneOrTwo = null;
                String pat_alias = rs.getString("pat_alias");
                // Replace this with Session object from Login Screen
                String pat_alias2 = userAlias;
                String friendReqSql = "SELECT * FROM Friend WHERE pat_alias1=? AND pat_alias2=?"
                        + " OR pat_alias1=? AND pat_alias2=?;";
                pStmt = con.prepareStatement(friendReqSql);
                pStmt.setString(1, pat_alias);
                pStmt.setString(2, pat_alias2);
                pStmt.setString(3, pat_alias2);
                pStmt.setString(4, pat_alias);
                ResultSet rs1 = pStmt.executeQuery();
                int count = 0;
                while(rs1.next()){
                    count++;
                    checkStatusOneOrTwo = rs1.getString("pat_alias1");
                }
                if(count == 0)
                    friendStatus = 0; // Send Request
                else if(count == 1){
                    if(checkStatusOneOrTwo.equals(pat_alias)) {
                        friendStatus = 1; // Accept Request
                    } else {
                        friendStatus = 2; // Not Accepted Yet
                    }
                }
                else
                    friendStatus = 3; // Friends
                    
                Patient p = new Patient(
                        pat_alias,
                        rs.getString("city"),
                        rs.getString("province"),
                        rs.getString("email"),
                        rs.getString("pat_name"),
                        friendStatus);  
                patient.add(p);
            }
            return patient;
        } finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }
        }          
    }
   
   public static ArrayList<Patient> viewRequest(HttpServletRequest request, String userAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        
        Connection con = null;
        PreparedStatement pStmt = null;
        // change the session object here
        String pat_alias2 = userAlias;
        try {
            con = getConnection();
            String sendRequestSql = "select pat_alias1 from Friend "
                    + "where pat_alias2=? and pat_alias1 " 
                    +"not in (select pat_alias2 from "
                    + "Friend where pat_alias1=?);";
            pStmt = con.prepareStatement(sendRequestSql);
            // Change the session object from Login screen here
            pStmt.setString(1, pat_alias2); 
            pStmt.setString(2, pat_alias2); 
            ResultSet rs = pStmt.executeQuery();
            ArrayList<Patient> viewRequest = new ArrayList<>();
            while(rs.next()){
                Patient Request = new Patient(
                        rs.getString("pat_alias1")
                );
                viewRequest.add(Request);
            }
            return viewRequest;
        }finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
       
   }
   
   public static void sendRequest(HttpServletRequest request, String userAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pStmt = null;
        // change the session object here
        String action = request.getParameter("action");
        String palias = request.getParameter("palias");
        try {
            con = getConnection();
            if("send".equals(action)){
                String sendRequestSql = "INSERT INTO Friend VALUES(?, ?);";
                pStmt = con.prepareStatement(sendRequestSql);
                pStmt.setString(1, userAlias);
                pStmt.setString(2, palias); 
                pStmt.executeUpdate();
            }  
            else if("accept".equals(action)){
                String sendRequestSql = "INSERT INTO Friend VALUES(?, ?);";
                pStmt = con.prepareStatement(sendRequestSql);
                pStmt.setString(1, userAlias);
                pStmt.setString(2, palias); 
                pStmt.executeUpdate();
            }
        } finally {
            if (pStmt != null) {
                pStmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
