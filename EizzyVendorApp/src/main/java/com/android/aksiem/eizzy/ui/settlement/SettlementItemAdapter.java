package com.android.aksiem.eizzy.ui.settlement;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.databinding.SettlementItemBinding;
import com.android.aksiem.eizzy.ui.common.DataBoundListAdapter;
import com.android.aksiem.eizzy.util.Objects;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;


public class SettlementItemAdapter extends DataBoundListAdapter<SettlementItem,
        SettlementItemBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final ItemClickCallback itemClickCallback;

    @Override
    protected SettlementItemBinding createBinding(ViewGroup parent, int viewType) {
        return createSettlementItemBinding(parent);
    }

    public SettlementItemAdapter(DataBindingComponent dataBindingComponent,
                                 ItemClickCallback itemClickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.itemClickCallback = itemClickCallback;
    }

    private SettlementItemBinding createSettlementItemBinding(ViewGroup parent) {
        SettlementItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.settlement_item, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            SettlementItem item = binding.getSettlementItem();
            if (item != null && itemClickCallback != null) {
                itemClickCallback.onClick(item);
            }
        });
        return binding;
    }

    @Override
    protected void bind(SettlementItemBinding binding, int viewType, int position) {
        binding.setSettlementItem(getItems().get(position));
    }

    @Override
    protected boolean areItemsTheSame(SettlementItem oldItem,
                                      SettlementItem newItem) {
        return Objects.equals(oldItem, newItem);
    }

    @Override
    protected boolean areContentsTheSame(SettlementItem oldItem,
                                         SettlementItem newItem) {
        return Objects.equals(oldItem.getTransactionId(), newItem.getTransactionId());
    }

    public interface ItemClickCallback {
        void onClick(SettlementItem item);
    }
}
