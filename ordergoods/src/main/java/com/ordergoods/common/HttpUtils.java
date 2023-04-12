package com.ordergoods.common;


import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpUtils {
    private static final Log logger = LogFactory.getLog(HttpUtils.class.getName());

    public HttpUtils() {
    }

    private static void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            Iterator var4 = keySet.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                httpPost.addHeader(key, (String)headMap.get(key));
            }
        }

    }

    public static String sendGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return sendGet(httpclient, url, (Map)null);
    }




    public static String sendGet(CloseableHttpClient httpClient, String url, Map<String, String> headMap) {
        String ret = "";

        try {
            HttpGet get = new HttpGet(url);
            logger.info("get url:...." + get.getURI());
            setGetHead(get, headMap);
            CloseableHttpResponse httpResponse = httpClient.execute(get);

            try {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    ret = getRespString(entity);
                    EntityUtils.consume(entity);
                }
            } finally {
                httpResponse.close();
                get.releaseConnection();
            }
        } catch (Exception var22) {
            logger.error("error:" + var22.getMessage());
        } finally {
            try {
                closeHttpClient(httpClient);
            } catch (IOException var20) {
                logger.error("error:" + var20.getMessage());
            }

        }

        return ret;
    }

    private static void closeHttpClient(CloseableHttpClient client) throws IOException {
        if (client != null) {
            client.close();
        }

    }

    private static void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            Iterator var4 = keySet.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                httpGet.addHeader(key, (String)headMap.get(key));
            }
        }

    }

    private static String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        } else {
            InputStream is = entity.getContent();
            StringBuffer strBuf = new StringBuffer();
            byte[] buffer = new byte[4096];
            boolean var4 = false;

            int r;
            while((r = is.read(buffer)) > 0) {
                strBuf.append(new String(buffer, 0, r, "UTF-8"));
            }

            return strBuf.toString();
        }
    }

    public static long getContentLength(HttpExchange he) {
        List<String> ret_list = he.getRequestHeaders().get("Content-Length");
        if (ret_list != null && !ret_list.isEmpty()) {
            for(int i = 0; i < ret_list.size(); ++i) {
                String ret = ((String)ret_list.get(i)).trim();
                if (!ret.isEmpty()) {
                    try {
                        long tid = Long.parseLong(ret);
                        return tid;
                    } catch (Exception var6) {
                        ;
                    }
                }
            }

            return 0L;
        } else {
            return 0L;
        }
    }


    public static String uploadFileImpl(String serverUrl, Map<String, String> file_map, Map<String, String> params, Map<String, String> headMap) throws Exception {
        String respStr = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httppost = new HttpPost(serverUrl);
            setPostHead(httppost, headMap);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            setUploadParams(multipartEntityBuilder, params);
            Iterator var9 = file_map.keySet().iterator();

            while(var9.hasNext()) {
                Object o = var9.next();
                String field_name = (String)o;
                String file_name = (String)file_map.get(o);
                File file = new File(file_name);
                if (file.exists()) {
                    FileBody filebody = new FileBody(file);
                    multipartEntityBuilder.addPart(field_name, filebody);
                }
            }

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);

            try {
                HttpEntity resEntity = response.getEntity();
                respStr = getRespString(resEntity);
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
                httppost.releaseConnection();
            }
        } finally {
            httpclient.close();
        }

        return respStr;
    }

    public static String sendPost(String urlParam, String content) throws Exception {
        logger.debug("sendPost:" + urlParam + " content:" + content);
        StringBuilder result = new StringBuilder();
        URL httpUrl = null;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        try {
            httpUrl = new URL(urlParam);
            connection = (HttpURLConnection)httpUrl.openConnection();
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
            connection.setRequestProperty("Content-Type", "multipart/form-data;name=media");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            byte[] b = content.getBytes("UTF-8");
            connection.getOutputStream().write(b, 0, b.length);
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            connection.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";

            while((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            bufferedReader.close();
            bufferedReader = null;
        } catch (Exception var11) {
            logger.error("Failed to send message to wechat server" + urlParam, var11);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
                bufferedReader = null;
            }

            if (connection != null) {
                connection.disconnect();
                connection = null;
            }

        }

        if (logger.isInfoEnabled()) {
            logger.info("result=" + result);
        }

        return result.toString();
    }


    private static void setUploadParams(MultipartEntityBuilder multipartEntityBuilder, Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            Iterator var4 = keys.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                multipartEntityBuilder.addPart(key, new StringBody((String)params.get(key), ContentType.TEXT_PLAIN));
            }
        }

    }
}
