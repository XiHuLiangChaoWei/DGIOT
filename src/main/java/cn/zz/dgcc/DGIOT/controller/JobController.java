package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.QuartzJob;
import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.service.JobService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by: YYL
 * Date: 2019/11/8 11:07
 * ClassExplain :
 * ->
 */
@Controller
@RestController
@RequestMapping("/job")
public class JobController {
    private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Resource
    private JobService jobServiceImpl;

    //定时任务
    @RequestMapping("/Quartz.html")
    public ModelAndView JobView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("com/common/jobView");
        return mav;
    }

    //新增配置
    @RequestMapping("/test.html")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("com/common/test");
        return mav;
    }

    //新增仓库
    @RequestMapping("/warehouse.html")
    public ModelAndView warehouse() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("com/common/warehouse");
        return mav;
    }

    //修改配置
    @RequestMapping("/updateQuartz.html")
    public ModelAndView updateQuartz(String jobID) {
        ModelAndView mav = new ModelAndView();
        QuartzJob quartzJob = jobServiceImpl.selectQuartzJobById(jobID);
        mav.addObject("quartz", quartzJob);
        mav.setViewName("com/common/test");
        return mav;
    }

    //删除配置
    @ResponseBody
    @RequestMapping("/deleteQuartz.do")
    public String deleteQuartz(String jobID) {
        JSONObject json = new JSONObject();
        QuartzJob quartzJob = jobServiceImpl.selectQuartzJobById(jobID);

        try {
            jobServiceImpl.deleteJob(quartzJob);
            json.put("flag", "01");
            json.put("msg", "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "00");
            json.put("msg", "操作失败!");
        }
        return json.toString();
    }


    //启动配置
    @ResponseBody
    @RequestMapping("/openQuartz.do")
    public String openQuartz(String jobID) {
        JSONObject json = new JSONObject();
        QuartzJob quartzJob = jobServiceImpl.selectQuartzJobById(jobID);

        try {
            jobServiceImpl.JobStart(quartzJob);
            json.put("flag", "01");
            json.put("msg", "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "00");
            json.put("msg", "操作失败!");
        }
        return json.toString();
    }

    //暂停配置
    @ResponseBody
    @RequestMapping("/pauseQuartz.do")
    public String pauseQuartz(String jobID) {
        JSONObject json = new JSONObject();
        QuartzJob quartzJob = jobServiceImpl.selectQuartzJobById(jobID);

        try {
            jobServiceImpl.pauseJob(quartzJob);
            json.put("flag", "01");
            json.put("msg", "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "00");
            json.put("msg", "操作失败!");
        }
        return json.toString();
    }

    /**
     * 获取已保存任务
     * get all quartz from DataBase
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/demo/table/user.do")
    public String table(Integer page, Integer limit) {

        PageInfo<QuartzJob> quartzJobs = jobServiceImpl.selectAll(page, limit);
        List<QuartzJob> list = quartzJobs.getList();
        JSONObject json = new JSONObject();
        json.put("code", "0");
        json.put("msg", "");
        json.put("count", quartzJobs.getTotal());
        json.put("data", JSONArray.toJSON(list));
        System.err.println(json.toString());
        return json.toString();
    }

    /**
     * 新增任务--
     * @param quartzJob quartzJob对象
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveQuartz.do")
    public String save(QuartzJob quartzJob, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("新增任务");
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute("User");

        quartzJob.setUpdatePeople(user.getUserName());

        try {
            System.err.println(quartzJob.getCron());
            jobServiceImpl.addJob(quartzJob);
            json.put("flag", "01");
            json.put("msg", "操作成功");
        } catch (RuntimeException e) {
            json.put("flag", "00");
            json.put("msg", "操作失败");
        }
        return json.toString();
    }

    /**
     * 修改任务--
     * @param quartzJob
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateQuartz.do")
    public String updateQuartz(QuartzJob quartzJob, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("修改任务");
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute("User");

        QuartzJob quar = jobServiceImpl.selectQuartzJobById(quartzJob.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String format = sdf.format(new Date());
        quar.setUpdateTime(format);
        quar.setUpdatePeople(user.getUserName());

        quar.setSchemeName(quartzJob.getSchemeName());
        quar.setSchemeType(quartzJob.getSchemeType());
        quar.setWeek(quartzJob.getWeek());
        quar.setTimeDate(quartzJob.getTimeDate());
        quar.setHour(quartzJob.getHour());
        quar.setMinute(quartzJob.getMinute());
        quar.setSetTime(quartzJob.getSetTime());
        quar.setRemark(quartzJob.getRemark());
        try {
            System.err.println(quartzJob.getCron());
            jobServiceImpl.updateJob(quar);
            json.put("flag", "01");
            json.put("msg", "操作成功");
        } catch (RuntimeException | SchedulerException e) {
            e.printStackTrace();
            json.put("flag", "00");
            json.put("msg", "操作失败");
        }
        return json.toString();
    }


}
