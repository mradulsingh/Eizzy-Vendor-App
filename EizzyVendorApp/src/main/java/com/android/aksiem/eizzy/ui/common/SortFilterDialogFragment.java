package com.android.aksiem.eizzy.ui.common;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.databinding.SortFilterDialogFragmentBinding;
import com.android.aksiem.eizzy.util.AutoClearedValue;
import com.android.aksiem.eizzy.util.StringUtils;
import com.android.aksiem.eizzy.vo.FilterItem;
import com.android.aksiem.eizzy.vo.SortItem;
import com.android.aksiem.eizzy.vo.order.OrderTrackingStatusCode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SortFilterDialogFragment extends BaseSortFilterDialogFragment {

    AutoClearedValue<SortFilterDialogFragmentBinding> binding;

    @Inject
    AppResourceManager appResourceManager;

    private Long startDate;
    private Long endDate;
    private String stateFilter;

    private OnApplyFilter onApplyFilter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SortFilterDialogFragmentBinding dataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.sort_filter_dialog_fragment,
                null, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        final Dialog dialog = createDialog(binding.get().getRoot());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateDialogView();
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public String getStateFilter() {
        return stateFilter;
    }

    public void setOnApplyFilter(OnApplyFilter onApplyFilter) {
        this.onApplyFilter = onApplyFilter;
    }

    private void populateDates(long currentTimeInMillis) {
        startDate = 0l;
        endDate = 0l;
        for (SortItem item : getSortItems()) {
            String tag = item.getItem();
            RadioButton button = binding.get().sortItemsRadioGroup.findViewWithTag(tag);
            if (button.isChecked()) {
                switch (tag) {
                    case "Today":
                        startDate = StringUtils.getDayStart(currentTimeInMillis);
                        break;
                    case "Current Month":
                        startDate = StringUtils.getMonthStart(currentTimeInMillis);
                        break;
                    case "Last Month":
                        startDate = StringUtils.getPreviousMonthStart(currentTimeInMillis);
                        endDate = StringUtils.getMonthStart(currentTimeInMillis);
                        break;
                    case "This Year":
                        startDate = StringUtils.getYearStart(currentTimeInMillis);
                        break;
                }
            }
        }
    }

    private void populateFilters() {
        stateFilter = "100";
        ArrayList<String> filterParams = new ArrayList<>();
        for (FilterItem item : getFilterItems()) {
            String tag = item.getItem();
            CheckBox box = binding.get().filterItemViewGroup.findViewWithTag(tag);
            if (box.isChecked()) {
                switch (item.getItem()) {
                    case "Confirmed":
                        filterParams.add(OrderTrackingStatusCode.NEW_ORDER.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.ACCEPTED_BY_MANAGER.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.ORDER_READY.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.ORDER_COMPLETE.getStatusCode()
                                .toString());
                        break;
                    case "Assigned":
                        filterParams.add(OrderTrackingStatusCode.ACCEPTED_BY_DRIVER.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.ENROUTE_TO_STORE.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.AT_STORE.getStatusCode()
                                .toString());
                        break;
                    case "Picked":
                        filterParams.add(OrderTrackingStatusCode.ENROUTE_TO_CUSTOMER.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.CUSTOMER_LOCATION.getStatusCode()
                                .toString());
                        break;
                    case "Delivered":
                        filterParams.add(OrderTrackingStatusCode.DELIVERED.getStatusCode()
                                .toString());
                        filterParams.add(OrderTrackingStatusCode.ORDER_COMPLETE_DRIVER
                                .getStatusCode().toString());
                        break;
                }
            }
        }
        if (filterParams.size() > 0) {
            stateFilter = TextUtils.join(",", filterParams);
        }
    }

    private void updateDialogView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (SortItem sortItem : getSortItems()) {
            RadioButton radioButton = (RadioButton) inflater.inflate(
                    R.layout.sort_item_radio_button, null);
            radioButton.setText(sortItem.getItem());
            radioButton.setTag(sortItem.getItem());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT,
                    1f));
            binding.get().sortItemsRadioGroup.addView(radioButton);
        }

        for (FilterItem filterItem : getFilterItems()) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.filter_item_checkbox,
                    null);
            checkBox.setText(filterItem.getItem());
            checkBox.setTag(filterItem.getItem());
            binding.get().filterItemViewGroup.addView(checkBox);
        }

        binding.get().setPositiveBtnText(appResourceManager.getString(
                R.string.button_action_apply));
        binding.get().actionPositive.setOnClickListener(v -> {
            long currentTimeInMillis = System.currentTimeMillis();
            populateDates(currentTimeInMillis);
            populateFilters();
            dismiss();
            onApplyFilter.apply();
        });
    }

    private List<SortItem> getSortItems() {
        List<SortItem> sortItems = new ArrayList<>();
        String[] items = appResourceManager.getStringArray(R.array.filter_by_date_items);
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

    public interface OnApplyFilter {

        void apply();

    }
}
