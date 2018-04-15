package com.android.aksiem.eizzy.ui.order;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.util.Objects;
import com.android.aksiem.eizzy.vo.OrderItem;

/**
 * Created by pdubey on 14/04/18.
 */

public class OrderItemsAdapter extends DataBoundListAdapter<OrderItem, OrderItemBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final OrderClickCallback orderClickCallback;

    public OrderItemsAdapter(DataBindingComponent dataBindingComponent,
                             OrderClickCallback orderClickCallback) {

        this.dataBindingComponent = dataBindingComponent;
        this.orderClickCallback = orderClickCallback;
    }

    @Override
    protected OrderItemBinding createBinding(ViewGroup parent) {
        OrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_item, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            OrderItem orderItem = binding.getOrder();
            if (orderItem != null && orderClickCallback != null) {
                orderClickCallback.onClick(orderItem);
            }
        });
        return binding;
    }

    @Override
    protected void bind(OrderItemBinding binding, OrderItem item) {
        binding.setOrder(item);
    }

    @Override
    protected boolean areItemsTheSame(OrderItem oldItem, OrderItem newItem) {
        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(OrderItem oldItem, OrderItem newItem) {
        return Objects.equals(oldItem, newItem) &&
                Objects.equals(oldItem.customer, newItem.customer) &&
                Objects.equals(oldItem.price, newItem.price) &&
                Objects.equals(oldItem.orderDetails, newItem.orderDetails) &&
                Objects.equals(oldItem.getDeliveryAssociate(), newItem.getDeliveryAssociate()) &&
                Objects.equals(oldItem.getCurrentOrderState(), newItem.getCurrentOrderState());
    }

    public interface OrderClickCallback {
        void onClick(OrderItem contributor);
    }
}
