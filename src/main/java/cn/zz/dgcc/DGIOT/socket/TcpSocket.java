package cn.zz.dgcc.DGIOT.socket;

import cn.zz.dgcc.DGIOT.utils.SocketUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpSocket extends Thread {
    Socket clientSocket;

    public TcpSocket(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        //设置5分钟超时
        try {
            this.clientSocket.setSoTimeout(5*60*1000);

            //获取客户端来的数据
            char[] a = new char[100];
            String str = "";
            while(true){
                BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                in.read(a);
                str = new String(a);
                //清除数组中多余的内容
                    str = str.replace("\0","");

                System.out.println("received message:"+str);
                //心跳指令干扰，必须保证收到login信息
                if(str.contains("login")){
                    break;
                }else{
                   try {
                       this.clientSocket.close();
                   }catch (IOException e){
                       System.out.println("connection closed!");
                   }
                }
            }
            //返回信息有login，登录成功
            if(str.contains("login")){
//                String sn = "";
//                JSONObject jo = JSONObject.parseObject(str);
//                sn = jo.getString("sn");//设备编号

                while (true){
                    //发送指令
//                    byte[] s = SocketUtil.hexStrToBinaryStr("010300012342C40B");
//                    this.clientSocket.getOutputStream().write(s);
//                    //接受信息
//                    byte[] rsByte = new byte[100];
//                    this.clientSocket.getInputStream().read(rsByte);
//                    String recMsg = SocketUtil.BinaryToHexString(rsByte);

                    // 以02 02 03开头的信息为数据信息
//                    if(recMsg.startsWith("02 02 03")){
//                        // 清除16进制字符串中的空格
//                        recMsg = recMsg.replace(" ", "");
//                        // 截取出温度和湿度
//                        String temperatureHex = recMsg.substring(6, 10);
//                        String humidityHex = recMsg.substring(10, 14);
//
//                        System.out.println(str + " / " + "Tem:" + temperatureHex + " / " + "Hum:" + humidityHex);
//                    }
                    // 每次休眠1秒钟
                    Thread.sleep(1000);
                }
            }

        } catch (Exception e) {
            System.out.println("发生错误:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("服务已启动， 等待连接！");
        //建立TCP连接服务,绑定端口
        ServerSocket tcpServer = new ServerSocket(5252);
        //接受连接,每个TCP连接都是一个java线程
        while(true){
            Socket clientSocket = tcpServer.accept();
            new TcpSocket(clientSocket).start();
        }
    }
}

