package com.android.aksiem.eizzy.ui.common;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.databinding.SortFilterDialogFragmentBinding;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.vo.FilterItem;
import com.android.aksiem.eizzy.vo.SortItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SortFilterDialogFragment extends BaseSortFilterDialogFragment {

    AutoClearedValue<SortFilterDialogFragmentBinding> binding;

    @Inject
    AppResourceManager appResourceManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SortFilterDialogFragmentBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.sort_filter_dialog_fragment, null, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        final Dialog dialog = createDialog(binding.get().getRoot());
        dialog.show();
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateDialogView();
    }

    private void updateDialogView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (SortItem sortItem : getSortItems()) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.sort_item_radio_button, null);
            radioButton.setText(sortItem.getItem());
            radioButton.setSelected(sortItem.isSelected());
            binding.get().sortItemsRadioGroup.addView(radioButton);
        }

        for (FilterItem filterItem : getFilterItems()) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.filter_item_checkbox, null);
            checkBox.setText(filterItem.getItem());
            checkBox.setChecked(filterItem.isChecked());
            binding.get().filterItemViewGroup.addView(checkBox);
        }

        binding.get().setPositiveBtnText(appResourceManager.getString(R.string.button_action_apply));
    }

    private List<SortItem> getSortItems() {
        List<SortItem> sortItems = new ArrayList<>();
        String[] items = appResourceManager.getStringArray(R.array.orders_sort_items);
        for (int i = 0; i < items.length; i++) {
            sortItems.add(new SortItem(items[i], i == 0 ? true : false));
        }
        return sortItems;
    }

    private List<FilterItem> getFilterItems() {
        List<FilterItem> filterItems = new ArrayList<>();
        String[] items = appResourceManager.getStringArray(R.array.orders_filter_items);
        for (int i = 0; i < items.length; i++) {
            filterItems.add(new FilterItem(items[i], i == 0 ? true : false));
        }
        return filterItems;
    }
}
