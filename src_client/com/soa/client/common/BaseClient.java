package com.soa.client.common;


/**
 * ����̬�Ķ�̬ע���·��
 * @author Administrator
 *
 */
public class BaseClient {

	/**
	 * ���߷������ƺͷ�������ȡ��Ӧ�ķ������
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public Object getService(String className,String methodName,Class clazz){
		
		
		//����-�ӷ���->�ṩ��(������)->IP(����·��):Э������:���Ӵ�
		//��->����->Э������:���Ӵ�
		
		//�����������Ҷ�Ӧ�ķ������
		//���߷��������Ҷ�Ӧ���ӷ���
		
		//���ض�Ӧ��Э�����ͺ����Ӵ�
		 //String connentStr=ConsumerServer.search();
		//���߲�ͬЭ��Ĳ�ͬ���͵��ò�ͬ����
		
		//�����WebService�ӿ�
		//�����Xfire
		
		//�����Socket�ӿڷ���Socket����
		return null;
	}
	//ͳһ���ýӿ�
	public Object invoke(Object... args){
		return null;
	}
	//ͳһ���ýӿ�(��Ԥ����)
	public Object invokeVirtual(Object... args){
		return null;
	}
	//HTTP�������л���ؽӿ�
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
	//BIN�������л���ؽӿ�
	public Object Byte2Object(Byte args[]){
		return null;
	}
	public Byte[] Object2Byte(Object args){
		return null;
	}
}
