package Model;

public class AssignPatientModel {
    int Assign_patient_id;
    String assign_patient_name;
    String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getAssign_patient_id() {
        return Assign_patient_id;
    }

    public void setAssign_patient_id(int assign_patient_id) {
        Assign_patient_id = assign_patient_id;
    }

    public String getAssign_patient_name() {
        return assign_patient_name;
    }

    public void setAssign_patient_name(String assign_patient_name) {
        this.assign_patient_name = assign_patient_name;
    }
}
