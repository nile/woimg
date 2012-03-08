package utils;

import org.apache.commons.lang.time.DateUtils;
import play.Logger;
import play.templates.JavaExtensions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class WoimgExtensions extends JavaExtensions {
	public static String freindly(Date date) {
		return FriendlyTime.friendlyTime(date);
	}
	public static String chsWeekDay(Date date) {
		String dayNames[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		int i = DateUtils.toCalendar(date).get(Calendar.DAY_OF_WEEK);
		return dayNames[i-1];
	}
	public static String fmt(Number l, String fmt) {
		DecimalFormat df = new DecimalFormat(fmt);
		return df.format(l);
	}
    public static String domain(String url){
        if(url!=null){
            try {
                return new URL(url).getHost();
            } catch (MalformedURLException e) {
                Logger.warn("转换url时候出错",e);
                return "";
            }
        }
        return "";
    }
}
