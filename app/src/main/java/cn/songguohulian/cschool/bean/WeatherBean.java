package cn.songguohulian.cschool.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */

public class WeatherBean {

    /**
     * desc : OK
     * status : 1000
     * data : {"wendu":"17","ganmao":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","forecast":[{"fengxiang":"南风","fengli":"3-4级","high":"高温 26℃","type":"多云","low":"低温 14℃","date":"3日星期三"},{"fengxiang":"北风","fengli":"3-4级","high":"高温 22℃","type":"小雨","low":"低温 12℃","date":"4日星期四"},{"fengxiang":"北风","fengli":"3-4级","high":"高温 27℃","type":"多云","low":"低温 10℃","date":"5日星期五"},{"fengxiang":"南风","fengli":"3-4级","high":"高温 27℃","type":"晴","low":"低温 14℃","date":"6日星期六"},{"fengxiang":"北风","fengli":"3-4级","high":"高温 30℃","type":"晴","low":"低温 16℃","date":"7日星期天"}],"yesterday":{"fl":"3-4级","fx":"东南风","high":"高温 30℃","type":"晴","low":"低温 13℃","date":"2日星期二"},"city":"潍坊"}
     */

    private String desc;
    private int status;
    private DataBean data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wendu : 17
         * ganmao : 天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。
         * forecast : [{"fengxiang":"南风","fengli":"3-4级","high":"高温 26℃","type":"多云","low":"低温 14℃","date":"3日星期三"},{"fengxiang":"北风","fengli":"3-4级","high":"高温 22℃","type":"小雨","low":"低温 12℃","date":"4日星期四"},{"fengxiang":"北风","fengli":"3-4级","high":"高温 27℃","type":"多云","low":"低温 10℃","date":"5日星期五"},{"fengxiang":"南风","fengli":"3-4级","high":"高温 27℃","type":"晴","low":"低温 14℃","date":"6日星期六"},{"fengxiang":"北风","fengli":"3-4级","high":"高温 30℃","type":"晴","low":"低温 16℃","date":"7日星期天"}]
         * yesterday : {"fl":"3-4级","fx":"东南风","high":"高温 30℃","type":"晴","low":"低温 13℃","date":"2日星期二"}
         * city : 潍坊
         */

        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private String city;
        private List<ForecastBean> forecast;

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * fl : 3-4级
             * fx : 东南风
             * high : 高温 30℃
             * type : 晴
             * low : 低温 13℃
             * date : 2日星期二
             */

            private String fl;
            private String fx;
            private String high;
            private String type;
            private String low;
            private String date;

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class ForecastBean {
            /**
             * fengxiang : 南风
             * fengli : 3-4级
             * high : 高温 26℃
             * type : 多云
             * low : 低温 14℃
             * date : 3日星期三
             */

            private String fengxiang;
            private String fengli;
            private String high;
            private String type;
            private String low;
            private String date;

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
