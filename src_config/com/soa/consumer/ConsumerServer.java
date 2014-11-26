package com.soa.consumer;

import java.util.List;
import java.util.Random;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;
import com.soa.provider.ProviderServer;

public class ConsumerServer implements IZkChildListener,IZkDataListener{
	
	private String serviceName="Service-A";
	private String serviceType="consumer";
	private String connectStr="0";
	private List serverList=null;
	private String routeRouls=null;
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(ConsumerServer.class);
	private SoaServer soa=new SoaServer();
	
	public List search(){
		return serverList;
	}
	public void rigist(){
		 soa.registerService(serviceName,serviceType,connectStr,"", new IZkChildListener(){

			@Override
			public void handleChildChange(String parentPath,
					List<String> currentChilds) throws Exception {
				Log.info("有新的客户端连接====================");
				for(String server:currentChilds){
					Log.info(server);
				}
				Log.info("有新的客户端连接====================");
				
			}
			 
			 
		 });
		 soa.registerService(serviceName,"provider","","", this);
	}
	
	public static void main(String args[]){
		
		ConsumerServer consumerServer=new ConsumerServer();
		System.out.println(consumerServer.search());
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds)
			throws Exception {
		serverList=currentChilds;
		System.out.println(currentChilds);
		
	}
	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		routeRouls=data.toString();
		System.out.println("系统规则发生变化"+routeRouls);
		
	}
	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
