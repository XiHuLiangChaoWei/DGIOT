package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.Device;

import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.service.DgN2Service;
import cn.zz.dgcc.DGIOT.service.UserService;
import cn.zz.dgcc.DGIOT.utils.JsonResult;


import com.aliyuncs.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/dgn2")
public class DgN2Controller extends BaseController {

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    DgN2Service dgN2Service;
    @Autowired
    UserService userService;


    @ResponseBody
    @RequestMapping("getsel")
    public JsonResult<List<Device>> getsel(HttpSession session, @RequestBody String param) {

        int state=200;

        //从session获当前用户的id
        int userId = getUserIdFromSession(session);
        //获取userId对应的城市id
        User user = userService.getByUid(userId);
        String companyId = String.valueOf(user.getCompanyId());

        //单个
        List list1 = dgN2Service.getcompanyid(companyId,param);

        //获取全部
        List list= dgN2Service.getsel();
        if(list1.size()!=0){
            state=2000;
        }


        return new JsonResult<>(state, list,list1);
    }


}
