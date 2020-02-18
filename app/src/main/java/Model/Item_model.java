package Model;

public class Item_model {
    String mrp;
    String save_percent;
    String save_amt;
    String unit;
    String refund_nonrefund;

    public Item_model(String mrp, String save_percent, String save_amt, String unit, String refund_nonrefund) {
        this.mrp = mrp;
        this.save_percent = save_percent;
        this.save_amt = save_amt;
        this.unit = unit;
        this.refund_nonrefund = refund_nonrefund;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSave_percent() {
        return save_percent;
    }

    public void setSave_percent(String save_percent) {
        this.save_percent = save_percent;
    }

    public String getSave_amt() {
        return save_amt;
    }

    public void setSave_amt(String save_amt) {
        this.save_amt = save_amt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRefund_nonrefund() {
        return refund_nonrefund;
    }

    public void setRefund_nonrefund(String refund_nonrefund) {
        this.refund_nonrefund = refund_nonrefund;
    }
}
