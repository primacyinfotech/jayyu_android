package Model;

public class OrderSummeryModel {
    String cart_item,unit,saveings_percentage,save_amount,total,price_amt,quantity,company_name;
    int qty,oder_id,item_id;

    /*public OrderSummeryModel(String cart_item, String unit, String saveings_percentage, String save_amount, String total) {
        this.cart_item = cart_item;
        this.unit = unit;
        this.saveings_percentage = saveings_percentage;
        this.save_amount = save_amount;
        this.total = total;
    }*/

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getPrice_amt() {
        return price_amt;
    }

    public void setPrice_amt(String price_amt) {
        this.price_amt = price_amt;
    }

    public String getCart_item() {
        return cart_item;
    }

    public void setCart_item(String cart_item) {
        this.cart_item = cart_item;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSaveings_percentage() {
        return saveings_percentage;
    }

    public void setSaveings_percentage(String saveings_percentage) {
        this.saveings_percentage = saveings_percentage;
    }

    public String getSave_amount() {
        return save_amount;
    }

    public void setSave_amount(String save_amount) {
        this.save_amount = save_amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getOder_id() {
        return oder_id;
    }

    public void setOder_id(int oder_id) {
        this.oder_id = oder_id;
    }
}
