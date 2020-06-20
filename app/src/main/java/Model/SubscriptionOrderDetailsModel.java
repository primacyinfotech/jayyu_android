package Model;

public class SubscriptionOrderDetailsModel {
    String Item_name, Item_unit, Item_qty, price_normal, mrp_price, com_name, normal_disc, composition;
    int Item_id;

    public String getItem_qty() {
        return Item_qty;
    }

    public void setItem_qty(String item_qty) {
        Item_qty = item_qty;
    }

    public int getItem_id() {
        return Item_id;
    }

    public void setItem_id(int item_id) {
        Item_id = item_id;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public String getItem_unit() {
        return Item_unit;
    }

    public void setItem_unit(String item_unit) {
        Item_unit = item_unit;
    }


    public String getPrice_normal() {
        return price_normal;
    }

    public void setPrice_normal(String price_normal) {
        this.price_normal = price_normal;
    }

    public String getMrp_price() {
        return mrp_price;
    }

    public void setMrp_price(String mrp_price) {
        this.mrp_price = mrp_price;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getNormal_disc() {
        return normal_disc;
    }

    public void setNormal_disc(String normal_disc) {
        this.normal_disc = normal_disc;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }
}
