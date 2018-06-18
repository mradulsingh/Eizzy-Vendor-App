package com.android.aksiem.eizzy.ui.settlement;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.SettlementRepository;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class SettlementViewModel extends ViewModel {

    private LiveData<Resource<List<SettlementItem>>> settlementItems;

    private MutableLiveData<String> storeId = new MutableLiveData<>();

    private SettlementRepository settlementRepository;

    private Long pageIndex = 0l;

    private Long startDate = 0l;

    private Long endDate = 0l;

    public void setStoreId(String storeId) {
        this.storeId.setValue(storeId);
    }

    @Inject
    public SettlementViewModel(SettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }

    @VisibleForTesting
    public LiveData<Resource<List<SettlementItem>>> getSettlements() {
        settlementItems = Transformations.switchMap(settlementRepository.loadItems(
                pageIndex, startDate, endDate), (items) -> {

            MutableLiveData<Resource<List<SettlementItem>>> toReturn =
                    new MutableLiveData<>();

            if (items != null) {
                List<SettlementItem> list = new ArrayList<>();
                switch (items.status) {
                    case SUCCESS:
                        if (items.data != null && items.data.data != null
                                && items.data.data.items != null
                                && !items.data.data.items.isEmpty()) {
                            list.addAll(items.data.data.items);
                        }
                        break;
                }
                Resource<List<SettlementItem>> resource = new Resource<>(
                        items.status, list, items.message);
                toReturn.setValue(resource);
                return toReturn;
            }
            return AbsentLiveData.create();
        });
        return settlementItems;
    }
}
