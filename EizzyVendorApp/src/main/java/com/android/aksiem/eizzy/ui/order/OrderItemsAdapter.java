package com.android.aksiem.eizzy.ui.order;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.databinding.OrderListItemBinding;
import com.android.aksiem.eizzy.databinding.TimestampTitleItemBinding;
import com.android.aksiem.eizzy.ui.common.DataBoundListAdapter;
import com.android.aksiem.eizzy.ui.common.SortFilterDialogFragment;
import com.android.aksiem.eizzy.util.Objects;
import com.android.aksiem.eizzy.vo.order.OrderListItem;
import com.android.aksiem.eizzy.vo.order.TimestampedItemWrapper;

/**
 * Created by pdubey on 14/04/18.
 */

public class OrderItemsAdapter extends DataBoundListAdapter<TimestampedItemWrapper<OrderListItem>,
        ViewDataBinding> {

    private final AppResourceManager appResourceManager;
    private final DataBindingComponent dataBindingComponent;
    private final ItemClickCallback itemClickCallback;
    private final FilterClickCallback filterClickCallback;

    public OrderItemsAdapter(AppResourceManager appResourceManager,
                             DataBindingComponent dataBindingComponent,
                             ItemClickCallback itemClickCallback,
                             FilterClickCallback filterClickCallback) {

        this.appResourceManager = appResourceManager;
        this.dataBindingComponent = dataBindingComponent;
        this.itemClickCallback = itemClickCallback;
        this.filterClickCallback = filterClickCallback;
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

    private OrderListItemBinding createOrderItemBinding(ViewGroup parent) {
        OrderListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.order_list_item, parent, false,
                dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            OrderListItem item = binding.getItem();
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
        binding.timestamp.setOnClickListener(v -> {
            // TODO: Does anything happen on the click of timestamp?
        });
        binding.filter.setOnClickListener(v -> {
            filterClickCallback.onClick();
        });
        return binding;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems().get(position).type.ordinal();
    }

    @Override
    protected void bind(ViewDataBinding binding, int viewType, int position) {
        TimestampedItemWrapper<OrderListItem> item = getItems().get(position);
        if (isItem(viewType)) {
            OrderListItemBinding orderItemBinding = ((OrderListItemBinding) binding);
            orderItemBinding.setItem(item.item);
            orderItemBinding.setResourceManager(appResourceManager);
        } else {
            TimestampTitleItemBinding timestampItemBinding = (TimestampTitleItemBinding) binding;
            timestampItemBinding.setTimestamp(item.timestamp);
            timestampItemBinding.setPosition(position);
        }
    }

    @Override
    protected boolean areItemsTheSame(TimestampedItemWrapper<OrderListItem> oldItem,
                                      TimestampedItemWrapper<OrderListItem> newItem) {

        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(TimestampedItemWrapper<OrderListItem> oldItem,
                                         TimestampedItemWrapper<OrderListItem> newItem) {

        if (oldItem.type != newItem.type) {
            return false;
        } else {
            if (oldItem.type == TimestampedItemWrapper.TimestampedItemWrapperType.ITEM) {
                return Objects.equals(oldItem.item, newItem.item);
            } else {
                return oldItem.timestamp.equals(newItem.timestamp);
            }
        }
    }

    private boolean isItem(int viewType) {
        return viewType == TimestampedItemWrapper.TimestampedItemWrapperType.ITEM.ordinal();
    }

    public interface ItemClickCallback {
        void onClick(OrderListItem item);
    }

    public interface FilterClickCallback {
        void onClick();
    }

}
