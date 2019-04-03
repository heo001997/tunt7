public class HighTime {
    long hour;
    long day;
    long month;
    long year;
    long traffic;

    public HighTime(long hour, long day, long month, long year, long traffic) {
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
        this.traffic = traffic;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public long getTraffic() {
        return traffic;
    }

    public void setTraffic(long traffic) {
        this.traffic = traffic;
    }
}
