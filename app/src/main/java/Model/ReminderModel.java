package Model;

public class ReminderModel {
    String medicin_name,track_time,numbeerOf_tablet;

    public ReminderModel(String medicin_name, String track_time, String numbeerOf_tablet) {
        this.medicin_name = medicin_name;
        this.track_time = track_time;
        this.numbeerOf_tablet = numbeerOf_tablet;
    }

    public String getMedicin_name() {
        return medicin_name;
    }

    public void setMedicin_name(String medicin_name) {
        this.medicin_name = medicin_name;
    }

    public String getTrack_time() {
        return track_time;
    }

    public void setTrack_time(String track_time) {
        this.track_time = track_time;
    }

    public String getNumbeerOf_tablet() {
        return numbeerOf_tablet;
    }

    public void setNumbeerOf_tablet(String numbeerOf_tablet) {
        this.numbeerOf_tablet = numbeerOf_tablet;
    }
}

