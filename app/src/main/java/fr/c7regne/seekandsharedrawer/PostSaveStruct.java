package fr.c7regne.seekandsharedrawer;

public class PostSaveStruct {
    private CharSequence Title;
    private CharSequence Content;
    private String DPchoice;
    private String SPchoice;


    public CharSequence getTitle() {
        return Title;
    }

    public void setTitle(CharSequence title) {
        Title = title;
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

    public PostSaveStruct() {
    }
}
