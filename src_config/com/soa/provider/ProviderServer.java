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
public class ProviderServer implements IZkChildListener{
	
	/**
	 * �����������
	 */
	private String serviceName="Service-A";
	/**
	 * �����ṩ��
	 */
	private final String serviceType="provider";
	/**
	 * ���Ӵ� HOST:PORT
	 */
	private String connectStr="4";
	/**
	 * Ȩ��
	 */
	private String weight="18";
	/**
	 * ����
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * ��־��¼
	 */
	protected final static Logger Log = Logger.getLogger(ProviderServer.class);

	
	public ProviderServer(){
		//��֪�ͻ���
		soa.registerService(serviceName,"consumer",null,null,this);
		//���������ע�����
		soa.registerService(serviceName, serviceType,connectStr,weight,this);
	}
	/**
	 * ���÷������
	 */
	@Override
	public void handleChildChange(String parentPath,
			List<String> currentChilds) throws Exception {
		if(parentPath.indexOf(serviceType)>-1){
			Log.info("�����-��ǰ�б�");
			//���÷������ĵĲ�ѯ�ӿڲ�ѯ����
			List<String> currentChildsList=soa.searchService(serviceName,serviceType);
			for(String server:currentChildsList){
				Log.info(server);
			}
		}else{
			Log.info("�ͻ���-��ǰ�б�");
			//���÷������ĵĲ�ѯ�ӿڲ�ѯ����
			List<String> currentChildsList=soa.searchService(serviceName,"consumer");
			for(String server:currentChildsList){
				Log.info(server);
			}
		}
	}
	
	public static void main(String args[]){
		
		ProviderServer providerServer=new ProviderServer();
		Log.info("��������ɹ�"+providerServer);
		
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
