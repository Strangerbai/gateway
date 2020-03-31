package com.test.gateway.service.model;


public interface ThreadLocalConstant {
	//请求路径
	String REQUEST_CONTENT = "request_content";

	String REQUEST_METHOD = "request_method";

	String URI = "uri";

	String HOST = "host";

	String REQUEST_HEADER = "request_header";

	String COOKIE = "cookie";

	String RESPONSE_CODE = "response_code";

	String RESPONSE_HEADER = "response_header";

	String RESPONSE_CONTENT = "response_content";

	String FORWARDED_IP = "x_forwarded_for";

	String SERVICE = "service";

}
