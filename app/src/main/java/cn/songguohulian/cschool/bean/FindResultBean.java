package cn.songguohulian.cschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class FindResultBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : {"act_info":[{"icon_url":"/operation/img/1478169868/gongyi.jpg","name":"广告位","url":"/oper/1478169868app.html"},{"icon_url":"/operation/img/1478169868/qingchun2.jpg","name":"富华游乐园","url":"/oper/1478169868app.html"},{"icon_url":"/operation/img/1478169868/qingchun1.jpg","name":"VR体验馆","url":"/oper/1478763176app.html"}]}
     */

    private int code;
    private String msg;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ActInfoBean> act_info;

        public List<ActInfoBean> getAct_info() {
            return act_info;
        }

        public void setAct_info(List<ActInfoBean> act_info) {
            this.act_info = act_info;
        }

        public static class ActInfoBean {
            /**
             * icon_url : /operation/img/1478169868/gongyi.jpg
             * name : 广告位
             * url : /oper/1478169868app.html
             */

            private String icon_url;
            private String name;
            private String url;

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
