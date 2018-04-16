package com.android.aksiem.eizzy.ui.order;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.OrderItemBinding;
import com.android.aksiem.eizzy.databinding.TimestampTitleItemBinding;
import com.android.aksiem.eizzy.ui.common.DataBoundListAdapter;
import com.android.aksiem.eizzy.util.Objects;
import com.android.aksiem.eizzy.vo.OrderItem;
import com.android.aksiem.eizzy.vo.TimestampedItemWrapper;

/**
 * Created by pdubey on 14/04/18.
 */

public class OrderItemsAdapter extends DataBoundListAdapter<TimestampedItemWrapper<OrderItem>, ViewDataBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final ItemClickCallback itemClickCallback;

    public OrderItemsAdapter(DataBindingComponent dataBindingComponent,
                             ItemClickCallback itemClickCallback) {

        this.dataBindingComponent = dataBindingComponent;
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {

        ViewDataBinding binding;

        if (isItem(viewType)) {

            binding = createOrderItemBinding(parent);

        } else {

            binding = createTimestampTitleItemBinding(parent);

        }

        return binding;
    }

    private OrderItemBinding createOrderItemBinding(ViewGroup parent) {
        OrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_item, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            OrderItem item = binding.getOrderItem();
            if (item != null && itemClickCallback != null) {
                itemClickCallback.onClick(item);
            }
        });
        return binding;
    }

    private TimestampTitleItemBinding createTimestampTitleItemBinding(ViewGroup parent) {
        TimestampTitleItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.timestamp_title_item, parent, false,
                dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            // TODO: Does anything happen on the click of timestamp?
        });
        return binding;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems().get(position).type.ordinal();
    }

    @Override
    protected void bind(ViewDataBinding binding, int viewType, TimestampedItemWrapper<OrderItem> item) {
        if (isItem(viewType)) {
            ((OrderItemBinding) binding).setOrderItem(item.item);
        } else {
            ((TimestampTitleItemBinding) binding).setTimestamp(item.timestamp);
        }
    }

    @Override
    protected boolean areItemsTheSame(TimestampedItemWrapper<OrderItem> oldItem,
                                      TimestampedItemWrapper<OrderItem> newItem) {

        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(TimestampedItemWrapper<OrderItem> oldItem,
                                         TimestampedItemWrapper<OrderItem> newItem) {

        if (oldItem.type != newItem.type) {
            return false;
        } else {
            if (oldItem.type == TimestampedItemWrapper.TimestampedItemWrapperType.ITEM) {
                return Objects.equals(oldItem.item, newItem.item) &&
                        Objects.equals(oldItem.item.customer, newItem.item.customer) &&
                        Objects.equals(oldItem.item.price, newItem.item.price) &&
                        Objects.equals(oldItem.item.orderDetails, newItem.item.orderDetails) &&
                        Objects.equals(oldItem.item.getOrderState(), newItem.item.getOrderState()) &&
                        Objects.equals(oldItem.item.getDeliveryAssociate(),
                                newItem.item.getDeliveryAssociate());
            } else {
                return oldItem.timestamp.equals(newItem.timestamp);
            }
        }
    }

    private boolean isItem(int viewType) {
        return viewType == TimestampedItemWrapper.TimestampedItemWrapperType.ITEM.ordinal();
    }

    public interface ItemClickCallback {
        void onClick(OrderItem item);
    }

}
