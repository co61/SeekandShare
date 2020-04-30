package fr.c7regne.seekandsharedrawer;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostSaveStruct {
    private String userId, userName;
    private CharSequence Title;
    private CharSequence Content;
    private CharSequence Place;
    private String DPchoice;
    private String SPchoice;
    private String publicationDate;


    public PostSaveStruct(String id, String userName, CharSequence t, CharSequence c,CharSequence l, String dp, String sp, String publicationDate) {
        this.userId = id;
        this.userName = userName;
        this.Title = t;
        this.Content = c;
        this.Place = l;
        this.DPchoice = dp;
        this.SPchoice = sp;
        this.publicationDate = publicationDate;
    }

    public CharSequence getTitle() {
        return Title;
    }

    public void setTitle(CharSequence title) {
        Title = title;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CharSequence getContent() {
        return Content;
    }

    public void setContent(CharSequence content) {
        Content = content;
    }

    public String getDPchoice() {
        return DPchoice;
    }

    public void setDPchoice(String DPchoice) {
        this.DPchoice = DPchoice;
    }

    public String getSPchoice() {
        return SPchoice;
    }

    public void setSPchoice(String SPchoice) {
        this.SPchoice = SPchoice;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    public CharSequence getPlace() {
        return Place;
    }

    public void setPlace(CharSequence place) {
        Place = place;
    }

}
