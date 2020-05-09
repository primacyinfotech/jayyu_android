package Model;

public class OrderStatusItemModel {
    int item_id,quantity,editTextValue;
    String title,price_normal,mrp_price,strip;

    private boolean isSelected;
    public int getItem_id() {
        return item_id;
    }

    public int getEditTextValue() {
        return editTextValue;
    }

    public void setEditTextValue(int editTextValue) {
        this.editTextValue = editTextValue;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStrip() {
        return strip;
    }

    public void setStrip(String strip) {
        this.strip = strip;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean getSelected() {
        return isSelected;
    }
}
