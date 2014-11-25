package com.soa.common;

import java.io.UnsupportedEncodingException;

import org.I0Itec.zkclient.*;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

public class SoaServer {
	
	/**
	 * Zookeeper服务集群IP列表
	 */
	private String zkServerList;
	/**
	 * zkClient客户端
	 */
	private static ZkClient zkClient=null;
	/**
	 * 字符编码
	 */
	private String charSet="UTF-8";
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(SoaServer.class);
	/**
	 * 链接Zookeeper服务集群
	 */
	public SoaServer(){
		zkClient=new ZkClient(zkServerList);
	}
	/**
	 * 创建永久节点的服务目录
	 */
	public void init(){
		//加载配置文件读取路由配置
		String routRules="路由规则";
		try {
			String configPath="/Config_Center/Config";
			boolean isExist=zkClient.exists(configPath);
			if(!isExist){
				zkClient.create(configPath, routRules.getBytes("UTF-8"), CreateMode.PERSISTENT);
			}
			Log.info("加载路由规则成功");
		} catch (Exception e) {
			Log.debug("加载路由规则失败");
		}
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
