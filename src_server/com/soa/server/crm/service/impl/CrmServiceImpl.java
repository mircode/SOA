package com.soa.server.crm.service.impl;

import com.soa.client.crm.service.CrmService;

public class CrmServiceImpl implements CrmService{

	public String invoke(String args) {
		String result="<INFO>Hello World!<INFO>";
		return result;
	}

}
