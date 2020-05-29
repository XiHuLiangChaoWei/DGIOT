package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

/**
 * Created by: YYL
 * Date: 2020/5/6 10:00
 * ClassExplain :
 * ->
 */
public class BytesUtil {

//    public static void main(String[] args) {
//        BytesUtil bytesUtil = new BytesUtil();
//        Float a = Hex2Float("41 9D 9F B4");
//    }
    /**
     * HEX转Float
     * @param string
     * @return
     */
    public static Float Hex2Float(String string){
        String msg = string.replace(" ","");
        Float tar = Float.intBitsToFloat(Integer.valueOf(msg,16)) ;
        System.err.println(tar);
        return tar;
    }


    /**
     * bytes输出十进制
     *
     * @param bytes
     * @return
     */
    public static Integer bytesToInt(byte[] bytes) {
        String str = "";
        int i = 0;
        boolean flag = true && bytes.length > 0;
        while (flag) {
            Byte b = bytes[i];
            i++;
            Integer bi = Byte.toUnsignedInt(b);
            str += toHexString(bi);
            if (i >= bytes.length || str.endsWith("EEEE"))
                flag = false;
        }
        return Integer.parseInt(str, 16);
    }

    /**
     * bytes输出十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToString(byte[] bytes) {
        String str = "";
        int i = 0;
        boolean flag = true && bytes.length > 0;
        while (flag) {
            Byte b = bytes[i];
            i++;
            Integer bi = Byte.toUnsignedInt(b);
            str += toHexString(bi);
            if (i >= bytes.length)
                flag = false;
        }
        return str;
    }

    /**
     * 十六进制转字节
     *
     * @param hex
     * @return
     */
    public static byte hexToByte(Integer hex) {
        return hex.byteValue();
    }

    /**
     * short转2字节 已调整高低位
     *
     * @param number
     * @return
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * 二进制子串转16进制子串
     *
     * @param bin
     * @return
     */
    public static String binToHex(String bin) {
        String hexStr = "";
        int size = bin.length() / 8;
        for (int i = 0; i < size; i++) {
            String value = bin.substring(i * 8, (i + 1) * 8);
            int temp = Integer.parseInt(value, 2);
            String tempHex = Integer.toHexString(temp);
            hexStr += tempHex;
        }
        return hexStr;
    }

    /**
     * 转为十六进制串，不足2位补0
     *
     * @param value
     * @return
     */
    public static String toHexString(int value) {
        String tempHex = Integer.toHexString(value);
        if (tempHex.length() < 2) {
            tempHex = "0" + tempHex;
        }
        return tempHex.toUpperCase();
    }

    public static String getTargetId(String value, boolean isTwo) {
        String tempHex = Integer.toHexString(Integer.valueOf(value));
        if (tempHex.length() < 2) {
            tempHex = "0" + tempHex;
        }
        if (isTwo) {
            if (tempHex.length() < 4) {
                tempHex = "0" + tempHex;
            }
            if (tempHex.length() < 4) {
                tempHex = "0" + tempHex;
            }
        }
        return tempHex.toUpperCase();
    }

    public static String getOrderId(int value) {
        String tempHex = Integer.toHexString(value);
        if (tempHex.length() < 2) {
            tempHex = "0" + tempHex;
        }
        if (tempHex.length() < 4) {
            tempHex = "0" + tempHex;
        }
        if (tempHex.length() < 4) {
            tempHex = "0" + tempHex;
        }
        return tempHex.toUpperCase();
    }

    /**
     * 将value转为same个相同满8位的二进制字串
     *
     * @param value
     * @param same
     * @return
     */
    public static String toBinary8StringSame(int value, int same) {
        String tempBinStr = toBinary8String(value);
        String rsBinStr = "";
        for (int i = 0; i < same; i++) {
            rsBinStr += tempBinStr;
        }
        return rsBinStr;
    }

    /**
     * 不足width个字节宽度时，前面补0至width*8
     *
     * @param value
     * @param width
     * @return
     */
    public static String toBinary8String(int value, int width) {
        String tempBinStr = toBinary8String(value);
        int size = tempBinStr.length();
        for (int i = 0; i < width; i++) {
            int temp = (i + 1) * 8;
            if (temp > size) {
                tempBinStr = "00000000" + tempBinStr;
                size = tempBinStr.length();
            }
        }
        return tempBinStr;
    }

