package comparevalue_bs;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.rosuda.JRI.Rengine;

public class timer {

	 Timer timer;
	    //每天17:00執行一次
	    public void Timer(){
	        Date time = getTime();
	        System.out.println("start R time=" + time);
	        timer = new Timer();
	        timer.schedule(new Task(), time ,5000);
	    }
	    
	    public Date getTime(){
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.HOUR_OF_DAY, 17);
	        calendar.set(Calendar.MINUTE, 00);
	        calendar.set(Calendar.SECOND, 00);
	        Date time = calendar.getTime();
	        
	        return time;
	    }

	public class Task extends TimerTask{

		
	    @Override
	    public void run() {
	    	//拿最後一筆期貨價格
	    	int closePrice = MsgFacade.getInstance().getcloseprice();
	    	//啟動R Blacksholes
	    	BlackSholes bs = new BlackSholes();
	        bs.jri(closePrice);
	      
	    }

	}
	
}
