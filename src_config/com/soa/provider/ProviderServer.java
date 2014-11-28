package com.soa.provider;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.apache.log4j.Logger;

import com.soa.common.SoaServer;

/**
 * 服务提供Server
 * @author Administrator
 *
 */
public class ProviderServer implements IZkChildListener{
	
	/**
	 * 所属服务分类
	 */
	private String serviceName=null;
	/**
	 * 服务提供者
	 */
	private final String serviceType="provider";
	/**
	 * 链接串 HOST:PORT
	 */
	private String connectStr=null;
	/**
	 * 权重
	 */
	private String weight=null;
	/**
	 * 单例
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(ProviderServer.class);

	/**
	 * 无参数构造函数
	 */
	public ProviderServer(String serviceName){
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip=addr.getHostAddress().toString();
		String connectStr=ip;
		String weight="1";
		this.serviceName=serviceName;
		this.connectStr=connectStr;
		this.weight=weight;
	}
	/**
	 * 构造函数
	 * @param serviceName 服务名称
	 * @param connectStr  链接串
	 * @param weight	     权重
	 */
	public ProviderServer(String serviceName,String connectStr,String weight){
		this.serviceName=serviceName;
		this.connectStr=connectStr;
		this.weight=weight;
	}
	/**
	 * 服务启动入口
	 */
	public void start(){
		
		//注册节点,并启动监听
		final ProviderServer serverListener=this;
		new Thread(new Runnable(){
			public void run(){
				//感知客户端
				soa.registerService(serviceName,"consumer",null,null,serverListener);
				//向服务中线注册服务
				soa.registerService(serviceName, serviceType,connectStr,weight,serverListener);
				
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 设置服务监听
	 */
	@Override
	public void handleChildChange(String parentPath,
			List<String> currentChilds) throws Exception {
		if(parentPath.indexOf(serviceType)>-1){
			Log.info("服务端-当前列表");
			//调用服务中心的查询接口查询服务
			List<String> currentChildsList=soa.searchService(serviceName,serviceType);
			for(String server:currentChildsList){
				Log.info(server);
			}
		}else{
			Log.info("客户端-当前列表");
			//调用服务中心的查询接口查询服务
			List<String> currentChildsList=soa.searchService(serviceName,"consumer");
			for(String server:currentChildsList){
				Log.info(server);
			}
		}
	}
	
	public static void main(String args[]) throws UnknownHostException{
		
		InetAddress addr=InetAddress.getLocalHost();
		String ip=addr.getHostAddress().toString();
		String connectStr=ip;
		String serviceName="Server-A";
		String weight="1";
		ProviderServer providerServer=new ProviderServer(serviceName,connectStr,weight);
		providerServer.start();
		
		Log.info("启动服务成功"+providerServer);
		
		
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getConnectStr() {
		return connectStr;
	}
	public void setConnectStr(String connectStr) {
		this.connectStr = connectStr;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getServiceType() {
		return serviceType;
	}
}
