package comparevalue_bs;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

public class BlackSholes {


	Rengine re = Rengine.getMainEngine();
	
	    public BlackSholes() {
	        System.out.println("BS create!");
	    }
		
		

	    public void jri(int price) {
	    	

	    	 if(re == null)
	    	   re = new Rengine(new String[] {"--vanilla"}, false, null);
	    	 

			re.eval("source('~/Desktop/rSource/linbs.R')");
			
			//System.out.println(re.assign("s", String.valueOf(price)));
			
			
			re.assign("s", String.valueOf(price));
		
			REXP value =re.eval("as.integer(bsTable=function(s))");
			   int a  = value.asInt();
			        System.out.println("r get "+a);

			        re.end();
	    }


	    
	}

