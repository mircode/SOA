package com.soa.consumer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

public class ConsumerServer implements IZkChildListener,IZkDataListener{
	/**
	 * �����������
	 */
	private String serviceName=null;
	/**
	 * ����������
	 */
	private  final String serviceType="consumer";
	/**
	 * ���Ӵ� �ͻ���IP
	 */
	private String connectStr=null;
	/**
	 * ����
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * ��־��¼
	 */
	protected final static Logger Log = Logger.getLogger(ConsumerServer.class);
	/**
	 * �޲ι��췽��
	 */
	public ConsumerServer(){
		
	}
	/**
	 * ���캯��
	 * @param serviceName ��������
	 * @param connectStr  ���Ӵ�
	 */
	public ConsumerServer(String serviceName,String connectStr){
		this.serviceName=serviceName;
		this.connectStr=connectStr;
	}
	/**
	 * �����������
	 */
	public void start(){
		//ע��ڵ�,����������
		final ConsumerServer serverListener=this;
		new Thread(new Runnable(){
			public void run(){
				//��֪����˱仯
				soa.registerService(serviceName,"provider",null,null,serverListener);
				//��֪·�ɹ���仯
				soa.readConfig(serverListener);
				//���������ע��������
				soa.registerService(serviceName,serviceType,connectStr,null,serverListener);
				
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
	public Map<String,Integer> searchService(String serviceName,String serviceType){
		 List<String> currentChildsList=soa.searchService(serviceName,serviceType);
		 
		 Map<String,Integer> serverListMap=new  HashMap<String,Integer>();
		 
		 for(String server:currentChildsList){
			 String weight=(String) soa.readData(server);
			 Integer w=(weight==null?1:Integer.parseInt(weight));
			 serverListMap.put(server,w);
		 }
		 return serverListMap;
	}
	public static void main(String args[]) throws UnknownHostException{
		
		InetAddress addr=InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();
		String connectStr=ip;
		String serviceName="Server-A";
		
		ConsumerServer consumerServer=new ConsumerServer(serviceName,connectStr);
		consumerServer.start();
		Log.info("�����ͻ��˳ɹ�"+consumerServer);
		
	}
}
