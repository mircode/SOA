package com.soa.consumer;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

public class ConsumerServer implements IZkChildListener,IZkDataListener{
	/**
	 * 所属服务分类
	 */
	private String serviceName="Service-A";
	/**
	 * 服务消费者
	 */
	private  final String serviceType="consumer";
	/**
	 * 链接串 客户端IP
	 */
	private String connectStr="01";
	/**
	 * 单例
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(ConsumerServer.class);
	
	public ConsumerServer(){
		//感知服务端变化
		soa.registerService(serviceName,"provider",null,null,this);
		//感知路由规则变化
		soa.readConfig(this);
		//向服务中线注册消费者
		soa.registerService(serviceName,serviceType,connectStr,null,this);
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds)
			throws Exception {
		if(parentPath.indexOf(serviceType)>-1){
			Log.info("客户端-当前列表");
			//调用服务中心的查询接口查询服务
			List<String> currentChildsList=soa.searchService(serviceName,serviceType);
			for(String server:currentChildsList){
				Log.info(server);
			}
		}else{
			Log.info("服务端-当前列表");
			//调用服务中心的查询接口查询服务
			List<String> currentChildsList=soa.searchService(serviceName,"provider");
			for(String server:currentChildsList){
				Log.info(server);
			}
		}
	}
	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		Log.info("路由规则发生变化:"+data);
		
		 String sourceCode=(String)data;
	     System.out.println(sourceCode);
	     GroovyClassLoader groovyClassLoader=new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
		 Class<?> groovyClass=groovyClassLoader.parseClass(sourceCode);
		 GroovyObject groovyObject=(GroovyObject)groovyClass.newInstance();
			
		 Map<String,Integer> serverListMap=new HashMap<String,Integer>();
		 List<String> currentChildsList=soa.searchService(serviceName,serviceType);
		 for(String server:currentChildsList){
			 serverListMap.put(server, 1);
		 }
		 String server=(String)groovyObject.invokeMethod("execute", serverListMap);
		 System.out.println(server);
	}
	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		
	}
	
	public static void main(String args[]){
		ConsumerServer consumerServer=new ConsumerServer();
		Log.info("启动客户端成功"+consumerServer);
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
