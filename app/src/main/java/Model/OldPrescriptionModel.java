package Model;

public class OldPrescriptionModel {
    int Old_Pres_id;
    String oldprescription_url;
    String old_prescription_date;
    private boolean isSelected;

    public int getOld_Pres_id() {
        return Old_Pres_id;
    }

    public void setOld_Pres_id(int old_Pres_id) {
        Old_Pres_id = old_Pres_id;
    }

    public String getOldprescription_url() {
        return oldprescription_url;
    }

    public void setOldprescription_url(String oldprescription_url) {
        this.oldprescription_url = oldprescription_url;
    }

    public String getOld_prescription_date() {
        return old_prescription_date;
    }

    public void setOld_prescription_date(String old_prescription_date) {
        this.old_prescription_date = old_prescription_date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
