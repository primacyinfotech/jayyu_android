package Model;

public class SuscriptionFetureNodel {
    int feture_img;
    String feture_titel,feture_content;

    public SuscriptionFetureNodel(int feture_img, String feture_titel, String feture_content) {
        this.feture_img = feture_img;
        this.feture_titel = feture_titel;
        this.feture_content = feture_content;
    }

    public int getFeture_img() {
        return feture_img;
    }

    public void setFeture_img(int feture_img) {
        this.feture_img = feture_img;
    }

    public String getFeture_titel() {
        return feture_titel;
    }

    public void setFeture_titel(String feture_titel) {
        this.feture_titel = feture_titel;
    }

    public String getFeture_content() {
        return feture_content;
    }

    public void setFeture_content(String feture_content) {
        this.feture_content = feture_content;
    }
}
