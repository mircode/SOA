package com.soa.provider;

import com.soa.common.SoaServer;

/**
 * 服务提供Server
 * @author Administrator
 *
 */
public class ProviderServer extends SoaServer{
	//单例
	public static SoaServer soa=new SoaServer();
	private String serviceName;
	private String serviceType;
	private String connectStr;

	public static void main(String args){
		//注册服务
		soa.registerService();
	}
	
}
