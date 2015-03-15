package ece356;

import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wojciech Golab
 */
/*
public class Lab3DBAO {

    public static final String host = "localhost";
    public static final String url = "jdbc:mysql://" + host + ":3306/";
    public static final String nid = "wgolab";
    public static final String user = "user_" + nid;
    public static final String pwd = "user_balogw";

    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    */

public class Lab3DBAO {

    //public static final String host = "localhost";
    public static final String host = "eceweb.uwaterloo.ca";
    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";// + host + ":3306/";
    public static final String nid = "nhleung";
    public static final String user = "user_" + nid;
    public static final String pwd = "artemis93db356";//"user_" + nid;

    public static void testConnection()
            throws ClassNotFoundException, SQLException {
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
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = null;
        try {
            con.createStatement();
            stmt = con.createStatement();
            stmt.execute("USE ece356db_nhleung");//hospital_" + nid);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }

    public static ArrayList<Employee> getEmployees()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Employee> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Employee");
            ret = new ArrayList<Employee>();
            while (resultSet.next()) {
                Employee e = new Employee(
                        resultSet.getInt("empID"),
                        resultSet.getString("empName"),
                        resultSet.getString("job"),
                        resultSet.getInt("deptID"),
                        resultSet.getInt("salary"));
                ret.add(e);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static ArrayList getDepartments()
            throws ClassNotFoundException, SQLException {
        {
            Connection con = null;
            Statement stmt = null;
            ArrayList ret = null;
            try {
                con = getConnection();
                stmt = con.createStatement();
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM Department");
                ret = new ArrayList<Employee>();
                while (resultSet.next()) {
                    Department d = new Department(
                            resultSet.getInt("Department.deptID"),
                            resultSet.getString("Department.deptName"),
                            resultSet.getString("Department.location"));
                    ret.add(d);
                }
                return ret;
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public static ArrayList<Employee> queryEmployee(int empID, String empName, int deptID, String job, int salary)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Employee> ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "SELECT * FROM Employee WHERE TRUE";
            if (empID != -1) {
                query += " AND empID = ?";
            }
            if (empName.length() != 0) {
                query += " AND empName = ?";
            }
            if (deptID != -1) {
                query += " AND deptID = ?";
            }
            if (job.length() != 0) {
                query += " AND job = ?";
            }
            if (salary != -1) {
                query += " AND salary = ?";
            }

            pstmt = con.prepareStatement(query);

            int num = 0;
            if (empID != -1) {
                pstmt.setInt(++num, empID);
            }
            if (empName.length() != 0) {
                pstmt.setString(++num, empName);
            }
            if (deptID != -1) {
                pstmt.setInt(++num, deptID);
            }
            if (job.length() != 0) {
                pstmt.setString(++num, job);
            }
            if (salary != -1) {
                pstmt.setInt(++num, salary);
            }

            ResultSet resultSet;
            resultSet = pstmt.executeQuery();

            ret = new ArrayList<Employee>();
            while (resultSet.next()) {
                Employee e = new Employee(
                        resultSet.getInt("empID"),
                        resultSet.getString("empName"),
                        resultSet.getString("job"),
                        resultSet.getInt("deptID"),
                        resultSet.getInt("salary"));
                ret.add(e);
            }
            return ret;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Doctor> queryDoctor(String doctorName, String gender,
            String street,String city, String province, String country, 
            String postalCode,String specialization, int yearsLicensed,
            double avgStarRating, String comments, Boolean reviewedByFriend)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Doctor> ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select DocRatings.name,DocRatings.gender,DocRatings.doc_alias,"
                    + "DocRatings.avg_star_rating,DocRatings.num_of_reviews from "
                    +"(select DocAdrSpec.name,DocAdrSpec.gender,DocAdrSpec.doc_alias,"
                    + "Ratings.avg_star_rating,Ratings.num_of_reviews from "
                    + "(select Doctor.doc_alias,name,gender,avg(rating) as avg_star_rating," +
                "count(rating) as num_of_reviews " +
                "from Doctor natural join Review " +
                "where Doctor.doc_alias=Review.doc_alias " +
                "group by Doctor.doc_alias) as Ratings ";
            
             query+="right join (SELECT Doctor.doc_alias, Doctor.name, "
                    + "Doctor.gender FROM Doctor natural join DoctorAddress "
                    + "natural join DoctorSpecialization WHERE TRUE ";
            if(doctorName.length() != 0){
                query+= " AND name like ?";
            }
            if(gender.length() != 0){
                query+= " AND gender = ?";
            }
            if(street.length() != 0){
                query+= " AND street like ?";
            }
            if(city.length() != 0){
                query+= " AND city like ?";
            }
            if(province.length() != 0){
                query+= " AND province like ?";
            }
            if(country.length() != 0){
                query+= " AND country like ?";
            }
            if(postalCode.length() != 0){
                query+= " AND postal_code like ?";
            }
            if(specialization.length() != 0){
                query+= " AND specialization like ?";
            }
            if(yearsLicensed != -1){
                query+= " AND years_licensed >= ?";
            }
//            if(query.endsWith("WHERE ")){
//                query = query.substring(0, query.lastIndexOf("WHERE"));
//            }
            query+=" GROUP BY Doctor.doc_alias";
            query+=") as DocAdrSpec on Ratings.doc_alias=DocAdrSpec.doc_alias)"
                    + "as DocRatings";
            

            
            if(comments.length() != 0){
                query+=" natural join (select Doctor.doc_alias from Doctor natural join Review " +
                    "where Doctor.doc_alias=Review.doc_alias ";
            }
                    if(reviewedByFriend){
                query+="and Review.pat_alias in(" +
                        "select pat_alias from Patient " +
                        "where pat_alias=(" +
                        "select pat_alias1 from Friend where pat_alias2=? " +
                        "and pat_alias1 in " +
                        "(select pat_alias2 from Friend where pat_alias1=?)" +
                        ")" +
                        ")";
                    }
            if(comments.length() != 0){
                query+="and comments like ? " +
                    "group by Doctor.doc_alias) as Friend; ";
            }
            pstmt = con.prepareStatement(query);

            int num = 0;
           
            if (doctorName.length() != 0) {
                pstmt.setString(++num, "%"+doctorName+"%");
            }
            if (gender.length() != 0) {
                pstmt.setString(++num, gender);
            }
            if (street.length() != 0) {
                pstmt.setString(++num, "%"+street+"%");
            }
            if (city.length() != 0) {
                pstmt.setString(++num, "%"+city+"%");
            }
            if (province.length() != 0) {
                pstmt.setString(++num, "%"+province+"%");
            }
            if (country.length() != 0) {
                pstmt.setString(++num, "%"+country+"%");
            }
            if (postalCode.length() != 0) {
                pstmt.setString(++num, "%"+postalCode+"%");
            }
            if (specialization.length() != 0) {
                pstmt.setString(++num, "%"+specialization+"%");
            }
            if (yearsLicensed!= -1) {
                pstmt.setInt(++num, yearsLicensed);
            }
            if (comments.length() != 0) {
                pstmt.setString(++num, "%"+comments+"%");
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();

            ret = new ArrayList<Doctor>();
            while (resultSet.next()) {
                Doctor d = new Doctor(
                        resultSet.getString("name"),
                        resultSet.getString("gender"),
                        resultSet.getDouble("avg_star_rating"),
                        resultSet.getInt("num_of_reviews"));
                ret.add(d);
            }
            return ret;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static void addEmployee(Employee em)
            throws ClassNotFoundException, SQLException {
        {
            Connection con = null;
            PreparedStatement pstmt = null;
            ArrayList ret = null;
            try {
                con = getConnection();
                pstmt = con.prepareStatement("INSERT INTO Employee VALUES(?, ?, ?, ?, ?)");
                pstmt.setInt(1, em.getEmpID());
                pstmt.setString(2, em.getEmpName());
                pstmt.setString(3, em.getJob());
                pstmt.setInt(4, em.getDeptID());
                pstmt.setInt(5, em.getSalary());
                pstmt.executeUpdate();
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
    }

    public static void deleteEmployee(int empID)
            throws ClassNotFoundException, SQLException {
        {
            Connection con = null;
            PreparedStatement pstmt = null;
            ArrayList ret = null;
            try {
                con = getConnection();
                pstmt = con.prepareStatement("DELETE FROM Employee WHERE empID = ?");
                pstmt.setInt(1, empID);
                pstmt.executeUpdate();
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
    }
}
