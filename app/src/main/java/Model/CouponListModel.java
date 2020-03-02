package Model;

public class CouponListModel {
    int coupon_id;
    String coupon_code,coupon_code_des,coupn_code_details,coupon_time,coupon_img;

   /* public CouponListModel(int coupon_img, String coupon_code, String coupon_code_des, String coupn_code_details, String coupon_time) {
        this.coupon_img = coupon_img;
        this.coupon_code = coupon_code;
        this.coupon_code_des = coupon_code_des;
        this.coupn_code_details = coupn_code_details;
        this.coupon_time = coupon_time;
    }*/

    public String getCoupn_code_details() {
        return coupn_code_details;
    }

    public void setCoupn_code_details(String coupn_code_details) {
        this.coupn_code_details = coupn_code_details;
    }


    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_img() {
        return coupon_img;
    }

    public void setCoupon_img(String coupon_img) {
        this.coupon_img = coupon_img;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_code_des() {
        return coupon_code_des;
    }

    public void setCoupon_code_des(String coupon_code_des) {
        this.coupon_code_des = coupon_code_des;
    }

    public String getCoupon_time() {
        return coupon_time;
    }

    public void setCoupon_time(String coupon_time) {
        this.coupon_time = coupon_time;
    }
}
