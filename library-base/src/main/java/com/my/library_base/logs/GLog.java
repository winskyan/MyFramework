package com.my.library_base.logs;

import android.text.TextUtils;
import android.util.Log;

import com.my.library_base.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GLog {
    private static boolean IS_SHOW_LOG = true;

    private static Logger logger;
    private static boolean mIsInitConfigure = false;

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final int JSON_INDENT = 4;

    private static final int D = 0x1;
    private static final int I = 0x2;
    private static final int W = 0x3;
    private static final int E = 0x4;
    private static final int JSON = 0x5;

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void d() {
        printLog(D, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, msg);
    }

    public static void i() {
        printLog(I, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, msg);
    }


    public static void w() {
        printLog(W, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, msg);
    }


    public static void e() {
        printLog(E, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, msg);
    }

    public static void json(String jsonFormat) {
        printLog(JSON, jsonFormat);
    }


    private static void printLog(int type, Object objectMsg) {
        String msg;
        if (!IS_SHOW_LOG) {
            return;
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        int index = 4;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();

        logger = LoggerFactory.getLogger(stackTrace[index].getClassName());

        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[(").append(className).append(":").append(lineNumber).append(")#").append(methodName).append("]");

        if (objectMsg == null) {
            msg = "Log with null Object";
        } else {
            msg = objectMsg.toString();
        }
        if (msg != null && type != JSON) {
            stringBuilder.append(msg);
        }

        String logStr = stringBuilder.toString();

        switch (type) {
            case D:
            case I:
            case W:
            case E:
                printMsg(type, logStr);
                break;
            case JSON: {
                if (TextUtils.isEmpty(msg)) {
                    printMsg(D, "Empty or Null json content");
                    return;
                }

                String message = null;

                try {
                    if (msg.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(msg);
                        message = jsonObject.toString(JSON_INDENT);
                    } else if (msg.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(msg);
                        message = jsonArray.toString(JSON_INDENT);
                    }
                } catch (JSONException e) {
                    printMsg(E, e.getCause().getMessage() + "\n" + msg);
                    return;
                }

                printLine(true);
                message = logStr + LINE_SEPARATOR + message;
                String[] lines = message.split(LINE_SEPARATOR);
                StringBuilder jsonContent = new StringBuilder();
                for (String line : lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR);
                }
                //Log.i(tag, jsonContent.toString());

                if (jsonContent.toString().length() > 3200) {
                    printMsg(W, "jsonContent.length = " + jsonContent.toString().length());
                    int chunkCount = jsonContent.toString().length() / 3200;
                    for (int i = 0; i <= chunkCount; i++) {
                        int max = 3200 * (i + 1);
                        if (max >= jsonContent.toString().length()) {
                            printMsg(W, jsonContent.toString().substring(3200 * i));
                        } else {
                            printMsg(W, jsonContent.toString().substring(3200 * i, max));
                        }
                    }

                } else {
                    printMsg(W, jsonContent.toString());

                }
                printLine(false);
            }
            break;
        }

    }

    private static void printLine(boolean isTop) {
        if (isTop) {
            printMsg(W, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            printMsg(W, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }

    }

    private static void printMsg(int level, String msg) {
        if (mIsInitConfigure) {
            if (null == logger) {
                return;
            }
            switch (level) {
                case D:
                    logger.debug(msg);
                    break;
                case I:
                    logger.info(msg);
                    break;
                case W:
                    logger.warn(msg);
                    break;
                case E:
                    logger.error(msg);
                    break;
                default:
                    break;
            }
        } else {
            switch (level) {
                case D:
                    Log.d(Constants.TAG, msg);
                    break;
                case I:
                    Log.i(Constants.TAG, msg);
                    break;
                case W:
                    Log.w(Constants.TAG, msg);
                    break;
                case E:
                    Log.e(Constants.TAG, msg);
                    break;
                default:
                    break;
            }
        }
    }
}
