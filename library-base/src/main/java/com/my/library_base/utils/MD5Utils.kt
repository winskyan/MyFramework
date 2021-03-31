package com.my.library_base.utils

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Utils {
    internal var md5String = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    fun MD5(pwd: String): String? {
        //用于加密的字符
        return try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            val btInput = pwd.toByteArray()

            // 获得指定摘要算法的 MessageDigest对象，此处为MD5
            //MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。 
            val mdInst = MessageDigest.getInstance("MD5")
            //System.out.println(mdInst);  
            //MD5 Message Digest from SUN, <initialized>

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput)
            //System.out.println(mdInst);  
            //MD5 Message Digest from SUN, <in progress>

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            val md = mdInst.digest()
            //System.out.println(md);

            // 把密文转换成十六进制的字符串形式
            val j = md.size
            //System.out.println(j);
            val str = CharArray(j * 2)
            var k = 0
            for (i in 0 until j) { //  i = 0
                val byte0 = md[i] //95
                str[k++] = md5String[byte0.toInt() ushr 4 and 0xf] //    5
                str[k++] = md5String[byte0.toInt() and 0xf] //   F
            }

            //返回经过加密后的字符串
            String(str)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    //protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    internal var messagedigest: MessageDigest? = null

    @Throws(IOException::class)
    fun getMD5String(buffer: ByteArray): String {
        messagedigest!!.update(buffer, 0, buffer.size)
        return bufferToHex(messagedigest!!.digest())
    }

    @Throws(IOException::class)
    fun getFileMD5String(file: File?): String {
        if (messagedigest != null) {
            messagedigest!!.reset()
        }
        val fis: InputStream
        fis = FileInputStream(file)
        val buffer = ByteArray(1024)
        var numRead = 0
        while (fis.read(buffer).also { numRead = it } > 0) {
            messagedigest!!.update(buffer, 0, numRead)
        }
        fis.close()
        return bufferToHex(messagedigest!!.digest())
    }

    private fun bufferToHex(bytes: ByteArray, m: Int = 0, n: Int = bytes.size): String {
        val stringbuffer = StringBuffer(2 * n)
        val k = m + n
        for (l in m until k) {
            appendHexPair(bytes[l], stringbuffer)
        }
        return stringbuffer.toString()
    }

    private fun appendHexPair(bt: Byte, stringbuffer: StringBuffer) {
        val c0 = md5String[bt.toInt() and 0xf0 shr 4] // 取字节中高 4 位的数字转换
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同  
        val c1 = md5String[bt.toInt() and 0xf] // 取字节中低 4 位的数字转换
        stringbuffer.append(c0)
        stringbuffer.append(c1)
    }

    init {
        try {
            messagedigest = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }
}