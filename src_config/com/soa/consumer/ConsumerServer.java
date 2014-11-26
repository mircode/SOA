package com.soa.consumer;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

public class ConsumerServer implements IZkChildListener,IZkDataListener{
	/**
	 * �����������
	 */
	private String serviceName="Service-A";
	/**
	 * ����������
	 */
	private  final String serviceType="consumer";
	/**
	 * ���Ӵ� �ͻ���IP
	 */
	private String connectStr="01";
	/**
	 * ����
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * ��־��¼
	 */
	protected final static Logger Log = Logger.getLogger(ConsumerServer.class);
	
	public ConsumerServer(){
		//��֪����˱仯
		soa.registerService(serviceName,"provider",null,null,this);
		//��֪·�ɹ���仯
		soa.readConfig(this);
		//���������ע��������
		soa.registerService(serviceName,serviceType,connectStr,null,this);
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds)
			throws Exception {
		if(parentPath.indexOf(serviceType)>-1){
			Log.info("�ͻ���-��ǰ�б�");
			//���÷������ĵĲ�ѯ�ӿڲ�ѯ����
			List<String> currentChildsList=soa.searchService(serviceName,serviceType);
			for(String server:currentChildsList){
				Log.info(server);
			}
		}else{
			Log.info("�����-��ǰ�б�");
			//���÷������ĵĲ�ѯ�ӿڲ�ѯ����
			List<String> currentChildsList=soa.searchService(serviceName,"provider");
			for(String server:currentChildsList){
				Log.info(server);
			}
		}
	}
	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		Log.info("·�ɹ������仯:"+data);
	}
	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		
	}
	
	public static void main(String args[]){
		ConsumerServer consumerServer=new ConsumerServer();
		Log.info("�����ͻ��˳ɹ�"+consumerServer);
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
