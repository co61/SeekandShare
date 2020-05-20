package fr.c7regne.seekandsharedrawer;

public class MessSaveStruct {
    private Boolean Side;
    private CharSequence Msg;
    private String Date;
    private boolean read;



    public MessSaveStruct(Boolean side, CharSequence msg, String Date, boolean Read) {
        this.Msg = msg;
        this.Side = side;
        this.Date = Date;
        this.read = Read;
    }

    public CharSequence getMsg() {
        return Msg;
    }

    public void setMsg(CharSequence msg) {
        Msg = msg;
    }

    public Boolean getSide() {
        return this.Side;
    }

    public void setSide(Boolean side) {
        this.Side = side;
    }


    public String getDate() {
        return this.Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


}
