public static boolean verifyLogIn(HttpServletRequest request)
           throws ClassNotFoundException, SQLException {
  Connection con = null;
  PreparedStatement pStmt = null;
  try {
  con = getConnection();
  String retrieveUserPwd = "SELECT * FROM users WHERE alias=?;";
     		pStmt = con.prepareStatement(retrieveUserPwd);
     		pStmt.setString(1, pat_alias); 
     		ResultSet rs = pStmt.executeQuery();
     		while(rs.next()){
     			aliasDB = rs.getString("alias");
         			passwordDB = rs.getString("password");
     		}
     		if(pat_alias.equals(aliasDB) && BCrypt.checkpw(password, passwordDB))
     			return true;
     		else
         		return false;
  }
}


public static String gensalt(int log_rounds, SecureRandom random) {
  StringBuffer rs = new StringBuffer();
  byte rnd[] = new byte[BCRYPT_SALT_LEN];

  random.nextBytes(rnd);

  rs.append("$2a$");
  if (log_rounds < 10)
    rs.append("0");
  if (log_rounds > 30) {
    throw new IllegalArgumentException(
       "log_rounds exceeds maximum (30)");
  }
  rs.append(Integer.toString(log_rounds));
  rs.append("$");
  rs.append(encode_base64(rnd, rnd.length));
  return rs.toString();
}
