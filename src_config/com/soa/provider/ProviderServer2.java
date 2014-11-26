package com.soa.provider;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

/**
 * 服务提供Server
 * @author Administrator
 *
 */
public class ProviderServer2 implements IZkChildListener{
	//单例
	private String serviceName="Service-A";
	private String serviceType="provider";
	private String connectStr="3";
	
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(ProviderServer.class);
	
	private SoaServer soa=new SoaServer();
	
	public void register(){
		 soa.registerService(serviceName, serviceType,connectStr,"18", this);
	}
	

	public static void main(String args[]){
		ProviderServer providerServer=new ProviderServer();
		providerServer.register();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void handleChildChange(String parentPath,
			List<String> currentChilds) throws Exception {
		Log.info("有新的服务上线====================");
		for(String server:currentChilds){
			Log.info(server);
		}
		Log.info("有新的服务上线====================");
	}
	
}
