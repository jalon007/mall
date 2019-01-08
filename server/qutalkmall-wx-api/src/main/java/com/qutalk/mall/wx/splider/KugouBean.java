package com.qutalk.mall.wx.splider;

/**
 * @author Jalon
 * @Description:
 * @create 2019/1/2 - 16:54
 * @package com.qutalk.mall.wx.splider
 */
public class KugouBean {
    Integer status;

    Bean data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Bean getData() {
        return data;
    }

    public void setData(Bean data) {
        this.data = data;
    }

    public static class  Bean{
        String hash;
        String play_url;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }
    }
}
