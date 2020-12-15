package ca.bcit.czone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notification {
    private String id;
    private String title;
    private String description;
    private String address;
    private String dateTime;

    public Notification(String id, String title, String description, String address, String dateTime){
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.dateTime = dateTime;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getAddress() {
        return address;
    }

    public String getDateTime() { return dateTime; }

    Date getReportedDate() throws ParseException {
        return new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(dateTime);
    }

    public Notification() {}
}
