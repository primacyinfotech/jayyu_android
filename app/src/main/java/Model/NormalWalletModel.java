package Model;

public class NormalWalletModel {
    String remark,debit,credit,date_val;

    public String getDate_val() {
        return date_val;
    }

    public void setDate_val(String date_val) {
        this.date_val = date_val;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
