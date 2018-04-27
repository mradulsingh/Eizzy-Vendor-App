package com.android.aksiem.eizzy.vo;

public class SortItem {
    private String item;
    private boolean selected;

    public SortItem(String item, boolean selected) {
        this.item = item;
        this.selected = selected;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
