package com.my.library_net.interceptor;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class BasicDataInterceptor implements Interceptor {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originRequest = chain.request();
        HttpUrl httpUrl = originRequest.url();
        StringBuilder requestUrl = new StringBuilder();
        requestUrl.append(httpUrl.url().toString() + "?");
        try {
            // GET方法
            if (METHOD_GET.equals(originRequest.method())) {
                requestUrl.append(httpUrl.toString());

            } else if (METHOD_POST.equals(originRequest.method())) {
                RequestBody requestBody = originRequest.body();
                if (requestBody != null) {
                    String msg = "-url--" + originRequest.url();
                    if (msg.contains("uploadFile")) {
                        requestUrl.append("--上传文件内容--");
                    } else {
                        requestUrl.append(getParam(requestBody));
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.proceed(originRequest);
    }


    /**
     * 读取参数
     *
     * @param requestBody
     * @return
     */
    private String getParam(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        String logparm;
        try {
            requestBody.writeTo(buffer);
            logparm = buffer.readUtf8();
            logparm = URLDecoder.decode(logparm, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return logparm;
    }
}
