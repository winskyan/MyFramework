package com.my.library_base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    protected static char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public final static String MD5(String pwd) {
        //用于加密的字符
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();

            // 获得指定摘要算法的 MessageDigest对象，此处为MD5
            //MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。 
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //System.out.println(mdInst);  
            //MD5 Message Digest from SUN, <initialized>

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);
            //System.out.println(mdInst);  
            //MD5 Message Digest from SUN, <in progress>

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();
            //System.out.println(md);

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            //System.out.println(j);
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { //  i = 0
                byte byte0 = md[i]; //95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; //    5  
                str[k++] = md5String[byte0 & 0xf]; //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    //protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getMD5String(byte[] buffer) throws IOException {
        messagedigest.update(buffer, 0, buffer.length);
        return bufferToHex(messagedigest.digest());
    }

    public static String getFileMD5String(File file) throws IOException {
        if (messagedigest != null) {
            messagedigest.reset();
        }
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }


    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = md5String[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换  
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同  
        char c1 = md5String[bt & 0xf];// 取字节中低 4 位的数字转换  
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}
