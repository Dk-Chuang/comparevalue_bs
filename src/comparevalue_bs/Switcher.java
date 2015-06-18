package comparevalue_bs;
import java.util.Map;

import main.GlobalConfigMap;
import main.KafkaTopicConsumer;

public class Switcher{
	public static void main(String[] args){
	    System.out.println("-----CheckBS active !-----");
		Receiver r = new Receiver();
		GlobalConfigMap config = new GlobalConfigMap();
		Map<String, String> zkMap = config.getGlobalConfigMap();
		//Topic name = Price
		//大lab：140.119.19.230:2181,140.119.19.232:2181,140.119.19.238:2181
		KafkaTopicConsumer ktc = new KafkaTopicConsumer(zkMap.get("ZookeeperURL"), "Pricetestbs", "Price", r);
		
		//每天五點啟動R
		 //new timer().Timer();
	}
}
