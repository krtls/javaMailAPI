package net.pack.mail;

public class TestApp {
    
	public static void main(String[] args) {
		MyDb db = new MyDb();
		ReadingEmail re = new ReadingEmail();
	
		try {
			db.openConn();System.out.print("Veritabanı bağlantısı yapıldı.\n");
		} catch (Exception e) {
			e.printStackTrace();System.out.print("Veritabanına bağlanılamadı!\n");
		}
		
		TimerControl.main(args);
		//re.read();
		
	}

}
