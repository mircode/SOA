package com.soa.common;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;


public class SoaServer {
	
	/**
	 * Zookeeper服务集群IP列表
	 */
	private String zkServerList="127.0.0.1:2181";
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
		if(zkClient!=null){
			initRoot(); 				//初始化-基本节点结构
			initRoute();				//初始化-路由规则
		}
	}
	/**
	 * 初始化配置服务的根节点和其他持久节点
	 */
	@SuppressWarnings("unchecked")
	private void initRoot(){
		//创建的持久节点
		String root="SOA服务配置中心";
		String config="配置节点";
		String server="服务节点";
		
		//对应的Path
		Map<String,String> configMap=new HashMap<String,String>();
		configMap.put("/Config_Center",root);
		configMap.put("/Config_Center/Config",config);
		configMap.put("/Config_Center/Server",server);
		
		//创建节点
		for (Map.Entry entry : configMap.entrySet()) {  
			   String path = entry.getKey().toString();  
			   String nodeDate = (String) entry.getValue();  
			   boolean isExist=zkClient.exists(path);
				if(!isExist){
					try {
						zkClient.create(path, nodeDate.getBytes(charSet), CreateMode.PERSISTENT);
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
					Log.info("创建服务节点["+path+"]成功");
				}
		}  
	}
	
	
	/**
	 * 初始化路由规则
	 */
	private void initRoute(){
		//加载配置文件读取路由配置
		String routRules="路由规则";
		try {
			String configPath="/Config_Center/Config/routRules";
			boolean isExist=zkClient.exists(configPath);
			if(!isExist){
				zkClient.create(configPath, routRules.getBytes(charSet), CreateMode.PERSISTENT);
			}
			Log.info("加载路由规则成功");
		} catch (Exception e) {
			Log.debug("加载路由规则失败");
		}
	}
	/**
	 * 注册服务(服务提供者和消费者)
	 * 
	 * @param serviceName 服务名称
	 * @param nodeType	     节点类型 
	 * @param connetUrl   连接串
	 * @param object	     节点数据
	 * @param listenter   服务节点监听
	 * 
	 */
	public void registerService(String serviceName,String nodeType,String connetUrl,Object object,IZkChildListener listenter){
		
		String parentPath="/Config_Center/Server/"+serviceName+"/"+nodeType;
		String registerPath=parentPath+"/"+connetUrl;
		
		//判断是否节点服务类型是否创建过
		boolean isExist=zkClient.exists(parentPath);
		if(!isExist){
				
			//创建服务持久父节点,并绑定监听事件
			zkClient.createPersistent(parentPath, true);
			zkClient.subscribeChildChanges(parentPath,listenter);
			Log.info("绑定节点监听事件成功["+parentPath+"]");
			
			if(connetUrl!=null){
				//创建按临时子节点
				zkClient.create(registerPath,object,CreateMode.EPHEMERAL);
				Log.info("注册新的服务成功["+parentPath+"]");
			}
		}else{
			
			//绑定事件
			zkClient.subscribeChildChanges(parentPath,listenter);
			Log.info("绑定节点监听事件成功["+parentPath+"]");
			
			if(connetUrl!=null){
				//如果存在父节点直接挂载在父节点下
				zkClient.create(registerPath,object,CreateMode.EPHEMERAL);
				
				Log.info("注册新的服务成功["+parentPath+"]");
			}
		}
	}
	/**
	 * 查询服务列表
	 * @param serviceName
	 * @param nodeType
	 * @return
	 */
	public List<String> searchService(String serviceName,String nodeType){
		//查询服务
		if(nodeType==null||"".equals(nodeType)){
			nodeType="provider";
		}
		String parentPath="/Config_Center/Server/"+serviceName+"/"+nodeType;
		boolean isExist=zkClient.exists(parentPath);
		List<String> serverList=null;
		if(isExist){
			 serverList=zkClient.getChildren(parentPath);
		}
		return serverList;
	}
	/**
	 * 读取某个节点数据
	 * @param path 节点路径
	 * @return
	 */
	public Object readData(String path){
		Object obj=zkClient.readData(path, true);
		return obj;
	}
	/**
	 * 查询服务列表
	 * @param serviceName
	 * @param nodeType
	 * @return
	 */
	public List<String> searchService(String serviceName){
		return this.searchService(serviceName, null);
	}
	/**
	 * 更新系统配置
	 */
	public void writeConfig(String rules){
		//创建配置节点
		zkClient.writeData("/Config_Center/Config/routRules", rules);
	}
	/**
	 * 读取系统配置
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
