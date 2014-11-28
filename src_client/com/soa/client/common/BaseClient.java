package com.soa.client.common;


/**
 * 服务动态的动态注册和路由
 * @author Administrator
 *
 */
public class BaseClient {

	/**
	 * 更具服务名称和方法名获取对应的服务对象
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public Object getService(String className,String methodName,Class clazz){
		
		
		//服务-子服务->提供者(消费者)->IP(用于路由):协议类型:连接串
		//类->方法->协议类型:连接串
		
		//更具类名查找对应的服务分组
		//更具方法名查找对应的子服务
		
		//返回对应的协议类型和链接串
		 //String connentStr=ConsumerServer.search();
		//更具不同协议的不同类型调用不同方法
		
		//如果是WebService接口
		//则调用Xfire
		
		//如果是Socket接口返回Socket对象
		return null;
	}
	//统一调用接口
	public Object invoke(Object... args){
		return null;
	}
	//统一调用接口(用预测是)
	public Object invokeVirtual(Object... args){
		return null;
	}
	//HTTP对象序列化相关接口
	public Object Json2Object(String args){
		return null;
	}
	public String Object2Json(Object args){
		return null;
	}
	public Object Xml2Object(String args){
		return null;
	}
	public String Object2Xml(Object args){
		return null;
	}
	//BIN对象序列化相关接口
	public Object Byte2Object(Byte args[]){
		return null;
	}
	public Byte[] Object2Byte(Object args){
		return null;
	}
}
