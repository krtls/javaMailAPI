package net.pack.mail;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class ReadingEmail {
	
	public String host = "pop.gmail.com";
    public String mailStoreType = "pop3";
    public String username = "mail@mail.com";
    public String password = "passWord";
    public InternetAddress from;
    public String fromEmail="mail@gmail.com";
    MyDb db =new MyDb();
   public void read() 
   {
		SendingEmail se = new SendingEmail();
	   try {
    	  ArrayList<String> ogr;
    	  ogr = new ArrayList<String>();
    	  boolean multi=false, newOgr=false;
      //create properties field
      Properties properties = new Properties();

      properties.put("mail.imap.host", host);
      properties.put("mail.imap.port", "993");//995
      properties.put("mail.imap.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("imaps");//pop3s

      store.connect(host, username, password);

      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      //System.out.println("messages.length---" + messages.length);

      int last=messages.length;
      //for (int i = 0, n = messages.length; i < n; i++) {
      	 Message message = messages[last-1];
         Object msgcontent=message.getContent();
         //String content=message.getContent().toString(); 

         this.from=(InternetAddress) message.getFrom()[0];
    	 fromEmail = from == null ? null : (from).getAddress();
    	 	
         //****içerik String ise *****  
         if (msgcontent instanceof String)  
         {  
             String body = (String)msgcontent; 
             
             System.out.println("---------------------------------");
        	 System.out.println("Date: " + message.getSentDate());
        	 System.out.println("Subject: " + message.getSubject());
        	 System.out.println("From: " + message.getFrom()[0]);
       		 System.out.println("Text: " + body.toString());
         
         }  
       
          //****içerik multipart ise *****
         else if (msgcontent instanceof Multipart){
        	 multi=true;
        	 Multipart mp = (Multipart)msgcontent;
        	// Multipart mp = (Multipart) message.getContent();
        	 BodyPart bp = mp.getBodyPart(0);
        	 //String regex ="[0-9]";
        	 //Pattern pattern=Pattern.compile(regex);
        	 //Matcher matcher= pattern.matcher(content); //(CharSequence) bp.getContent()
  
         String ayrac="-";
         StringTokenizer st=new StringTokenizer((String) bp.getContent(), ayrac);
         System.out.println("token sayısı: "+st.countTokens());
         String t, s2; int uz, i=0;
         String[] no , isim;
         uz=st.countTokens();
         no=new String[uz]; isim=new String[uz];
         while(st.hasMoreTokens()){
      	     t = st.nextToken().toUpperCase().trim();
    	     ogr.add(t);
         }
         
         for(String s:ogr ){
        	 s2=s;
        	 if(i%2==0){ 
        		 s=s.replaceAll("[^0-9]","");
        		 no[i]=s;
        		 if(i>0){ 
        			 s2=s2.replaceAll("[0-9]","");
        			 isim[i-1]=s2;	 
        		 }
        	 }
        	 else{
        		 s=s.replaceAll("[0-9]","");
        		 isim[i-1]=s;
        		 s2=s2.replaceAll("[^0-9]","");
        		 no[i]=s2;
        	 } 	 
    
    	     i++;
         }
         int grup = db.getGroup()+1;
   
         System.out.println("MAX:  "+grup);
         if(no[0].length()==11) multi=true;
         
         //********Kayıt mevcut mu*********
         for(i=0;i<uz-1;i++){
        	 newOgr=db.existNo(no[i]);
        	 	if(newOgr==false) break;
         }
         //********************************
    		
         for(i=0;i<uz-1;i++){
        	 System.out.println("no:  "+no[i]);
    	     System.out.println("isim:  "+isim[i]);
    	     
    	     if(multi && newOgr){
    	    	 db.insertRow(grup, no[i], isim[i]);
    	    	
    	     }
         }
         if(multi && newOgr)
	    	 se.send();
                     
         //if (matcher.lookingAt()){
         //if(content.matches("")){  //\\.*SYT\\.*[0-9]{10}\\.*
                 
         	 System.out.println("---------------------------------");
        	 System.out.println("Date: " + message.getSentDate());
        	 System.out.println("Subject: " + message.getSubject());
        	 System.out.println("From: " + message.getFrom()[0]);
       		 System.out.println("Text: " + bp.getContent());//message.getContent().toString());bp.getContent()
       		       		
         }//içerik multipart ise
         //}
         
      //}

      //close the store and folder objects
      emailFolder.close(false);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
