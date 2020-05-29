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

}
