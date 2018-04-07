package com.hsbank.util.java.http.constant;

import com.hsbank.util.java.collection.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Http_状态码_常量类
 * <p/>
 * 完整的 HTTP 1.1规范说明书来自于RFC 2616。
 * HTTP 1.1的状态码被标记为新特性，因为许多浏览器只支持 HTTP 1.0。
 * 支持协议版本可以通过调用request.getRequestProtocol来检查。
 * <p/>
 * 这些状态码被分为五大类： 
 * 100-199 用于指定客户端应相应的某些动作。 
 * 200-299 用于表示请求成功。 
 * 300-399 用于已经移动的文件并且常被包含在定位头信息中指定新的地址信息。 
 * 400-499 用于指出客户端的错误。 
 * 500-599 用于支持服务器错误。 
 * </p>
 * @author arthur_xie
 * 2013-09-10
 */
public class HttpStatusConstant {
	//1xx
	public static final int CODE_100 = 100;
    public static final String TEXT_100 = "Continue/继续(HTTP 1.1)";
    public static final int CODE_101 = 101;
    public static final String TEXT_101 = "Switching Protocols/转换协议(HTTP 1.1)";
    //2xx
    public static final int CODE_200 = 200;
    public static final String TEXT_200 = "OK/正常";
    public static final int CODE_201 = 201;
    public static final String TEXT_201 = "Created/已创建";
    public static final int CODE_202 = 202;
    public static final String TEXT_202 = "Accepted/接受";
    public static final int CODE_203 = 203;
    public static final String TEXT_203 = "Non-Authoritative Information/非官方信息(HTTP 1.1)";
    public static final int CODE_204 = 204;
    public static final String TEXT_204 = "No Content/无内容";
    public static final int CODE_205 = 205;
    public static final String TEXT_205 = "Reset Content/重置内容(HTTP 1.1)";
    public static final int CODE_206 = 206;
    public static final String TEXT_206 = "Partial Content/局部内容(HTTP 1.1)";
    //3xx
    public static final int CODE_300 = 300;
    public static final String TEXT_300 = "Multiple Choices/多重选择";
    public static final int CODE_301 = 301;
    public static final String TEXT_301 = "Moved Permanently/永久移动";
    public static final int CODE_302 = 302;
    public static final String TEXT_302 = "Found/找到";
    public static final int CODE_303 = 303;
    public static final String TEXT_303 = "See Other/参见其他信息(HTTP 1.1)";
    public static final int CODE_304 = 304;
    public static final String TEXT_304 = "Not Modified/没有修改";
    public static final int CODE_305 = 305;
    public static final String TEXT_305 = "Use Proxy/使用代理(HTTP 1.1)";
    public static final int CODE_307 = 307;
    public static final String TEXT_307 = "Temporary Redirect/临时重定向(HTTP 1.1)";
    //4xx
    public static final int CODE_400 = 400;
    public static final String TEXT_400 = "Bad Request/错误请求";
    public static final int CODE_401 = 401;
    public static final String TEXT_401 = "Unauthorized/未授权";
    public static final int CODE_403 = 403;
    public static final String TEXT_403 = "Forbidden/禁止";
    public static final int CODE_404 = 404;
    public static final String TEXT_404 = "Not Found/未找到";
    public static final int CODE_405 = 405;
    public static final String TEXT_405 = "Method Not Allowed/方法未允许(HTTP 1.1)";
    public static final int CODE_406 = 406;
    public static final String TEXT_406 = "Not Acceptable/无法访问(HTTP 1.1)";
    public static final int CODE_407 = 407;
    public static final String TEXT_407 = "Proxy Authentication Required/代理服务器认证要求(HTTP 1.1)";
    public static final int CODE_408 = 408;
    public static final String TEXT_408 = "Request Timeout/请求超时(HTTP 1.1)";
    public static final int CODE_409 = 409;
    public static final String TEXT_409 = "Conflict/冲突(HTTP 1.1)";
    public static final int CODE_410 = 410;
    public static final String TEXT_410 = "Gone/已经不存在(HTTP 1.1)";
    public static final int CODE_411 = 411;
    public static final String TEXT_411 = "Length Required/需要数据长度(HTTP 1.1)";
    public static final int CODE_412 = 412;
    public static final String TEXT_412 = "Precondition Failed/先决条件错误(HTTP 1.1)";
    public static final int CODE_413 = 413;
    public static final String TEXT_413 = "Request Entity Too Large/请求实体过大(HTTP 1.1)";
    public static final int CODE_414 = 414;
    public static final String TEXT_414 = "Request URI Too Long/请求URI过长(HTTP 1.1)";
    public static final int CODE_415 = 415;
    public static final String TEXT_415 = "Unsupported Media Type/不支持的媒体格式(HTTP 1.1)";
    public static final int CODE_416 = 416;
    public static final String TEXT_416 = "Requested Range Not Satisfiable/请求范围无法满足(HTTP 1.1)";
    public static final int CODE_417 = 417;
    public static final String TEXT_417 = "Expectation Failed/期望失败(HTTP 1.1)";
    //5xx
    public static final int CODE_500 = 500;
    public static final String TEXT_500 = "Internal Server Error/内部服务器错误";
    public static final int CODE_501 = 501;
    public static final String TEXT_501 = "Not Implemented/未实现";
    public static final int CODE_502 = 502;
    public static final String TEXT_502 = "Bad Gateway/错误的网关";
    public static final int CODE_503 = 503;
    public static final String TEXT_503 = "Service Unavailable/服务无法获得";
    public static final int CODE_504 = 504;
    public static final String TEXT_504 = "Gateway Timeout/网关超时(HTTP 1.1)";
    public static final int CODE_505 = 505;
    public static final String TEXT_505 = "HTTP Version Not Supported/不支持的 HTTP 版本(HTTP 1.1)";

