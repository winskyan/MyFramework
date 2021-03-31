package com.my.library_base.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.StatFs
import android.util.Log
import java.io.*
import java.util.*

/**
 * 文件工具类
 *
 * @author 曾繁添
 * @version 1.0
 */
object FileUtils {
    private val TAG = FileUtils::class.java.simpleName

    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    private val isMountedSDCard: Boolean
        get() = if (Environment.MEDIA_MOUNTED == Environment
                        .getExternalStorageState()) {
            true
        } else {
            Log.w(TAG, "SDCARD is not MOUNTED !")
            false
        }

    /**
     * 获取SD卡剩余容量（单位Byte）
     *
     * @return
     */
    fun gainSDFreeSize(): Long {
        return if (isMountedSDCard) {
            // 取得SD卡文件路径
            val path = Environment.getExternalStorageDirectory()
            val sf = StatFs(path.path)
            // 获取单个数据块的大小(Byte)
            val blockSize = sf.blockSize.toLong()
            // 空闲的数据块的数量
            val freeBlocks = sf.availableBlocks.toLong()

            // 返回SD卡空闲大小
            freeBlocks * blockSize // 单位Byte
        } else {
            0
        }
    }

    /**
     * 获取SD卡总容量（单位Byte）
     *
     * @return
     */
    fun gainSDAllSize(): Long {
        return if (isMountedSDCard) {
            // 取得SD卡文件路径
            val path = Environment.getExternalStorageDirectory()
            val sf = StatFs(path.path)
            // 获取单个数据块的大小(Byte)
            val blockSize = sf.blockSize.toLong()
            // 获取所有数据块数
            val allBlocks = sf.blockCount.toLong()
            // 返回SD卡大小（Byte）
            allBlocks * blockSize
        } else {
            0
        }
    }

