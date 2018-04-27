package com.android.aksiem.eizzy.vo;

public class FilterItem {

    private String item;
    private boolean checked;

    public FilterItem(String item, boolean checked) {
        this.item = item;
        this.checked = checked;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
