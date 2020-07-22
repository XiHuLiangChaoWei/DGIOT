package cn.zz.dgcc.DGIOT.controller;

import javax.servlet.http.HttpSession;

import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
public abstract class BaseController {
	protected final int success = 200;
	protected final int badRequest = 400;
	protected final int notFound = 404;
	protected final int servWrong = 500;
	protected final int reDirect = 300;
	protected final int upload = 600;



	protected final static int 通风设备 = 1;
	protected final static int 气调设备 = 2;
	protected final static int 粮情设备 = 3;
	protected final static int 环流设备 = 4;
	protected final static int 制氮设备 = 5;
	protected final static int 油情设备 = 6;
	protected final static int 太阳能分机设备 = 7;
	protected final static int 测试设备 = 0;


	//括号内限制异常类型
	@ExceptionHandler({ISqlException.class})
	public JsonResult<Void> handleException(Throwable e){
		JsonResult<Void> jr = new JsonResult<Void>(e);
		jr.setState(servWrong);
		return jr;	
	}

	public Integer getUserIdFromSession(HttpSession session) {
		Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
		return userId;	
	}
	public String getUserNameFromSession(HttpSession session) {
		String modifiedUser = session.getAttribute("username").toString();
		return modifiedUser;	
	}

	public Integer getCompanyIdFromSession(HttpSession session){
		Integer companyId = Integer.valueOf(session.getAttribute("company").toString());
		return companyId;
	}

}
