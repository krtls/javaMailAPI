package net.pack.mail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDb {
	private Connection conn = null; //Ba�lant� nesnemiz
    private final String dbUrl = "jdbc:mysql://localhost:3306/";//veritaban�n�n adresi ve portu
    private final String dbName = "mail_db"; //veritaban�n�n ismi
    private final String properties= "?useUnicode=true&amp;characterEncoding=utf8"; //T�rk�e karakter problemi ya�amamak i�in
    private final String driver = "com.mysql.jdbc.Driver";//MySQL-Java ba�lant�s�n� sa�layan JDBC s�r�c�s�
    private final String userName = "root"; //veritaban� i�in kullan�c� ad�
    private final String password = ""; //kullan�c� �ifresi
    private ResultSet res; // sorgulardan d�necek kay�tlar (sonu� k�mesi) bu nesne i�erisinde tutulacak
    private Statement stmt;

    public void closeConn() throws Exception {
    	conn.close();
    }
    
    public Statement openConn() throws Exception {
    	Class.forName(driver).newInstance();
    	conn = (Connection) DriverManager.getConnection(dbUrl + dbName + properties, userName, password);//ba�lant� a��l�yor
    	return conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
    	//return
    	//conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    }
    
    public int getGroup() throws Exception{
    	String sql="select max(group_no) from mails";
    	try {
				stmt = (Statement) openConn();
				res = stmt.executeQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		int max = 0; 
		while( res.next() )
		{
		    max = res.getInt(1);
		}
	
		return max;
    }
    
    public boolean existNo(String ogrNo) throws SQLException{
    	String sql="select count(*) as adet from mails where no='"+ogrNo+"'";
    	int adet=0;
    	try {
				stmt = (Statement) openConn();
				res = stmt.executeQuery(sql);
			} catch (Exception e) {
				e.printStackTrace();
			} 
    	if (res.first()) {
    		adet=res.getInt("adet");
    		//System.out.println("D�NEN ADET:   "+adet);
    	}
    	if(adet>0)
    		return false;
    	else
    		return true;
    	
    }
    
    public void insertRow(int group, String no, String name){
	String sql="insert into mails (group_no,no,name) values ("+group+",'"+no+"','"+name+"')"; 
		try {
			stmt = (Statement) openConn(); 
			res = stmt.executeQuery(sql); 
			//JOptionPane.showMessageDialog(null, "okkkk");
			} catch (Exception ex) {
			Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
			//JOptionPane.showMessageDialog(null, "Malesef");
			}
			System.out.println(res);
			try {
			//int cvp = JOptionPane.showConfirmDialog(null, "Tabloya Eklemek �stiyor musunuz?",
			//"Ekleme", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			//if (cvp == JOptionPane.YES_OPTION) {
			stmt.executeUpdate(sql);
			
			//}
			}
			catch (SQLException ex) {
			Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
			closeConn();
			} catch (Exception ex) {
			Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
			}	
	
}

}
