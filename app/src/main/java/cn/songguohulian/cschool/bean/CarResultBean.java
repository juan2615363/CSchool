package cn.songguohulian.cschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class CarResultBean {


    /**
     * code : 200
     * msg : 请求成功
     * result : {"banner_info":[{"image":"/1478770583834.png","option":3,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/1478770583835.png","option":2,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/1478770583836.png","option":1,"type":0,"value":{"url":"/act20161111?cyc_app=1"}}],"channel_info":[{"channel_name":"公告栏","image":"/car/message.png","option":2,"type":1,"value":{"channel_id":"8"}},{"channel_name":"起售时间","image":"/car/time.png","option":2,"type":1,"value":{"channel_id":"8"}},{"channel_name":"温馨服务","image":"/car/server.png","option":2,"type":1,"value":{"channel_id":"4"}},{"channel_name":"火车票","image":"/car/huoche.png","option":2,"type":1,"value":{"channel_id":"3"}},{"channel_name":"约车","image":"/car/car.png","option":2,"type":1,"value":{"channel_id":"5"}}]}
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
        private List<BannerInfoBean> banner_info;
        private List<ChannelInfoBean> channel_info;

        public List<BannerInfoBean> getBanner_info() {
            return banner_info;
        }

        public void setBanner_info(List<BannerInfoBean> banner_info) {
            this.banner_info = banner_info;
        }

        public List<ChannelInfoBean> getChannel_info() {
            return channel_info;
        }

        public void setChannel_info(List<ChannelInfoBean> channel_info) {
            this.channel_info = channel_info;
        }

        public static class BannerInfoBean {
            /**
             * image : /1478770583834.png
             * option : 3
             * type : 0
             * value : {"url":"/act20161111?cyc_app=1"}
             */

            private String image;
            private int option;
            private int type;
            private ValueBean value;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getOption() {
                return option;
            }

            public void setOption(int option) {
                this.option = option;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public ValueBean getValue() {
                return value;
            }

            public void setValue(ValueBean value) {
                this.value = value;
            }

            public static class ValueBean {
                /**
                 * url : /act20161111?cyc_app=1
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class ChannelInfoBean {
            /**
             * channel_name : 公告栏
             * image : /car/message.png
             * option : 2
             * type : 1
             * value : {"channel_id":"8"}
             */

            private String channel_name;
            private String image;
            private int option;
            private int type;
            private ValueBeanX value;

            public String getChannel_name() {
                return channel_name;
            }

            public void setChannel_name(String channel_name) {
                this.channel_name = channel_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getOption() {
                return option;
            }

            public void setOption(int option) {
                this.option = option;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public ValueBeanX getValue() {
                return value;
            }

            public void setValue(ValueBeanX value) {
                this.value = value;
            }

            public static class ValueBeanX {
                /**
                 * channel_id : 8
                 */

                private String channel_id;

                public String getChannel_id() {
                    return channel_id;
                }

                public void setChannel_id(String channel_id) {
                    this.channel_id = channel_id;
                }
            }
        }
    }
}
