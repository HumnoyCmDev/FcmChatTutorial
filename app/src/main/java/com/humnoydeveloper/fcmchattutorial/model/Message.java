package com.humnoydeveloper.fcmchattutorial.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 19/6/59.13:32น.
 *
 * @FcmChatTutorial
 */
@IgnoreExtraProperties
public class Message {
    private String uid;
    private String author;
    private String text;
    private String timeStamp;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid",uid);
        result.put("author",author);
        result.put("text",text);
        result.put("timeStamp",timeStamp);

        return result;
    }
}
