package Model;

public class HelpModel {
    private String helpitem;
    private int help_id;

    public HelpModel() {

    }

    public int getHelp_id() {
        return help_id;
    }

    public void setHelp_id(int help_id) {
        this.help_id = help_id;
    }

    public HelpModel(String helpitem) {
        this.helpitem = helpitem;
    }

    public String getHelpitem() {
        return helpitem;
    }

    public void setHelpitem(String helpitem) {
        this.helpitem = helpitem;
    }
}
