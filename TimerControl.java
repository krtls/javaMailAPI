package net.pack.mail;

import java.util.Timer;
import java.util.TimerTask;
 
public class TimerControl {
		
       static int i=0;
       public static void main(final String[] args) {
    	   	 final ReadingEmail re = new ReadingEmail();	
             final Timer myTimer=new Timer();
             TimerTask task =new TimerTask() {
             @Override
                    public void run() {  
                          /* i++; 
                           if(i==5)
                                  myTimer.cancel();
                                  */
                    	re.read();
                    	System.out.println("---------------");
                    }
             };
 
           myTimer.schedule(task,0,3000); // 10 dakikada bir 600000
       }
}