package cn.zz.dgcc.DGIOT.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

import java.io.*;
import java.util.*;

/**
 * Created by: YYL
 * Date: 2020/6/2 9:29
 * ClassExplain :
 * ->
 */
public class FileUtils {
//    public static void main(String[] args) throws IOException {
//        String url = "F:/GIT/A101.bin";
//        File file = getFile(url);
//        Map map = getFileSplit(file);
//        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
//        Iterator<Map.Entry<Integer, byte[]>> it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<Integer, byte[]> entry = it.next();
//            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//        }
//    }

//    public static void main(String[] args) throws IOException {
//        String url = "F:/GIT/A101.bin";
//        File file = getFile(url);
//        JSONArray ja = getFileSplit(file);
//        for (int i = 0; i < ja.size(); i++) {
//            JSONObject jo = ja.getJSONObject(i);
//            int index = jo.getInteger("index");
//            int sum = jo.getInteger("sum");
//            byte[] bytes = jo.getBytes("fileData");
//            System.out.println("index = " + index);
//            System.out.println("sum = " + sum);
//            System.out.println("bytes = " + bytes);
//        }
//        byte[] a = new byte[]{11, 11, 22, 33, 44};
//        byte[] c = fixWith0(a);
//        System.out.println(new String(c));
//        for (byte d:c
//             ) {
//            byteToInt(d);
//        }
//        String str = "AA554499223331FF";
//        byte[] mid = null;
//        for (int i = 0; i < str.length() / 2; i++) {
//            System.out.println("---------------------------------------------------------");
//            System.out.println(str.substring(i * 2, i * 2 + 2));
//            System.out.println(Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16));
//            System.out.println((byte) (Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16)));
//        }
//        System.err.println(byteToInt((byte)-1));
//    }

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
//            System.out.println(Integer.toHexString(i));
//            System.out.println(i1);
        }
        System.out.println("总数" + sum);
        return sum;
    }



    public static JSONArray getFileSplit(File file) throws IOException {
        JSONArray ja = new JSONArray();
        JSONObject jo;
        Map<Integer, byte[]> tar = new HashMap<>();
        FileInputStream fis = null;
        //文件大小
        long length = file.length();
        System.err.println(length);

        byte[] fileData = null;
        //打开文件流
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        int len = 0;
        int index = 1;
        fileData = new byte[1024];
        while ((len = fis.read(fileData)) != -1) {
//            System.err.println(len);
//            System.err.println(new String(fileData));
//            if(len!=1024){
//                fixWith0(fileData);
//            }
            System.out.println("--------------------------" + index + "---------------------------");
            int sum = getNum(fileData);
            tar.put(index, fixWith0(fileData));
            jo = new JSONObject();
            jo.put("sum", sum);
            jo.put("index", index);
            jo.put("fileData", fixWith0(fileData));
            ja.add(jo);
            index++;
        }
        if (fis != null)
            fis.close();
        return ja;
    }

    public static byte[] fixWith0(byte[] bytes) {
        byte[] newA = Arrays.copyOf(bytes, 1024);
        Arrays.fill(newA, bytes.length, newA.length, (byte) -1);
        return newA;
    }
}