    /**
     * 获取可用的SD卡路径（若SD卡不没有挂载则返回""）
     *
     * @return
     */
    fun gainSDCardPath(): String {
        if (isMountedSDCard) {
            val sdcardDir = Environment.getExternalStorageDirectory()
            if (!sdcardDir.canWrite()) {
                Log.w(TAG, "SDCARD can not write !")
            }
            return sdcardDir.path
        }
        return ""
    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath 文件路径
     */
    @Throws(IOException::class)
    fun readFileByLines(filePath: String?): String {
        var reader: BufferedReader? = null
        val sb = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(FileInputStream(filePath), System.getProperty("file.encoding")))
            var tempString: String? = null
            while (reader.readLine().also { tempString = it } != null) {
                sb.append(tempString)
                sb.append("\n")
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
        return sb.toString()
    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath 文件路径
     * @param encoding 写文件编码
     */
    @Throws(IOException::class)
    fun readFileByLines(filePath: String?, encoding: String?): String {
        var reader: BufferedReader? = null
        val sb = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(FileInputStream(filePath), encoding))
            var tempString: String? = null
            while (reader.readLine().also { tempString = it } != null) {
                sb.append(tempString)
                sb.append("\n")
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
        return sb.toString()
    }
    /**
     * 指定编码保存内容
     *
     * @param filePath 文件路径
     * @param content  保存的内容
     * @param encoding 写文件编码
     * @throws IOException
     */
    /**
     * 保存内容
     *
     * @param filePath 文件路径
     * @param content  保存的内容
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun saveToFile(filePath: String?, content: String?, encoding: String? = System.getProperty("file.encoding")) {
        var writer: BufferedWriter? = null
        val file = File(filePath)
        try {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file, false), encoding))
            writer.write(content)
        } finally {
            writer?.close()
        }
    }
    /**
     * 追加文本
     *
     * @param content  需要追加的内容
     * @param file     待追加文件源
     * @param encoding 文件编码
     * @throws IOException
     */
    /**
     * 追加文本
     *
     * @param content 需要追加的内容
     * @param file    待追加文件源
     * @throws IOException
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun appendToFile(content: String?, file: File, encoding: String? = System.getProperty("file.encoding")) {
        var writer: BufferedWriter? = null
        try {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            writer = BufferedWriter(OutputStreamWriter(FileOutputStream(file, true), encoding))
            writer.write(content)
        } finally {
            writer?.close()
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     * @throws Exception
     */
    fun isExsit(filePath: String?): Boolean {
        var flag = false
        try {
            val file = File(filePath)
            if (file.exists()) {
                flag = true
            }
        } catch (e: Exception) {
            println("判断文件失败-->" + e.message)
        }
        return flag
    }

    /**
     * 快速读取程序应用包下的文件内容
     *
     * @param context  上下文
     * @param filename 文件名称
     * @return 文件内容
     * @throws IOException
     */
    @Throws(IOException::class)
    fun read(context: Context, filename: String?): String {
        val inStream = context.openFileInput(filename)
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = 0
        while (inStream.read(buffer).also { len = it } != -1) {
            outStream.write(buffer, 0, len)
        }
        val data = outStream.toByteArray()
        return String(data)
    }

    /**
     * 读取指定目录文件的文件内容
     *
     * @param fileName 文件名称
     * @return 文件内容
     * @throws Exception
     */
    @Throws(IOException::class)
    fun read(fileName: String?): String {
        val inStream = FileInputStream(fileName)
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = 0
        while (inStream.read(buffer).also { len = it } != -1) {
            outStream.write(buffer, 0, len)
        }
        val data = outStream.toByteArray()
        return String(data)
    }

    /***
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param fileName
     * 文件名称
     * @param encoding
     * 文件编码
     * @return 字符串内容
     * @throws IOException
     */
    @Throws(IOException::class)
    fun read(fileName: String?, encoding: String?): String {
        var reader: BufferedReader? = null
        val sb = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(
                    FileInputStream(fileName), encoding))
            var tempString: String? = null
            while (reader.readLine().also { tempString = it } != null) {
                sb.append(tempString)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
        return sb.toString()
    }

    /**
     * 读取raw目录的文件内容
     *
     * @param context   内容上下文
     * @param rawFileId raw文件名id
     * @return
     */
    fun readRawValue(context: Context, rawFileId: Int): String {
        var result = ""
        try {
            val `is` = context.resources.openRawResource(rawFileId)
            val len = `is`.available()
            val buffer = ByteArray(len)
            `is`.read(buffer)
            result = String(buffer)
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context  内容上下文
     * @param fileName 文件名称，包含扩展名
     * @return
     */
    fun readAssetsValue(context: Context, fileName: String?): String {
        var result = ""
        try {
            val `is` = context.resources.assets.open(fileName!!)
            val len = `is`.available()
            val buffer = ByteArray(len)
            `is`.read(buffer)
            result = String(buffer)
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context  内容上下文
     * @param fileName 文件名称，包含扩展名
     * @return
     */
    fun readAssetsListValue(context: Context, fileName: String?): List<String?> {
        val list: MutableList<String?> = ArrayList()
        try {
            val `in` = context.resources.assets.open(fileName!!)
            val br = BufferedReader(InputStreamReader(`in`, "UTF-8"))
            var str: String? = null
            while (br.readLine().also { str = it } != null) {
                list.add(str)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }

    /**
     * 获取SharedPreferences文件内容
     *
     * @param context       上下文
     * @param fileNameNoExt 文件名称（不用带后缀名）
     * @return
     */
    fun readShrePerface(context: Context, fileNameNoExt: String?): Map<String, *> {
        val preferences = context.getSharedPreferences(
                fileNameNoExt, Context.MODE_PRIVATE)
        return preferences.all
    }

    /**
     * 写入SharedPreferences文件内容
     *
     * @param context       上下文
     * @param fileNameNoExt 文件名称（不用带后缀名）
     * @param values        需要写入的数据Map(String,Boolean,Float,Long,Integer)
     * @return
     */
    fun writeShrePerface(context: Context, fileNameNoExt: String?, values: Map<String?, *>) {
        try {
            val preferences = context.getSharedPreferences(fileNameNoExt, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            val iterator: Iterator<*> = values.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<String, *>
                if (entry.value is String) {
                    editor.putString(entry.key, entry.value as String?)
                } else if (entry.value is Boolean) {
                    editor.putBoolean(entry.key, (entry.value as Boolean?)!!)
                } else if (entry.value is Float) {
                    editor.putFloat(entry.key, (entry.value as Float?)!!)
                } else if (entry.value is Long) {
                    editor.putLong(entry.key, (entry.value as Long?)!!)
                } else if (entry.value is Int) {
                    editor.putInt(entry.key, (entry.value as Int?)!!)
                }
            }
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param content  文件内容
     */
    fun write(context: Context, fileName: String?, content: String) {
        try {
            val outStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE)
            outStream.write(content.toByteArray())
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param content  文件内容
     */
    fun write(context: Context, fileName: String?, content: ByteArray?) {
        try {
            val outStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE)
            outStream.write(content)
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param modeType 文件写入模式（Context.MODE_PRIVATE、Context.MODE_APPEND、Context.
     * MODE_WORLD_READABLE、Context.MODE_WORLD_WRITEABLE）
     * @param content  文件内容
     */
    fun write(context: Context, fileName: String?, content: ByteArray?,
              modeType: Int) {
        try {
            val outStream = context.openFileOutput(fileName,
                    modeType)
            outStream.write(content)
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 指定编码将内容写入目标文件
     *
     * @param target   目标文件
     * @param content  文件内容
     * @param encoding 写入文件编码
     * @throws Exception
     */
    @Throws(IOException::class)
    fun write(target: File, content: String?, encoding: String?) {
        var writer: BufferedWriter? = null
        try {
            if (!target.parentFile.exists()) {
                target.parentFile.mkdirs()
            }
            writer = BufferedWriter(OutputStreamWriter(
                    FileOutputStream(target, false), encoding))
            writer.write(content)
        } finally {
            writer?.close()
        }
    }

    /**
     * 指定目录写入文件内容
     *
     * @param filePath 文件路径+文件名
     * @param content  文件内容
     * @throws IOException
     */
    @Throws(IOException::class)
    fun write(filePath: String?, content: ByteArray?) {
        var fos: FileOutputStream? = null
        try {
            val file = File(filePath)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            fos = FileOutputStream(file)
            fos.write(content)
            fos.flush()
        } finally {
            fos?.close()
        }
    }

    /**
     * 写入文件
     *
     * @param
     * @param
     * @throws IOException
     */
    @Throws(IOException::class)
    fun write(inputStream: InputStream, filePath: String?): File {
        var outputStream: OutputStream? = null
        // 在指定目录创建一个空文件并获取文件对象
        val mFile = File(filePath)
        if (!mFile.parentFile.exists()) mFile.parentFile.mkdirs()
        return try {
            outputStream = FileOutputStream(mFile)
            val buffer = ByteArray(4 * 1024)
            var lenght = 0
            while (inputStream.read(buffer).also { lenght = it } > 0) {
                outputStream.write(buffer, 0, lenght)
            }
            outputStream.flush()
            mFile
        } catch (e: IOException) {
            Log.e(TAG, "写入文件失败，原因：" + e.message)
            throw e
        } finally {
            try {
                inputStream.close()
                outputStream!!.close()
            } catch (e: IOException) {
            }
        }
    }

    /**
     * 指定目录写入文件内容
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun saveAsJPEG(bitmap: Bitmap, filePath: String?) {
        var fos: FileOutputStream? = null
        try {
            val file = File(filePath)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
        } finally {
            fos?.close()
        }
    }

    /**
     * 指定目录写入文件内容
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun saveAsPNG(bitmap: Bitmap, filePath: String?) {
        var fos: FileOutputStream? = null
        try {
            val file = File(filePath)
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
        } finally {
            fos?.close()
        }
    }
}