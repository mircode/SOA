package com.soa.consumer;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
	private String serviceName=null;
	/**
	 * 服务消费者
	 */
	private  final String serviceType="consumer";
	/**
	 * 链接串 客户端IP
	 */
	private String connectStr=null;
	/**
	 * 单例
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(ConsumerServer.class);
	/**
	 * 无参构造方法
	 */
	public ConsumerServer(){
		
	}
	/**
	 * 构造函数
	 * @param serviceName 服务名称
	 * @param connectStr  链接串
	 */
	public ConsumerServer(String serviceName,String connectStr){
		this.serviceName=serviceName;
		this.connectStr=connectStr;
	}
	/**
	 * 服务启动入口
	 */
	public void start(){
		//注册节点,并启动监听
		final ConsumerServer serverListener=this;
		new Thread(new Runnable(){
			public void run(){
				//感知服务端变化
				soa.registerService(serviceName,"provider",null,null,serverListener);
				//感知路由规则变化
				soa.readConfig(serverListener);
				//向服务中线注册消费者
				soa.registerService(serviceName,serviceType,connectStr,null,serverListener);
				
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
	}
	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		
	}
	public Map<String,Integer> searchService(String serviceName,String serviceType){
		 List<String> currentChildsList=soa.searchService(serviceName,serviceType);
		 
		 Map<String,Integer> serverListMap=new  HashMap<String,Integer>();
		 
		 for(String server:currentChildsList){
			 String weight=(String) soa.readData(server);
			 Integer w=(weight==null?1:Integer.parseInt(weight));
			 serverListMap.put(server,w);
		 }
		 return serverListMap;
	}
	public static void main(String args[]) throws UnknownHostException{
		
		InetAddress addr=InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();
		String connectStr=ip;
		String serviceName="Server-A";
		
		ConsumerServer consumerServer=new ConsumerServer(serviceName,connectStr);
		consumerServer.start();
		Log.info("启动客户端成功"+consumerServer);
		
	}
}
