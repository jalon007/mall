package com.qutalk.mall.wx.splider;

/**
 * @author Jalon
 * @Description:
 * @create 2019/1/2 - 16:33
 * @package com.qutalk.mall.wx.splider
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 */
public class DownloadUtil {

    /**
     * 文件下载
     * @param url 链接地址
     * @param path 要保存的路径及文件名
     * @return
     */
    public static boolean download(String url,String path){

        boolean flag = false;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
                .setConnectTimeout(2000).build();

        HttpGet httpGet= new HttpGet(url);
        httpGet.setConfig(requestConfig);

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try{
            CloseableHttpResponse result = httpclient.execute(httpGet);
            System.out.println(result.getStatusLine());
            if(result.getStatusLine().getStatusCode() == 200){
                in = new BufferedInputStream(result.getEntity().getContent());
                File file = new File(path);
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[1024];
                int len = -1;
                while((len = in.read(buffer,0,1024)) > -1){
                    out.write(buffer,0,len);
                }
                flag = true;
            }

        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }finally{
            httpGet.releaseConnection();
            try{
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
            }catch(Exception e){
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}