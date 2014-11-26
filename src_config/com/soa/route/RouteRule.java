package com.soa.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

/**
 * ·���㷨���㷨���ú���ģ��
 * @author Administrator
 *
 */
public class RouteRule {
	
	
	/**
	 * ��־��¼
	 */
	protected final static Logger Log = Logger.getLogger(RouteRule.class);
	
	public static void main(String args[]){
		SoaServer soa=new SoaServer();
		soa.writeConfig("new rules");
		Log.info("�ı�·�ɹ���");
	}
	/**
	 * ��ѯ
	 * @param serverMap ����IP��Ȩ��
	 * @return
	 */
	private static Integer pos=0;
	public String roundRobin(Map<String,Integer> serverMapT){
		//���´���һ��Map,����������ڷ����������ߵ��²�������
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverMapT);
		
		Set<String> keySet=serverMap.keySet();
		ArrayList<String> keyList=new ArrayList<String>();
		keyList.addAll(keySet);
		
		String server=null;
		synchronized(pos){
			if(pos>=keySet.size()){
				pos=0;
			}
		}
		server=keyList.get(pos);
		pos++;
		return server;
	}
	/**
	 * ����㷨
	 * @param serverMap ����IP��Ȩ��
	 * @return
	 */
	public String random(Map<String,Integer> serverMapT){
		//���´���һ��Map,����������ڷ����������ߵ��²�������
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverMapT);
		
		Set<String> keySet=serverMap.keySet();
		ArrayList<String> keyList=new ArrayList<String>();
		keyList.addAll(keySet);
		
		String server=null;
		
		Random random=new Random();
		int randomPos=random.nextInt(keyList.size());
		server=keyList.get(randomPos);
		
		return server;
		
	}
	/**
	 * Դ��ַHash�㷨
	 * @param remoteIp
	 * @return
	 */
	public String consumerHash(Map<String,Integer> serverMapT,String remoteIp){
		//���´���һ��Map,����������ڷ����������ߵ��²�������
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverMapT);
		
		Set<String> keySet=serverMap.keySet();
		ArrayList<String> keyList=new ArrayList<String>();
		keyList.addAll(keySet);
		
		String server=null;
		
		int hashCode=remoteIp.hashCode();
		int serverListSize=keyList.size();
		int serverPos=hashCode%serverListSize;
		
		server=keyList.get(serverPos);
		return server;
		
	}
	/**
	 * ��Ȩ��ѯ�㷨
	 * @return
	 */
	private static Integer pos2=0;
	public String weightRoundRobin(Map<String,Integer> serverMapT){
		//���´���һ��Map,����������ڷ����������ߵ��²�������
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverMapT);
		
		Set<String> keySet=serverMap.keySet();
		Iterator<String> it=keySet.iterator();
		
		ArrayList<String> serverList=new ArrayList<String>();
		while(it.hasNext()){
			String server=it.next();
			Integer weight=serverMap.get(server);
			for(int i=9;i<weight;i++){
				serverList.add(server);
			}
		}
		String server=null;
		synchronized(pos2){
			if(pos2>=keySet.size()){
				pos2=0;
			}
		}
		server=serverList.get(pos2);
		pos2++;
		return server;
		
	}
}
