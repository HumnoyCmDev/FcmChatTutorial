package com.humnoydeveloper.fcmchattutorial.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 19/6/59.20:13น.
 *
 * @FcmChatTutorial
 */
@IgnoreExtraProperties
public class Room {
    private String userId;
    private String author;
    private String interlocutor;
    private String timeStamp;

    public Room() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid",userId);
        result.put("author",author);
        result.put("interlocutor",interlocutor);
        result.put("timeStamp",timeStamp);
        return result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
