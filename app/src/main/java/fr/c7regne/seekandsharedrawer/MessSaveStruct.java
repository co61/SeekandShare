package fr.c7regne.seekandsharedrawer;

public class MessSaveStruct {
    private Boolean Side;
    private CharSequence Msg;


    public MessSaveStruct(Boolean side, CharSequence msg) {
        this.Msg = msg;
        this.Side = side;
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



}
