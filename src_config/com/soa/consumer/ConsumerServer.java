package com.soa.consumer;

import com.soa.common.SoaServer;

public class ConsumerServer extends SoaServer{
	
	private String serviceName;
	private String serviceType;
	private String connectStr;
	public ConsumerServer(String serviceName){
		
	}
	
	public static String search(){
		return null;
	}
	public static void main(String args[]){
		SoaServer soa=new SoaServer();
		soa.searchService();
		
	}
}
