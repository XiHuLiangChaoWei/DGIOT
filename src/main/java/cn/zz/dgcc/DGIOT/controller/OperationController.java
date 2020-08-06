package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.VO.ProjectVO;
import cn.zz.dgcc.DGIOT.service.impl.ProjectServiceImpl;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by: LT001
 * Date: 2020/7/21 14:27
 * ClassExplain : 提供新建项目的便捷操作
 * ->
 */
@Controller
@RequestMapping("operation")
public class OperationController extends BaseController {
    @Autowired
    ProjectServiceImpl projectService;

    @RequestMapping("addProject")
    public String a() {
        return "html/addProject";
    }

    @ResponseBody
    @RequestMapping("addP")
    public JsonResult<String> addproject(ProjectVO projectVO) {
        System.err.println(projectVO.toString());
        //向项目表添加项目
        JsonResult<String> jr = projectService.addP(projectVO);
        return jr;
//    return new JsonResult<>(success,"?" );
    }
}
