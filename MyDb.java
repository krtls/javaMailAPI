package net.pack.mail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDb {
	private Connection conn = null; //Baðlantý nesnemiz
    private final String dbUrl = "jdbc:mysql://localhost:3306/";//veritabanýnýn adresi ve portu
    private final String dbName = "mail_db"; //veritabanýnýn ismi
    private final String properties= "?useUnicode=true&amp;characterEncoding=utf8"; //Türkçe karakter problemi yaþamamak için
    private final String driver = "com.mysql.jdbc.Driver";//MySQL-Java baðlantýsýný saðlayan JDBC sürücüsü
    private final String userName = "root"; //veritabaný için kullanýcý adý
    private final String password = ""; //kullanýcý þifresi
    private ResultSet res; // sorgulardan dönecek kayýtlar (sonuç kümesi) bu nesne içerisinde tutulacak
    private Statement stmt;

    public void closeConn() throws Exception {
    	conn.close();
    }
    
    public Statement openConn() throws Exception {
    	Class.forName(driver).newInstance();
    	conn = (Connection) DriverManager.getConnection(dbUrl + dbName + properties, userName, password);//baðlantý açýlýyor
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
    		//System.out.println("DÖNEN ADET:   "+adet);
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
			//int cvp = JOptionPane.showConfirmDialog(null, "Tabloya Eklemek Ýstiyor musunuz?",
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
