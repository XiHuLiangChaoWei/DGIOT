package cn.zz.dgcc.DGIOT;

import cn.zz.dgcc.DGIOT.entity.*;
import cn.zz.dgcc.DGIOT.mapper.OilMapper;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg4AnalysisN2;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.ControlOrderCommondBuilder;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.GasInfoCommondBuilder;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.GrainInfoCommondBuilder;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.ModelCommondBuilder;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/5/8 9:19
 * ClassExplain :
 * ->
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DgiotApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class Test1 {
    @Autowired
    IoTService ioTService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    DepotService depotService;
    @Autowired
    N2Service n2Service;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    OilService oilService;

    @Test
    public void login() {
        User u = userService.Login("6000", "admin");
        System.err.println(u);
    }

    @Test
    public void save() {
        List<Product> products = ioTService.getProductList();
        List<Device> devices = ioTService.getDeviceList(products);
        deviceService.saveDeviceList(devices);
    }


    @Test
    public void saveOil() {
        Oil o = new Oil("testDev", new Date(), "AASSFFGG");
        int rs = oilService.saveOil(o);
        System.out.println(rs);
    }


    @Test
    public void grain() {
        GrainInfoCommondBuilder grainInfoCommondBuilder = GrainInfoCommondBuilder.getGrainInfoCommondBuilder();
        grainInfoCommondBuilder.setDevBH("02");
        grainInfoCommondBuilder.setDevZH("03");
        String con = grainInfoCommondBuilder.build().toString();
        System.err.println("test = " + con);
    }

    @Test
    public void sendModel() {
        int a = 1;
        System.err.println("模式" + a);
        DecimalFormat df = new DecimalFormat("00");
        String model1 = df.format(a);
        ModelCommondBuilder mcb = ModelCommondBuilder.getModelCommonBuilder();
        mcb.setModel(model1);
        mcb.setDevNote("");
        mcb.setDevBH("01");
        mcb.setDevZH("01");
        mcb.setAction("11");
        mcb.setTargetCC("63");
        BuildMessage buildMessage = mcb.build();
        System.err.println(buildMessage.toString());
    }

    @Test
    public void sendOrder() {
        ControlOrderCommondBuilder coc = ControlOrderCommondBuilder.getInstance();
        coc.setDevBH("02");
        coc.setDevZH("02");
        coc.setDevDZ("08");
        coc.setDevBHDZ("01");
        BuildMessage buildMessage = coc.build();
        System.err.println(buildMessage);
        //AA55F70602020801111AFFFFCB75EFEF
    }

    @Test
    public void testAnalysis() {
        String msg = "AA B0 FF FF FF FF FF FF 00 01 4A 01 49 01 4B 01 4B 01 49 01 4A 01 4A 01 49 01 46 01 49 01 4A 01 48 01 49 01 47 01 49 01 45 01 44 01 47 01 4B 01 45 01 48 01 48 01 46 01 4B 01 4A 01 46 01 43 01 42 01 42 01 46 01 40 01 48 01 43 01 44 01 42 01 43 01 38 01 45 01 42 01 43 01 4D 01 47 01 4B 01 46 01 47 01 43 01 4B 01 46 01 4D 01 48 01 45 01 46 01 46 01 48 01 42 01 40 01 47 01 41 01 44 01 45 01 49 01 4B 01 48 01 4C 01 36 01 4A 01 47 01 47 01 21 01 4A 01 24 01 1F 01 45 01 48 01 22 01 49 01 1E 01 44 01 21 01 45 01 42 01 48 01 3C 01 47 01 45 01 48 01 4B 01 45 01 3D 01 3E 01 3A 01 39 01 4A 01 35 01 3A 01 37 01 2B 01 26 01 45 01 45 01 4D 01 4E 01 4B 01 4A 01 4B 01 48 01 4A 01 4E 01 43 01 4C 01 47 01 4B 01 45 01 46 01 4A 01 44 01 42 01 43 01 3F 01 48 01 48 01 4A 01 46 01 4A 01 48 01 46 01 45 01 46 01 45 01 46 01 48 01 42 01 4A 01 47 01 47 01 45 01 49 01 47 01 44 01 42 01 4A 01 B3 00 BF 00 FF FF 4A 01 9C 00 C5 00 FF FF 49 01 C4 00 AB 00 FF FF 3E 01 9F 00 A2 00 FF FF 32 01 9D 00 9D 00 FF FF 8D 01 A8 00 BC 00 FF FF 3E 01 A2 00 B1 00 FF FF 36 01 A8 00 AB 00 FF FF 3F 01 AD 00 A3 00 FF FF 30 01 A9 00 9D 00 FF FF 66 01 E2 00 C0 00 FF FF 6F 01 DE 00 C2 00 FF FF 50 01 F0 00 DF 00 FF FF 64 01 16 01 C5 00FF FF 6E 01 D5 00 CD 00 FF FF 62 01 FE 00 D3 00 FF FF 6C 01 F9 00 CD 00 FF FF 7B 01 06 01 D1 00 FF FF 6A 01 1A 01 C9 00 FF FF 80 01 B4 00 98 00 FF FF 69 01 E9 00 DB 00 FF FF 6C 01 E2 00 DB 00 FF FF 68 01 E6 00 C8 00 FF FF 63 01 F1 00 D1 00 FF FF 5B 01 D5 00 C3 00 FF FF 5D 01 03 01 E0 00 FF FF A2 01 2B 01 D2 00 FF FF 4B 01 06 01 C4 00 FF FF 6D 01 1D 01 F6 00 FF FF 67 01 CA 00 BD 00 FF FF 56 01 BC 00 C5 00 FF FF 66 01 DC 00 C4 00 FF FF 6A 01 E0 00 C5 00 FF FF 5A 01 D5 00 BF 00 FF FF 64 01 D3 00 B8 00 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 24 00 F7 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 00 02 91 E9 EF EF04 07 FB F8 FF FF A1 5EA5 04 07 FB F8 FF FF A1 5EA5 04 07 FB F8 FF FF A1 5E";
        String content = msg.replace(" ", "");
        JSONObject js = null;
        Depot depot = depotService.getDepotByDevName("LQ000001");
        Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
        try {
//            js = dg3AnalysisGrain.analysis(content, depot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println(js.toJSONString());
    }

    @Test
    public void N21() {
        String devName = "QT000001";
        Depot depot = depotService.getDepotByDevName(devName);
        N2 n2 = n2Service.getNewInfoByDevName(devName);
        String content = n2.getContent();
        Dg4AnalysisN2 dg4AnalysisN2 = Dg4AnalysisN2.newInstance();
        dg4AnalysisN2.analysisN2Info(n2, devName, depot);
    }

    @Test
    public void or() {
        Order o = orderService.getNewOn1("030600230001B822");
        System.err.println(o.getMessageContent());
    }
}
