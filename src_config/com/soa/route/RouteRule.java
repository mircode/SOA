package com.soa.route;

import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

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
}
