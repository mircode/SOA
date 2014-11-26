package com.soa.provider;

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
	private String serviceName="Service-A";
	/**
	 * 服务提供者
	 */
	private final String serviceType="provider";
	/**
	 * 链接串 HOST:PORT
	 */
	private String connectStr="4";
	/**
	 * 权重
	 */
	private String weight="18";
	/**
	 * 单例
	 */
	private static SoaServer soa=new SoaServer();
	/**
	 * 日志记录
	 */
	protected final static Logger Log = Logger.getLogger(ProviderServer.class);

	
	public ProviderServer(){
		//感知客户端
		soa.registerService(serviceName,"consumer",null,null,this);
		//向服务中线注册服务
		soa.registerService(serviceName, serviceType,connectStr,weight,this);
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
	
	public static void main(String args[]){
		
		ProviderServer providerServer=new ProviderServer();
		Log.info("启动服务成功"+providerServer);
		
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
