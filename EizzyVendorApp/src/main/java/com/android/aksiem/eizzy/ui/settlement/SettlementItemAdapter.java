package com.android.aksiem.eizzy.ui.settlement;

import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.ui.common.DataBoundListAdapter;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;


public class SettlementItemAdapter extends DataBoundListAdapter<TimestampedItemWrapper<SettlementItem>, ViewDataBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final ItemClickCallback itemClickCallback;

    public SettlementItemAdapter(DataBindingComponent dataBindingComponent,
                                 ItemClickCallback itemClickCallback) {

        this.dataBindingComponent = dataBindingComponent;
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {

        ViewDataBinding binding = null;

        if (isItem(viewType)) {

            //binding = createOldSettlementItemBinding(parent);

        } else {

            //binding = createSettlementOverviewBinding(parent);

        }

        return binding;
    }

//    private OrderItemBinding createOldSettlementItemBinding(ViewGroup parent) {
//        OrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.old_settlement_element, parent, false, dataBindingComponent);
//        binding.getRoot().setOnClickListener(v -> {
//            OrderItem item = binding.getOrderItem();
//            if (item != null && itemClickCallback != null) {
//                itemClickCallback.onClick(item);
//            }
//        });
//        return binding;
//        return null;
//    }

//    private TimestampTitleItemBinding createSettlementOverviewBinding(ViewGroup parent) {
//        TimestampTitleItemBinding binding = DataBindingUtil.inflate(
//                LayoutInflater.from(parent.getContext()),
//                R.layout.settlement_overview_element, parent, false,
//                dataBindingComponent);
//        binding.getRoot().setOnClickListener(v -> {
//            // TODO: Does anything happen on the click of timestamp?
//        });
//        return binding;
//        return null;
//    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        else {
            return 2;
        }
    }

    @Override
    protected void bind(ViewDataBinding binding, int viewType, TimestampedItemWrapper<SettlementItem> item) {
        if (isItem(viewType)) {
            //((OrderItemBinding) binding).setOrderItem(item.item);
        } else {
           // ((TimestampTitleItemBinding) binding).setTimestamp(Long.toString(item.timestamp));
        }
    }

    @Override
    protected boolean areItemsTheSame(TimestampedItemWrapper<SettlementItem> oldItem, TimestampedItemWrapper<SettlementItem> newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(TimestampedItemWrapper<SettlementItem> oldItem, TimestampedItemWrapper<SettlementItem> newItem) {
        return false;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    private boolean isItem(int viewType) {
        //  return viewType == TimestampedItemWrapper.TimestampedItemWrapperType.ITEM.ordinal();
        return true; // TODO check overview
    }

    public interface ItemClickCallback {
        void onClick(OrderItem item);
    }

}
