package com.soa.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
	private String serviceName=null;
	/**
	 * �����ṩ��
	 */
	private final String serviceType="provider";
	/**
	 * ���Ӵ� HOST:PORT
	 */
	private String connectStr=null;
	/**
	 * Ȩ��
	 */
	private String weight=null;
	/**
	 * ����
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * ��־��¼
	 */
	protected final static Logger Log = Logger.getLogger(ProviderServer.class);

	/**
	 * �޲������캯��
	 */
	public ProviderServer(String serviceName){
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip=addr.getHostAddress().toString();
		String connectStr=ip;
		String weight="1";
		this.serviceName=serviceName;
		this.connectStr=connectStr;
		this.weight=weight;
	}
	/**
	 * ���캯��
	 * @param serviceName ��������
	 * @param connectStr  ���Ӵ�
	 * @param weight	     Ȩ��
	 */
	public ProviderServer(String serviceName,String connectStr,String weight){
		this.serviceName=serviceName;
		this.connectStr=connectStr;
		this.weight=weight;
	}
	/**
	 * �����������
	 */
	public void start(){
		
		//ע��ڵ�,����������
		final ProviderServer serverListener=this;
		new Thread(new Runnable(){
			public void run(){
				//��֪�ͻ���
				soa.registerService(serviceName,"consumer",null,null,serverListener);
				//���������ע�����
				soa.registerService(serviceName, serviceType,connectStr,weight,serverListener);
				
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
	
	public static void main(String args[]) throws UnknownHostException{
		
		InetAddress addr=InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();
		String connectStr=ip;
		String serviceName="Server-A";
		String weight="1";
		ProviderServer providerServer=new ProviderServer(serviceName,connectStr,weight);
		providerServer.start();
		
		Log.info("��������ɹ�"+providerServer);
		
		
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getConnectStr() {
		return connectStr;
	}
	public void setConnectStr(String connectStr) {
		this.connectStr = connectStr;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getServiceType() {
		return serviceType;
	}
}
