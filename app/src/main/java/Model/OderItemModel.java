package Model;

public class OderItemModel {
    String Item_Name,Item_amt,Item_unit;

   /* public OderItemModel(String item_Name, String item_amt, String item_unit) {
        Item_Name = item_Name;
        Item_amt = item_amt;
        Item_unit = item_unit;
    }*/

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_amt() {
        return Item_amt;
    }

    public void setItem_amt(String item_amt) {
        Item_amt = item_amt;
    }

    public String getItem_unit() {
        return Item_unit;
    }

    public void setItem_unit(String item_unit) {
        Item_unit = item_unit;
    }
}
