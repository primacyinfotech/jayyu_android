package Model;

public class PatientPastPrescriptionModel {
    String past_pres_head,past_pres_date,past_pres_img;
    int patient_pres_id;

    public int getPatient_pres_id() {
        return patient_pres_id;
    }

    public void setPatient_pres_id(int patient_pres_id) {
        this.patient_pres_id = patient_pres_id;
    }

    public String getPast_pres_head() {
        return past_pres_head;
    }

    public void setPast_pres_head(String past_pres_head) {
        this.past_pres_head = past_pres_head;
    }

    public String getPast_pres_date() {
        return past_pres_date;
    }

    public void setPast_pres_date(String past_pres_date) {
        this.past_pres_date = past_pres_date;
    }

    public String getPast_pres_img() {
        return past_pres_img;
    }

    public void setPast_pres_img(String past_pres_img) {
        this.past_pres_img = past_pres_img;
    }
}
