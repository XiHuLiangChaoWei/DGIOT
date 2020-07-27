package cn.zz.dgcc.DGIOT.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

import java.io.*;
import java.util.*;

/**
 * Created by: LT001
 * Date: 2020/6/2 9:29
 * ClassExplain :   文件工具
 * ->
 */
public class FileUtils {
    /**
     * @param url 通过url读取硬盘上的文件
     * @return
     */
    public static File getFile(String url) {
        File file = new File(url);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static int byteToInt(byte b) {
//        System.out.println("byte 是:" + b);
        int x = b & 0xff;
//        System.out.println("int 是:" + x);
        return x;
    }


    public static int getNum(byte[] b) {
        int sum = 0;
        for (byte i : b
        ) {
            int i1 = byteToInt(i);
            sum += i1;
        }
        return sum;
    }

//    public static void main(String[] args) throws IOException {
//        File file = getFile("D:/update/Fireware/cbe962a3-d7d1-4555-b105-17118ee7b010.bin");
//        JSONArray jr = getFileSplit(file);
//        for (int i = 0; i < jr.size(); i++) {
//            JSONObject jo = jr.getJSONObject(i);
//            byte[] bytes = jo.getBytes("fileData");
////            System.out.println(bytes.toString());
//            System.out.println(jo.toJSONString());
//            System.out.println(jo.getInteger("sum"));
//        }
//    }

    /**
     *
     * @param file 文件
     * @return 将文件分割成1024字节的分包 并打包成json
     * @throws IOException
     */
    public static JSONArray getFileSplit(File file) throws IOException {
        JSONArray ja = new JSONArray();
        JSONObject jo;
        FileInputStream fis = null;
        //文件大小
        long length = file.length();
//        System.err.println(length);
        byte[] fileData;
        //打开文件流
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        int len = 0;
        int index = 0;
        fileData = new byte[1024];
        //每1024个字节封装成一个jsonObject对象
        while ((len = fis.read(fileData)) != -1) {
//            System.err.println(len);
//            System.err.println(new String(fileData));
//            if(len!=1024){
//                fixWith0(fileData);
//            }
//            System.out.println("--------------------------" + index + "---------------------------");
            jo = new JSONObject();
            byte[] r = null;
            jo.put("index", index);
            //最后一包，不足1024字节的，用FF填充
            if ((length - index * 1024) < 1024) {
                r=fixWith0(fileData, (int) (length - index * 1024));
            } else {
                r= fixWith0(fileData, 1024);
            }
            int sum = getNum(r);
            jo.put("fileData", r);
            jo.put("sum", sum);
            jo.put("length", length);
            ja.add(jo);
            index++;
        }
        if (fis != null)
            fis.close();
        return ja;
    }

    public static byte[] fixWith0(byte[] bytes, int len) {
        byte[] newA = Arrays.copyOf(bytes, len);
        byte[] newB = Arrays.copyOf(newA, 1024);
        Arrays.fill(newB, len, 1024, (byte) -1);
//        System.err.println(Arrays.toString(newB));
        return newB;
    }
}
