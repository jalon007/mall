package com.qutalk.mall.wx.splider;

/**
 * @author Jalon
 * @Description:
 * @create 2019/1/2 - 16:31
 * @package com.qutalk.mall.wx.splider
 */
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderKugou {
    private static Log logger = LogFactory.getLog(SpiderKugou.class);

    public static Integer pages=10;
    public static String filePath = "E:/music/";
    public static String mp3 = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery191027067069941080546_1546235744250&"
            + "hash=HASH&album_id=0&_=TIME";

    public static String LINK = "https://www.kugou.com/yy/rank/home/PAGE-8888.html?from=rank";

    public static void main(String[] args) throws IOException {

        for(int i = 1 ; i < pages ; i++){
            String url = LINK.replace("PAGE", i + "");
            analysHtml(url);
        }
    }

    public static String analysHtml(String url) throws IOException{
        String content = HttpGetConnect.connect(url, "utf-8");
        HtmlManage html = new HtmlManage();
        Document doc = html.manage(content);
        Element ele = doc.getElementsByClass("pc_temp_songlist").get(0);
        Elements eles = ele.getElementsByTag("li");
        for(int i = 0 ; i < eles.size() ; i++){
            Element item = eles.get(i);
            String title = item.attr("title").trim();
            String link = item.getElementsByTag("a").first().attr("href");

            download(link,title);
        }
        return null;
    }

    public static String download(String url,String name) throws IOException{
        String hash = "";
        String content = HttpGetConnect.connect(url, "utf-8");
        HtmlManage html = new HtmlManage();

        String regEx = "\"hash\":\"[0-9A-Z]+\"";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            hash = matcher.group();
            hash = hash.replace("\"hash\":\"", "");
            hash = hash.replace("\"", "");
        }

        String item = mp3.replace("HASH", hash);
        item = item.replace("TIME", System.currentTimeMillis() + "");

        logger.info(item);
        String mp = HttpGetConnect.connect(item, "utf-8");

        mp = mp.substring(mp.indexOf("(") + 1, mp.length() - 3);

        KugouBean bean= JSON.parseObject(mp.toString(), KugouBean.class);

        logger.info(bean.getData().getPlay_url() + "  ==  ");
        DownloadUtil.download(bean.getData().getPlay_url(), filePath + name + ".mp3");

        logger.info(name + "下载完成");
        return bean.getData().getPlay_url();
    }

}