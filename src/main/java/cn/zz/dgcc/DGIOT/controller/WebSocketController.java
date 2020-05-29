package cn.zz.dgcc.DGIOT.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by: YYL
 * Date: 2020/4/21 13:51
 * ClassExplain :
 * ->
 */
@Controller
public class WebSocketController extends BaseController {
    @ResponseBody
    @RequestMapping("/iot/date")
    public String getDates(String message) {
        return null;
    }
}
