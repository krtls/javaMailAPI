package net.pack.mail;

public class TestApp {
    
	public static void main(String[] args) {
		MyDb db = new MyDb();
		ReadingEmail re = new ReadingEmail();
	
		try {
			db.openConn();System.out.print("Veritaban� ba�lant�s� yap�ld�.\n");
		} catch (Exception e) {
			e.printStackTrace();System.out.print("Veritaban�na ba�lan�lamad�!\n");
		}
		
		TimerControl.main(args);
		//re.read();
		
	}

}
