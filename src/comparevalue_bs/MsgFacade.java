package comparevalue_bs;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import main.KafkaTopicProducer;

public class MsgFacade {
    private static MsgFacade msgFacade = null;

    public static MsgFacade getInstance() {
        return msgFacade == null ? (msgFacade = new MsgFacade()) : msgFacade;
    }

    private MsgFacade() {

    }
    
    int closePrice;
    
    public void distributer(String msg) {
        
        String[] send = this.BSFinder(msg);
        
        
        //丟結果到kafka
        if(send[0] !=null){
        KafkaTopicProducer.getInstance().send("Rmodel", send[0]);
        System.out.println(" send to: Rmodel ,with value:"+send[0]);
       

        //更新最後一筆期貨值
        closePrice = (int)(Math.round(Double.parseDouble(send[1])));
        }
        //System.out.println("the newest close price is :"+closePrice);
    }
    
    public int getcloseprice(){
    	return closePrice;
    }

    private String[] BSFinder(String message) {
        /*
         * msgKey:taiwan_future_MTXU5_1m@1434597107993;8:8843.0;5:8843.0;7:8843.0;Date:1434597107993;9:501.0;6:8854.0;KBAR:taiwan_future_MTXU5_1m_1434597107993;
         * msgKey:taiwan_future_MTXQ5_5s@1434597106591;8:8910.0;5:8910.0;7:8910.0;Date:1434597106591;9:592.0;6:8910.0;KBAR:taiwan_future_MTXQ5_5s_1434597106591;
         * msgKey:taiwan_future_TXF_1s@1433832861806;8:7799;5:8040;7:88;Date:1433832861788;9:376;6:8050;
         * 要拿7為收盤價
         */
    	
    	
        String[] splitedMsg = message.split(";");
        String[] price = splitedMsg[3].split(":");
        String[] date = splitedMsg[4].split(":");
        String product = splitedMsg[0].split("_")[2];
        String[] retMe = new String[2];
        String gettime = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(Long.parseLong(date[1])));
        
        
        //判斷是不是大台指
        if(product.startsWith("TX")){
        	
        	
        	
        //從mongo拿選擇權table資料
        //price上面的價格有小數點 但是mongo裡面沒有 所以要先去掉小數點再搜尋
        int key = (int)(Math.round(Double.parseDouble(price[1])));
        
        String getbs = MongoDBHelper.getInstance().readBSTable(key);
        
        
        if(getbs != "nodata"){
        	
        	String[] splitbs = getbs.split("\\{");
        	
        
        	retMe[0] = "{\"Method\": \"BS\",\"signal\":{\"Key\":"+key+",\"value\":{"+splitbs[3].replaceAll("\\}", "")+"},\"timestamp\":"+gettime+"}}";
        }
        else{
        	
        	retMe[0] = retMe[0] = "{\"Method\": \"BS\",\"signal\":{\"Key\":"+key+",\"value\":\"Key: "+key+" not found in mongo\",\"timestamp\":"+gettime+"}}";
        }
        
        }
        
        retMe[1] = price[1];

        
        return retMe;
    }
}