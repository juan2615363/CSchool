package cn.songguohulian.cschool.bean;

import cn.bmob.v3.BmobObject;

/**
 *
 * @author Ziv
 * @data 2017/5/12
 * @time 16:36
 *
 * 车票详情bean
 */

public class TicketBean extends BmobObject {
    private String destination; // 目的地
    private String place; // 出发地
    private String gooff; // 出发时间
    private String arrival; //到达时间
    private String price; // 票价
    private String surplus; // 余票
    private String date; // 出发日期


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGooff() {
        return gooff;
    }

    public void setGooff(String gooff) {
        this.gooff = gooff;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
