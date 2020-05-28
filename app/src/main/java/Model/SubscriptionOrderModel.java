package Model;

public class SubscriptionOrderModel {
    String subscription_order,ord_date;
    int subscription_tbl_id;

   /* public SubscriptionOrderModel(String subscription_order, String ord_date) {
        this.subscription_order = subscription_order;
        this.ord_date = ord_date;
    }*/

    public int getSubscription_tbl_id() {
        return subscription_tbl_id;
    }

    public void setSubscription_tbl_id(int subscription_tbl_id) {
        this.subscription_tbl_id = subscription_tbl_id;
    }

    public String getSubscription_order() {
        return subscription_order;
    }

    public void setSubscription_order(String subscription_order) {
        this.subscription_order = subscription_order;
    }

    public String getOrd_date() {
        return ord_date;
    }

    public void setOrd_date(String ord_date) {
        this.ord_date = ord_date;
    }
}
