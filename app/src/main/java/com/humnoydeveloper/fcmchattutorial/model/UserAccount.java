package com.humnoydeveloper.fcmchattutorial.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 18/6/59.22:25น.
 *
 * @FcmChatTutorial
 */
@IgnoreExtraProperties
public class UserAccount {
//    private String avatar;
    private String username;
    private String email;
    private String status;

    public UserAccount() {
    }

    public UserAccount(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
