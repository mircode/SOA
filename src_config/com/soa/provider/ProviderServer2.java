package com.soa.provider;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

/**
 * �����ṩServer
 * @author Administrator
 *
 */
public class ProviderServer2 implements IZkChildListener{
	//����
	private String serviceName="Service-A";
	private String serviceType="provider";
	private String connectStr="3";
	
	/**
	 * ��־��¼
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
		Log.info("���µķ�������====================");
		for(String server:currentChilds){
			Log.info(server);
		}
		Log.info("���µķ�������====================");
	}
	
}
