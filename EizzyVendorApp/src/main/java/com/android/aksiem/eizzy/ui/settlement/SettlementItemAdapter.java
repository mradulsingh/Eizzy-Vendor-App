package com.android.aksiem.eizzy.ui.settlement;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aksiem.eizzy.R;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

import java.util.List;


public class SettlementItemAdapter extends RecyclerView.Adapter<SettlementItemAdapter.PersonViewHolder> {

    private static final int HEADER = 0;
    private static final int NONHEADER = 1;
    List<SettlementItem> settlementItems;
    View view;
    static String TAG = SettlementItemAdapter.class.getSimpleName();

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView cus_tv_trans_id;

        PersonViewHolder(View itemView) {
            super(itemView);
            cus_tv_trans_id = (TextView) itemView.findViewById(R.id.cus_tv_trans_id);
        }
    }


    public SettlementItemAdapter(List<SettlementItem> settlementItems) {
        this.settlementItems = settlementItems;
        Log.e(TAG, " SettlementItemAdapter Constructor");

    }


    @Override
    public SettlementItemAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PersonViewHolder pvh = null;
        Log.e(TAG, " onCreateViewHolder");
        if (viewType == HEADER)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_settlement_header_element, parent, false);
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_settlement_element, parent, false);
        }
        pvh = new PersonViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(SettlementItemAdapter.PersonViewHolder personViewHolder, final int position) {
        if (position != HEADER) {
            Log.e(TAG, settlementItems.get(position - 1).transactionId + "<<");
            personViewHolder.cus_tv_trans_id.setText(settlementItems.get(position - 1).transactionId);

        }
    }


    @Override
    public int getItemCount() {

        return settlementItems.size() + 1;

    }

    @Override
    public int getItemViewType(int position) {

        if (position == HEADER) {
            return HEADER;
        }
        return NONHEADER;


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


}
