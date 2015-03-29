package ece356;


import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



public class HandleUserLogIn {
    public static final String host = "eceweb.uwaterloo.ca";
    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";// + host + ":3306/";
    public static final String nid = "nhleung";
    public static final String user = "user_" + nid;
    public static final String pwd = "artemis93db356";

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
            stmt.execute("USE ece356db_nhleung");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
   
   public static boolean verifyLogIn(HttpServletRequest request)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pStmt = null;
        PreparedStatement pStmt1 = null;
        HttpSession session = request.getSession(true);
        String pat_alias = ((UserData)session.getAttribute("userData")).getAlias();
        String password = ((UserData)session.getAttribute("userData")).getPassword();
        String aliasDB = null;
        String passwordDB = null;
    //    String hashed = null;
        try {
            con = getConnection();
            String retrieveUserPwd = "SELECT * FROM users WHERE alias=?;";
            pStmt = con.prepareStatement(retrieveUserPwd);
            pStmt.setString(1, pat_alias); 
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()){
                aliasDB = rs.getString("alias");
                passwordDB = rs.getString("password");
            //    hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            //    String updatePwd = "UPDATE users SET password=? WHERE alias=?;";
            //    pStmt1 = con.prepareStatement(updatePwd);
            //    pStmt1.setString(1, hashed);
            //    pStmt1.setString(2, pat_alias);
            //    pStmt1.executeUpdate();
            }
            if (pat_alias.equals(aliasDB) && BCrypt.checkpw(password, passwordDB))
                return true;
            else
                return false;
           /* if(pat_alias.equals(aliasDB) && password.equals(passwordDB)){
                
            }else{
                
            }*/
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
