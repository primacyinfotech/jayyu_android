package Model;

public class PatientWisePrescriptionModel {
    String id, user_id, normal, patient_img;

    public String getPatient_img() {
        return patient_img;
    }

    public void setPatient_img(String patient_img) {
        this.patient_img = patient_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }
}
