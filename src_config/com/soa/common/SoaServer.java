package com.soa.common;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;


public class SoaServer {
	
	/**
	 * Zookeeper����ȺIP�б�
	 */
	private String zkServerList="127.0.0.1:2181";
	/**
	 * zkClient�ͻ���
	 */
	private static ZkClient zkClient=null;
	/**
	 * �ַ�����
	 */
	private String charSet="UTF-8";
	/**
	 * ��־��¼
	 */
	protected final static Logger Log = Logger.getLogger(SoaServer.class);
	
	/**
	 * ����Zookeeper����Ⱥ
	 */
	public SoaServer(){
		zkClient=new ZkClient(zkServerList);
		if(zkClient!=null){
			initRoot(); 				//��ʼ��-�����ڵ�ṹ
			initRoute();				//��ʼ��-·�ɹ���
			
			/*initServerListener();		//��ʼ��-�����ṩ�߼���
			initProviderListener();		//��ʼ��-�����߼���
			initConfigDateListener();	//��ʼ��-���ü���*/
		}
	}
	/**
	 * ��ʼ�����÷���ĸ��ڵ�������־ýڵ�
	 */
	private void initRoot(){
		//�����ĳ־ýڵ�
		String root="SOA������������";
		String config="���ýڵ�";
		String server="����ڵ�";
		
		//��Ӧ��Path
		Map<String,String> configMap=new HashMap<String,String>();
		configMap.put("/Config_Center",root);
		configMap.put("/Config_Center/Config",config);
		configMap.put("/Config_Center/Server",server);
		
		//�����ڵ�
		Set<String> keys = configMap.keySet();  
		Iterator<String> iterator = keys.iterator();  
		for (Map.Entry entry : configMap.entrySet()) {  
			   String path = entry.getKey().toString();  
			   String nodeDate = (String) entry.getValue();  
			   boolean isExist=zkClient.exists(path);
				if(!isExist){
					try {
						zkClient.create(path, nodeDate.getBytes("UTF-8"), CreateMode.PERSISTENT);
					} catch (ZkInterruptedException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (ZkException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
					Log.info("��������ڵ�["+path+"]�ɹ�");
				}
		}  
	}
	
	
	/**
	 * ��ʼ��·�ɹ���
	 */
	private void initRoute(){
		//���������ļ���ȡ·������
		String routRules="·�ɹ���";
		try {
			String configPath="/Config_Center/Config/routRules";
			boolean isExist=zkClient.exists(configPath);
			if(!isExist){
				zkClient.create(configPath, routRules.getBytes("UTF-8"), CreateMode.PERSISTENT);
			}
			Log.info("����·�ɹ���ɹ�");
		} catch (Exception e) {
			Log.debug("����·�ɹ���ʧ��");
		}
	}
	/**
	 * ע�����
	 */
	public void registerService(String serviceName,String nodeType,String connetUrl,Object object,IZkChildListener listenter){
		
		String parentPath="/Config_Center/Server/"+serviceName+"/"+nodeType;
		
		boolean isExist=zkClient.exists(parentPath);
		if(!isExist){
			try{
				zkClient.create("/Config_Center/Server/"+serviceName, (serviceName+nodeType).getBytes("UTF-8"), CreateMode.PERSISTENT);
				zkClient.create(parentPath, (serviceName+nodeType).getBytes("UTF-8"), CreateMode.PERSISTENT);
				zkClient.create(parentPath+"/"+connetUrl, object, CreateMode.EPHEMERAL);
				zkClient.subscribeChildChanges(parentPath,listenter);
				Log.info("ע���µķ���ɹ�["+parentPath+"]");
			}catch (Exception e){
				e.printStackTrace();
				Log.debug("ע���µķ���ʧ��["+parentPath+"]",e);
			}
			
		}else{
			zkClient.create(parentPath+"/"+connetUrl, object, CreateMode.EPHEMERAL);
		}
	}
	/**
	 * ��ѯ����
	 */
	public List<String> searchService(String serviceName,String nodeType){
		//��ѯ����
		if(nodeType==null||"".equals(nodeType)){
			nodeType="provider";
		}
		String parentPath="/Config_Center/Server/"+serviceName+"/"+nodeType+"/";
		boolean isExist=zkClient.exists(parentPath);
		List<String> serverList=null;
		if(isExist){
			 serverList=zkClient.getChildren(parentPath);
		}
		return serverList;
	}
	/**
	 * ����ϵͳ����
	 */
	public void writeConfig(String rules){
		//�������ýڵ�
		zkClient.writeData("/Config_Center/Config/routRules", rules);
	}
	/**
	 * ��ȡϵͳ����
	 */
	public  void readConfig(IZkDataListener listener){
		zkClient.subscribeDataChanges("/Config_Center/Config/routRules", listener);
	}
	public String getZkServerList() {
		return zkServerList;
	}
	public void setZkServerList(String zkServerList) {
		this.zkServerList = zkServerList;
	}
	
	
	
	
	
	
	
}
