package Model;

public class SubscriptionOrderDetailsModel {
     String Item_name,Item_unit,Item_qty;
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


}
