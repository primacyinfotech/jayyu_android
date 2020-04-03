package Model;

public class AddressModel {
    int add_id;
    String address_pref,name,address,zip_code,lanmark,phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLanmark() {
        return lanmark;
    }

    public void setLanmark(String lanmark) {
        this.lanmark = lanmark;
    }

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public String getAddress_pref() {
        return address_pref;
    }

    public void setAddress_pref(String address_pref) {
        this.address_pref = address_pref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
