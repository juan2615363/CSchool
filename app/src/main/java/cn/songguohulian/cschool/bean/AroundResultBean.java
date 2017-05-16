package cn.songguohulian.cschool.bean;

import java.util.List;

/**
 *
 * @author Ziv
 * @data 2017/4/29
 * @time 16:00
 *
 * 周边游 javabean
 */

public class AroundResultBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : {"around_imginfo":[{"figure":"/around/around_bj.jpg"}],"around_info":[{"cover_price":"30元","figure":"/around/jingtanggucun.jpg","name":"井塘古村","address":"https://lvyou.baidu.com/jingtanggucun/","product_id":"10659"}]}
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
        private List<AroundImginfoBean> around_imginfo;
        private List<AroundInfoBean> around_info;

        public List<AroundImginfoBean> getAround_imginfo() {
            return around_imginfo;
        }

        public void setAround_imginfo(List<AroundImginfoBean> around_imginfo) {
            this.around_imginfo = around_imginfo;
        }

        public List<AroundInfoBean> getAround_info() {
            return around_info;
        }

        public void setAround_info(List<AroundInfoBean> around_info) {
            this.around_info = around_info;
        }

        public static class AroundImginfoBean {
            /**
             * figure : /around/around_bj.jpg
             */

            private String figure;

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }
        }

        public static class AroundInfoBean {
            /**
             * cover_price : 30元
             * figure : /around/jingtanggucun.jpg
             * name : 井塘古村
             * address : https://lvyou.baidu.com/jingtanggucun/
             * product_id : 10659
             */

            private String cover_price;
            private String figure;
            private String name;
            private String address;
            private String product_id;

            public String getCover_price() {
                return cover_price;
            }

            public void setCover_price(String cover_price) {
                this.cover_price = cover_price;
            }

            public String getFigure() {
                return figure;
            }

            public void setFigure(String figure) {
                this.figure = figure;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }
        }
    }
}
