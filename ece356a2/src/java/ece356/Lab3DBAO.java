package ece356;

import java.sql.*;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

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
    //public static final String host = "eceweb.uwaterloo.ca";
    //public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";// + host + ":3306/";
    //public static final String nid = "nhleung";
    //public static final String user = "user_" + nid;
    //public static final String pwd = "artemis93db356";//"user_" + nid;

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
            stmt.execute("USE ece356db_nhleung");//hospital_" + nid);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }

    public static ArrayList<Doctor> queryDoctor(String userAlias,String doctorName, String gender,
            String street,String city, String province, 
            String postalCode,String specialization, int yearsLicensed,
            double avgStarRating, String comments, Boolean reviewedByFriend)
            throws ClassNotFoundException, SQLException, NamingException {
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
            if(postalCode.length() != 0){
                query+= " AND postal_code like ?";
            }
            if(specialization.length() != 0){
                query+= " AND specialization like ?";
            }
            if(yearsLicensed != -1){
                query+= " AND YEAR(CURDATE()) - license_year >= ?";
            }
            query+=" GROUP BY Doctor.doc_alias";
            query+=") as DocAdrSpec on Ratings.doc_alias=DocAdrSpec.doc_alias)"
                    + "as DocRatings ";
            

            
            if(comments.length() != 0 || reviewedByFriend){
                query+=" natural join (select Doctor.doc_alias from Doctor "
                        + "natural join Review where TRUE";
            }
            if(comments.length() != 0){
                 query+=" and Doctor.doc_alias=Review.doc_alias ";
            }
            if(reviewedByFriend){
                query+=" and Review.pat_alias in(" +
                "select pat_alias from Patients " +
                "where pat_alias=(" +
                "select pat_alias1 from Friend where pat_alias2=? " +
                "and pat_alias1 in " +
                "(select pat_alias2 from Friend where pat_alias1=?)" +
                ")" +
                ")";
            }
            if(comments.length() != 0){
                query+="and comments like ? ";
            }
            if(comments.length() != 0 || reviewedByFriend){
                query+="group by Doctor.doc_alias) as Friend ";
            }
            if(avgStarRating != -1.0){
                query+=" WHERE DocRatings.avg_star_rating >= ?";
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
            if (postalCode.length() != 0) {
                pstmt.setString(++num, "%"+postalCode+"%");
            }
            if (specialization.length() != 0) {
                pstmt.setString(++num, "%"+specialization+"%");
            }
            if (yearsLicensed!= -1) {
                pstmt.setInt(++num, yearsLicensed);
            }
            //need to change for testing
            if(reviewedByFriend){
                pstmt.setString(++num, userAlias);
                pstmt.setString(++num, userAlias);
            }
            if (comments.length() != 0) {
                pstmt.setString(++num, "%"+comments+"%");
            }
            if (avgStarRating != -1.0) {
                pstmt.setDouble(++num, avgStarRating);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();

            ret = new ArrayList<Doctor>();
            while (resultSet.next()) {
                Doctor d = new Doctor(
                        resultSet.getString("doc_alias"),
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
    
    public static Doctor viewDoctorProfile(String doctorAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        Doctor ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query ="select DocAlias.doc_alias,DocAlias.name," +
            "DocAlias.gender,DocAlias.license_year,Ratings.avg_star_rating,"
                    + "Ratings.num_of_reviews from " +
            "(select doc_alias, avg(rating) as avg_star_rating," +
            "count(rating) as num_of_reviews " +
            "from Doctor natural join Review " +
            "where Doctor.doc_alias=Review.doc_alias and doc_alias=?"
                    + "group by Doctor.doc_alias) as Ratings";
            query+=" right join (select doc_alias,name,gender,license_year from Doctor "
                    + "WHERE doc_alias=?) as DocAlias "
                    + "on Ratings.doc_alias=DocAlias.doc_alias";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (doctorAlias.length() != 0) {
                pstmt.setString(++num, doctorAlias);
                pstmt.setString(++num, doctorAlias);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            ret = new Doctor();
            while (resultSet.next()) {
                Doctor d = new Doctor(
                        resultSet.getString("doc_alias"),
                        resultSet.getString("name"),
                        resultSet.getString("gender"),
                        resultSet.getDouble("avg_star_rating"),
                        resultSet.getInt("num_of_reviews"),
                        resultSet.getInt("license_year"));
                ret = d;
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
     public static ArrayList<Address> getAddresses(String doctorAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Address> ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select street,city,province,postal_code "
                    + "from DoctorAddress " +
                "where doc_alias=?;";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (doctorAlias.length() != 0) {
                pstmt.setString(++num, doctorAlias);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            ret = new ArrayList<Address>();
            while (resultSet.next()) {
                Address a = new Address(
                        resultSet.getString("street"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postal_code"));
                ret.add(a);
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
     public static ArrayList<Specialization> getSpecs(String doctorAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Specialization> ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select specialization from DoctorSpecialization\n" +
                "where doc_alias=?";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (doctorAlias.length() != 0) {
                pstmt.setString(++num, doctorAlias);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            ret = new ArrayList<Specialization>();
            while (resultSet.next()) {
                Specialization s = new Specialization(
                        resultSet.getString("specialization"));
                ret.add(s);
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
     public static ArrayList<Review> getReviews(String doctorAlias)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Review> ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select * from Review " +
                "where doc_alias=?" +
                "order by date desc;";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (doctorAlias.length() != 0) {
                pstmt.setString(++num, doctorAlias);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            ret = new ArrayList<Review>();
            while (resultSet.next()) {
                Review r = new Review(
                        resultSet.getInt("rev_id"),
                        resultSet.getString("date"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comments")
                );
                ret.add(r);
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
     public static Review readReview(int revID)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        Review ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select * from Review " +
                "where rev_id=?";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (revID != -1) {
                pstmt.setInt(++num, revID);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            ret = new Review();
            while (resultSet.next()) {
                ret = new Review(
                        resultSet.getInt("rev_id"),
                        resultSet.getString("date"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comments")
                );
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
     public static int getNextReview(String docAlias, int revID)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        Review ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select rev_id from Review " +
                "where doc_alias=? and rev_id > ?" +
                " order by date;";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (docAlias.length() != 0) {
                pstmt.setString(++num, docAlias);
            }
            if (revID != -1) {
                pstmt.setInt(++num, revID);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            if(resultSet.first()){
                return resultSet.getInt("rev_id");
            }
            else{
                return revID;
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
     public static int getPrevReview(String docAlias, int revID)
            throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement pstmt = null;
        Review ret;

        try {
            con = getConnection();

            /* Build SQL query */
            String query = "select rev_id from Review " +
                "where doc_alias=? and rev_id < ?" +
                " order by date desc;";
            pstmt = con.prepareStatement(query);
            int num = 0;
            if (docAlias.length() != 0) {
                pstmt.setString(++num, docAlias);
            }
            if (revID != -1) {
                pstmt.setInt(++num, revID);
            }
            ResultSet resultSet;
            resultSet = pstmt.executeQuery();
            if(resultSet.first()){
                return resultSet.getInt("rev_id");
            }
            else{
                return revID;
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
public static void writeReview(String userAlias, String docAlias, double rating, String comments)
            throws ClassNotFoundException, SQLException, NamingException {
        {
            Connection con = null;
            PreparedStatement pstmt = null;
            ArrayList ret = null;
            try {
                con = getConnection();
                //con.setAutoCommit(false);
                //con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                pstmt = con.prepareStatement("INSERT INTO "
                        + "Review(pat_alias,doc_alias,date,rating,comments) "
                        + " VALUES(?, ?, CURDATE(), ?, ?)");
                pstmt.setString(1, userAlias);
                pstmt.setString(2, docAlias);
                pstmt.setDouble(3, rating);
                pstmt.setString(4, comments);
                pstmt.executeUpdate();
                //con.commit();
            } 
//            catch(SQLException se){
//                con.rollback();
//            }
                finally {
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
            throws ClassNotFoundException, SQLException, NamingException {
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
