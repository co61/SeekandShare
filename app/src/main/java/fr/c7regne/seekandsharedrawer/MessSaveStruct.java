package fr.c7regne.seekandsharedrawer;

public class MessSaveStruct {
    private Boolean Side;
    private CharSequence Msg;
    private String Date;


    public MessSaveStruct(Boolean side, CharSequence msg, String Date) {
        this.Msg = msg;
        this.Side = side;
        this.Date = Date;
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



}
