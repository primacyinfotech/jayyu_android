package Model;

public class OrderModel {
 private     String order_mame,order_id,order_date,ship_status,instant;
  private   int tbl_order_id;
   /* public OrderModel(String order_id, String order_date, String order_mame) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_mame = order_mame;
    }*/

    public String getInstant() {
        return instant;
    }

    public void setInstant(String instant) {
        this.instant = instant;
    }

    public String getShip_status() {
        return ship_status;
    }

    public void setShip_status(String ship_status) {
        this.ship_status = ship_status;
    }

    public int getTbl_order_id() {
        return tbl_order_id;
    }

    public void setTbl_order_id(int tbl_order_id) {
        this.tbl_order_id = tbl_order_id;
    }

    public String getOrder_mame() {
        return order_mame;
    }

    public void setOrder_mame(String order_mame) {
        this.order_mame = order_mame;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}
