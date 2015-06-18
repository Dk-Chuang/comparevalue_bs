package comparevalue_bs;

import main.MsgReceiver;
public class Receiver extends MsgReceiver {
	public void execute(String message) {
	    //System.out.println("-----Price INFO:Receive Price data-----");
	    //找mongo的table 並傳到kafka
		MsgFacade.getInstance().distributer(message);
		
	}
}