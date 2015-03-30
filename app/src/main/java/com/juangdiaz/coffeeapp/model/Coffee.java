package com.juangdiaz.coffeeapp.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by juangdiaz on 3/28/15.
 */
public class Coffee {

    private String desc;
    private String image_url;
    private String id;
    private String name;
    private String last_updated_at;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(String last_updated_at) {
        this.last_updated_at = last_updated_at;
    }


    //Formatted Date
    public String getLastTimeUpdatedFormattedDate() {
        if (getLast_updated_at() == null)
            return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date result = df.parse(getLast_updated_at());
            Date todayDate = new Date();
            long timeDifference = todayDate.getTime() - result.getTime();
            long diffHours = timeDifference / (60 * 60 * 1000);
            int diffInDays = (int) timeDifference / (1000 * 60 * 60 * 24);
            int diffInWeek = (int) timeDifference / (1000 * 60 * 60 * 24 * 7);
            if (diffInWeek != 0) {

                return "Updated " + diffInWeek +
                        (diffInWeek == 1? " Week ago": " Weeks ago");
            }
            if (diffInDays != 0) {
                return "Updated " + diffInDays +
                        (diffInDays == 1? " Day ago": " Days ago");
            }
            if (diffHours != 0) {
                return "Updated " + diffHours +
                        (diffHours == 1? " Hour ago": " Hours ago");
            }
            return "Updated just now" ;

        } catch (ParseException e) {
            return null;
        }
    }



}


