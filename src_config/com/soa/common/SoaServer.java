package com.soa.common;

public class SoaServer {
	
	/**
	 * Zookeeper服务集群IP列表
	 */
	private String zkServerList;
	
	/**
	 * 链接Zookeeper服务集群
	 */
	public SoaServer(){
		
	}
	
	/**
	 * 注册服务
	 */
	public void registerService(){
		
	}
	/**
	 * 查询服务
	 */
	public void searchService(){
		//查询服务
		//读取路由配置
		//返回链接串
	}
	/**
	 * 更新系统配置
	 */
	public void writeConfig(){
		//创建配置节点
	}
	/**
	 * 读取系统配置
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
