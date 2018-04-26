package com.luuva.model;

/**
 * Created by NhuongPH on 4/13/2018.
 */

public class Setting {
    private String setting;
    private int icon;

    public Setting(String setting, int icon) {
        this.setting = setting;
        this.icon = icon;
    }

    public String getSetting() {
        return setting;
    }

    public int getIcon() {
        return icon;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