    /**
     * 补足8位
     *
     * @param value
     * @return
     */
    public static String toBinary8String(int value) {
        String temp = Integer.toBinaryString(value);
        if (value == 0) {
            temp = "00000000";
        }
        int length = temp.length();
        while (length < 8) {
            temp = "0" + temp;
            length = temp.length();
        }
        return temp;
    }

    public static String toEmptyBinaryWidthString(int width) {
        String str = "";
        for (int i = 0; i < width; i++) {
            str += "00000000";
        }
        return str;
    }

    public static byte[] emptyBytes(int bytes) {
        byte[] bys = new byte[bytes];
        for (int i = 0; i < bytes; i++) {
            bys[i] = emptyByte();
        }
        return bys;
    }

    public static byte emptyByte() {
        Integer b0 = 0x00;
        return b0.byteValue();
    }

    public static byte binToBytes(String bin) {
        return Integer.valueOf(bin, 2).byteValue();
    }

    public static byte[] binaryStrToBytes(String binStr) {
        int size = binStr.length() / 8;
        byte[] bs = new byte[size];
        for (int i = 0; i < size; i++) {
            String temp = binStr.substring(i * 8, (i + 1) * 8);
            bs[i] = Integer.valueOf(temp, 2).byteValue();
        }
        return bs;
    }

    /**
     * 十六进制串转字节数组
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStrToBytes(String hexStr) {
        int size = hexStr.length() / 2;
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            String tmp = hexStr.substring(i * 2, (i + 1) * 2);
            //将tmp字符串以十六进制解析
            Integer tmpHex = Integer.parseInt(tmp, 16);
            //以byte类型返回该 Integer 的值。只取低八位的值，高位不要。
            bytes[i] = tmpHex.byteValue();
        }
        return bytes;
    }

    /**
     * 两字节高低位装换
     *
     * @param b
     * @return
     */
    public static byte[] hexHeightLow(byte[] b) {
        byte[] b2 = new byte[2];
        //转换高低位
        b2[0] = b[1];
        b2[1] = b[0];
        return b2;
    }

    /**
     * 16进制转换为10进制
     *
     * @param strHex
     * @return
     */
    public static int hexToInt(String strHex) {
        short s = (short) (Integer.valueOf(strHex, 16) & 0xffff);
        return s;
    }

    /**
     * int转16进制字符串 2位 00 00
     *
     * @param num
     * @return
     */
    public static String intToHexStr(int num) {
        //需要使用2字节表示b
        return String.format("%04x", num);
    }

    // 计算16进制对应的数值
    public static int GetHex(char ch) throws Exception {
        if (ch >= '0' && ch <= '9')
            return ch - '0';
        if (ch >= 'a' && ch <= 'f')
            return ch - 'a' + 10;
        if (ch >= 'A' && ch <= 'F')
            return ch - 'A' + 10;
        throw new Exception("error param");
    }

    // 计算幂
    public static int GetPower(int nValue, int nCount) throws Exception {
        if (nCount < 0)
            throw new Exception("nCount can't small than 1!");
        if (nCount == 0)
            return 1;
        int nSum = 1;
        for (int i = 0; i < nCount; ++i) {
            nSum = nSum * nValue;
        }
        return nSum;
    }

//    public static void main(String[] args) {
//        int id = 0;
//        System.out.println(BytesUtil.getOrderId(id));
//        System.out.println(BytesUtil.getTargetId(id + "", true));
//        System.out.println(hexToInt("FF6A"));
//        System.out.println(intToHexStr(-150));
//        //System.out.println(hexStrToBytes("0024"));
//        String msg = "0000000000090010405D00050A0001012C012C00040006";
//        byte[] order = hexStrToBytes(msg);
//        byte[] temp = hexStrToBytes(intToHexStr(-150));
//        order[order.length - 4] = temp[2];
//        order[order.length - 3] = temp[3];
//        System.out.println(bytesToString(order));
//    }

    /**
     * 16进制字符串 高低换位
     *
     * @param info
     * @return
     */
    public static String tran_LH(String info) {
        return info.substring(2) + info.substring(0, 2);
    }

    /**
     * 16进制字符串 高低换位 8个字符
     *
     * @param info
     * @return
     */
    public static String tran_LH8(String info) {
        return tran_LH(info.substring(4)) + tran_LH(info.substring(0, 4));
    }
}
