package Model;

import android.widget.TextView;

import org.w3c.dom.Text;

public class OfferPageModel {
   private String offer_img,offer_heading,Offer_des,Offer_exp_date;

    public String getOffer_img() {
        return offer_img;
    }

    public void setOffer_img(String offer_img) {
        this.offer_img = offer_img;
    }

    public String getOffer_heading() {
        return offer_heading;
    }

    public void setOffer_heading(String offer_heading) {
        this.offer_heading = offer_heading;
    }

    public String getOffer_des() {
        return Offer_des;
    }

    public void setOffer_des(String offer_des) {
        Offer_des = offer_des;
    }

    public String getOffer_exp_date() {
        return Offer_exp_date;
    }

    public void setOffer_exp_date(String offer_exp_date) {
        Offer_exp_date = offer_exp_date;
    }
}
