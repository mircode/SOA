package com.soa.provider;

import com.soa.common.SoaServer;

/**
 * �����ṩServer
 * @author Administrator
 *
 */
public class ProviderServer extends SoaServer{
	//����
	public static SoaServer soa=new SoaServer();
	private String serviceName;
	private String serviceType;
	private String connectStr;

	public static void main(String args){
		//ע�����
		soa.registerService();
	}
	
}
