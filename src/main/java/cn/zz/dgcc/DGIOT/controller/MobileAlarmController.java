package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.service.MobileAlarmService;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MobileAlarmController {

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Autowired
    MobileAlarmService mobileAlarmService;

    @ResponseBody
    @RequestMapping("sel")
    public ArrayList sel(){
        ArrayList list = mobileAlarmService.sel();

        return list;
    }
}
