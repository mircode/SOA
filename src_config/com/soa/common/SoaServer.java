package com.soa.common;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.*;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

public class SoaServer {
	
	/**
	 * Zookeeper����ȺIP�б�
	 */
	private String zkServerList;
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
	}
	/**
	 * �������ýڵ�ķ���Ŀ¼
	 */
	public void init(){
		//���������ļ���ȡ·������
		String routRules="·�ɹ���";
		try {
			String configPath="/Config_Center/Config";
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
	public void registerService(){
		
	}
	/**
	 * ��ѯ����
	 */
	public void searchService(){
		//��ѯ����
		//��ȡ·������
		//�������Ӵ�
	}
	/**
	 * ����ϵͳ����
	 */
	public void writeConfig(){
		//�������ýڵ�
	}
	/**
	 * ��ȡϵͳ����
	 */
	public void readConfig(){
		
	}
	public String getZkServerList() {
		return zkServerList;
	}
	public void setZkServerList(String zkServerList) {
		this.zkServerList = zkServerList;
	}
	
	
	
	
	
	
	
}
