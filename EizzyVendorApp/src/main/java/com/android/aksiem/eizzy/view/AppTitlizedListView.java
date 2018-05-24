package com.android.aksiem.eizzy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.TitleSubtitleRowHorizontalBinding;
import com.android.aksiem.eizzy.vo.support.TitledAndSubtitled;

import java.util.ArrayList;
import java.util.List;

public class AppTitlizedListView extends RelativeLayout {

    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    private static final int DEFAULT_COLOR_VALUE = -1;
    private static final float DEFAULT_DIMENSION_VALUE = -1f;

    private RelativeLayout rootView;
    private TextView titleTextView;
    private ListView listView;

    private String lvTitle;
    private int lvTitleColor;
    private float lvTitleSize;
    private boolean lvTitleBold;
    private boolean lvTitleItalics;
    private int lvTitlePaddingBottom;
    private float lvItemTitleSize;
    private int lvItemTitleColor;
    private float lvItemSubtitleSize;
    private int lvItemSubtitleColor;

    private List<? extends TitledAndSubtitled> items;
    private ArrayAdapter<TitledAndSubtitled> adapter;

    public AppTitlizedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppTitlizedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        initAttrs(context, attributeSet);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray a = context.obtainStyledAttributes(R.styleable.AppTitlizedListView);
            lvTitle = a.getString(R.styleable.AppTitlizedListView_lvTitle);
            lvTitleColor = a.getColor(R.styleable.AppTitlizedListView_lvTitleColor,
                    DEFAULT_COLOR_VALUE);
            lvTitleSize = a.getDimension(R.styleable.AppTitlizedListView_lvTitleSize,
                    DEFAULT_DIMENSION_VALUE);
            lvTitleBold = a.getBoolean(R.styleable.AppTitlizedListView_lvTitleBold,
                    DEFAULT_BOOLEAN_VALUE);
            lvTitleItalics = a.getBoolean(R.styleable.AppTitlizedListView_lvTitleItalics,
                    DEFAULT_BOOLEAN_VALUE);
            lvTitlePaddingBottom = (int) a.getDimension(
                    R.styleable.AppTitlizedListView_lvTitlePaddingBottom, DEFAULT_DIMENSION_VALUE);
            lvItemTitleSize = a.getDimension(R.styleable.AppTitlizedListView_lvItemTitleSize,
                    DEFAULT_DIMENSION_VALUE);
            lvItemTitleColor = a.getColor(R.styleable.AppTitlizedListView_lvItemTitleColor,
                    DEFAULT_COLOR_VALUE);
            lvItemSubtitleSize = a.getDimension(R.styleable.AppTitlizedListView_lvItemSubtitleSize,
                    DEFAULT_DIMENSION_VALUE);
            lvItemSubtitleColor = a.getColor(R.styleable.AppTitlizedListView_lvItemSubtitleColor,
                    DEFAULT_COLOR_VALUE);
            a.recycle();
        }
    }

    private void initView(Context context) {
        rootView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.app_titlized_list_view, this, true);
        titleTextView = rootView.findViewById(R.id.atlvTitle);
        setLvTitle(lvTitle);
        setLvTitleColor(lvTitleColor);
        setLvTitleSize(lvTitleSize);
        manageTitleTextStyling();
        listView = rootView.findViewById(R.id.atlvList);
        adapter = new DefaultAdapter(context, R.layout.title_subtitle_row_horizontal,
                new ArrayList<>());
        listView.setAdapter(adapter);
    }

    public String getLvTitle() {
        return lvTitle;
    }

    public void setLvTitle(String lvTitle) {
        this.lvTitle = lvTitle;
        titleTextView.setText(this.lvTitle);
    }

    public int getLvTitleColor() {
        return lvTitleColor;
    }

    public void setLvTitleColor(int lvTitleColor) {
        this.lvTitleColor = lvTitleColor;
        titleTextView.setTextColor(this.lvTitleColor);
    }

    public float getLvTitleSize() {
        return lvTitleSize;
    }

    public void setLvTitleSize(float lvTitleSize) {
        this.lvTitleSize = lvTitleSize;
        titleTextView.setTextSize(this.lvTitleSize);
    }

    public boolean isLvTitleBold() {
        return lvTitleBold;
    }

    public void setLvTitleBold(boolean lvTitleBold) {
        this.lvTitleBold = lvTitleBold;
        manageTitleTextStyling();
    }

    public boolean isLvTitleItalics() {
        return lvTitleItalics;
    }

    public void setLvTitleItalics(boolean lvTitleItalics) {
        this.lvTitleItalics = lvTitleItalics;
        manageTitleTextStyling();
    }

    private void manageTitleTextStyling() {
        if (lvTitleBold && lvTitleItalics) {
            titleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        } else if (lvTitleBold && !lvTitleItalics) {
            titleTextView.setTypeface(null, Typeface.BOLD);
        } else if (!lvTitleBold && lvTitleItalics) {
            titleTextView.setTypeface(null, Typeface.ITALIC);
        } else {
            titleTextView.setTypeface(null, Typeface.NORMAL);
        }
    }

    public int getLvTitlePaddingBottom() {
        return lvTitlePaddingBottom;
    }

    public void setLvTitlePaddingBottom(int lvTitlePaddingBottom) {
        this.lvTitlePaddingBottom = lvTitlePaddingBottom;
        titleTextView.setPadding(0, 0, 0, this.lvTitlePaddingBottom);
    }

    public float getLvItemTitleSize() {
        return lvItemTitleSize;
    }

    public void setLvItemTitleSize(float lvItemTitleSize) {
        this.lvItemTitleSize = lvItemTitleSize;
    }

    public int getLvItemTitleColor() {
        return lvItemTitleColor;
    }

    public void setLvItemTitleColor(int lvItemTitleColor) {
        this.lvItemTitleColor = lvItemTitleColor;
    }

    public float getLvItemSubtitleSize() {
        return lvItemSubtitleSize;
    }

    public void setLvItemSubtitleSize(float lvItemSubtitleSize) {
        this.lvItemSubtitleSize = lvItemSubtitleSize;
    }

    public int getLvItemSubtitleColor() {
        return lvItemSubtitleColor;
    }

    public void setLvItemSubtitleColor(int lvItemSubtitleColor) {
        this.lvItemSubtitleColor = lvItemSubtitleColor;
    }

    public List<? extends TitledAndSubtitled> getItems() {
        return items;
    }

    public void setItems(List<? extends TitledAndSubtitled> items) {
        this.items = items;
        adapter.clear();
        adapter.addAll(this.items);
    }

    protected class DefaultAdapter extends ArrayAdapter<TitledAndSubtitled> {

        public DefaultAdapter(@NonNull Context context, int resource,
                              @NonNull List<TitledAndSubtitled> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TitleSubtitleRowHorizontalBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()), R.layout.title_subtitle_row_horizontal,
                    parent, false);
            binding.setRow(getItem(position));
            manageStyling(binding);
            return binding.getRoot();
        }

        private void manageStyling(TitleSubtitleRowHorizontalBinding binding) {
            if (lvItemTitleSize != DEFAULT_DIMENSION_VALUE)
                binding.title.setTextSize(lvItemTitleSize);
            if (lvItemTitleColor != DEFAULT_COLOR_VALUE)
                binding.title.setTextColor(lvItemTitleColor);
            if (lvItemSubtitleSize != DEFAULT_DIMENSION_VALUE)
                binding.subtitle.setTextSize(lvItemSubtitleSize);
            if (lvItemSubtitleColor != DEFAULT_COLOR_VALUE)
                binding.subtitle.setTextColor(lvItemSubtitleColor);
        }
    }
}