    /**
     * 把上面定义的值都放到一个集合里面去
     * @return			集合
     */
    public static Map<String, Object> getMap() {
    	Map<String, Object> resultValue = new HashMap<String, Object>();
    	resultValue.put(CODE_100 + "", TEXT_100);
    	resultValue.put(CODE_101 + "", TEXT_101);
    	resultValue.put(CODE_200 + "", TEXT_200);
    	resultValue.put(CODE_201 + "", TEXT_201);
    	resultValue.put(CODE_202 + "", TEXT_202);
    	resultValue.put(CODE_203 + "", TEXT_203);
    	resultValue.put(CODE_204 + "", TEXT_204);
    	resultValue.put(CODE_205 + "", TEXT_205);
    	resultValue.put(CODE_206 + "", TEXT_206);
    	resultValue.put(CODE_300 + "", TEXT_300);
    	resultValue.put(CODE_301 + "", TEXT_301);
    	resultValue.put(CODE_302 + "", TEXT_302);
    	resultValue.put(CODE_303 + "", TEXT_303);
    	resultValue.put(CODE_304 + "", TEXT_304);
    	resultValue.put(CODE_305 + "", TEXT_305);
    	resultValue.put(CODE_307 + "", TEXT_307);
    	resultValue.put(CODE_400 + "", TEXT_400);
    	resultValue.put(CODE_401 + "", TEXT_401);
    	resultValue.put(CODE_403 + "", TEXT_403);
    	resultValue.put(CODE_404 + "", TEXT_404);
    	resultValue.put(CODE_405 + "", TEXT_405);
    	resultValue.put(CODE_406 + "", TEXT_406);
    	resultValue.put(CODE_407 + "", TEXT_407);
    	resultValue.put(CODE_408 + "", TEXT_408);
    	resultValue.put(CODE_409 + "", TEXT_409);
    	resultValue.put(CODE_410 + "", TEXT_410);
    	resultValue.put(CODE_411 + "", TEXT_411);
    	resultValue.put(CODE_412 + "", TEXT_412);
    	resultValue.put(CODE_413 + "", TEXT_413);
    	resultValue.put(CODE_414 + "", TEXT_414);
    	resultValue.put(CODE_415 + "", TEXT_415);
    	resultValue.put(CODE_416 + "", TEXT_416);
    	resultValue.put(CODE_417 + "", TEXT_417);
    	resultValue.put(CODE_500 + "", TEXT_500);
    	resultValue.put(CODE_501 + "", TEXT_501);
    	resultValue.put(CODE_502 + "", TEXT_502);
    	resultValue.put(CODE_503 + "", TEXT_503);
    	resultValue.put(CODE_504 + "", TEXT_504);
    	resultValue.put(CODE_505 + "", TEXT_505);
    	return resultValue;
    }
    
    /**
     * 得到状态描述
     * @param code		状态代码
     * @return			状态描述
     */
    public static String getText(int code) {
    	return MapUtil.getString(getMap(), code + "");
    }
}
