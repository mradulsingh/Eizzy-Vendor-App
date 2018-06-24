package com.android.aksiem.eizzy.ui.settlement;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.repository.SettlementRepository;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.settlement.SettlementItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class SettlementViewModel extends ViewModel {

    private LiveData<Resource<List<SettlementItem>>> settlementItems;

    private MutableLiveData<String> storeId = new MutableLiveData<>();

    private SettlementRepository settlementRepository;

    private final NextPageHandler nextPageHandler;

    private int nextPageIndex = 0;

    private Long startDate = 0l;

    private Long endDate = 0l;

    public void setStoreId(String storeId) {
        this.storeId.setValue(storeId);
    }

    @Inject
    public SettlementViewModel(SettlementRepository settlementRepository) {
        this.nextPageHandler = new NextPageHandler(settlementRepository);
        this.settlementRepository = settlementRepository;
    }

    @VisibleForTesting
    public LiveData<Resource<List<SettlementItem>>> getSettlements() {
        settlementItems = Transformations.switchMap(settlementRepository.loadItems(
                nextPageIndex, startDate, endDate), (items) -> {

            MutableLiveData<Resource<List<SettlementItem>>> toReturn =
                    new MutableLiveData<>();

            if (items != null) {
                List<SettlementItem> list = new ArrayList<>();
                switch (items.status) {
                    case SUCCESS:
                        if (items.data != null && items.data.data != null
                                && !items.data.data.isEmpty()) {
                            list.addAll(items.data.data);
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

    @VisibleForTesting
    public void loadNextPage() {
        //nextPageHandler.getNextPage(nextPageIndex, startDate, endDate);
    }

    static class LoadMoreState {
        private final boolean running;
        private final String errorMessage;
        private boolean handledError = false;

        LoadMoreState(boolean running, String errorMessage) {
            this.running = running;
            this.errorMessage = errorMessage;
        }

        boolean isRunning() {
            return running;
        }

        String getErrorMessage() {
            return errorMessage;
        }

        String getErrorMessageIfNotHandled() {
            if (handledError) {
                return null;
            }
            handledError = true;
            return errorMessage;
        }
    }

    @VisibleForTesting
    static class NextPageHandler implements Observer<Resource<EizzyApiRespone<ArrayList<SettlementItem>>>> {
        @Nullable
        private LiveData<Resource<EizzyApiRespone<ArrayList<SettlementItem>>>> nextPageLiveData;
        private final MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
        private final SettlementRepository repository;

        @VisibleForTesting
        boolean hasMore;

        @VisibleForTesting
        NextPageHandler(SettlementRepository repository) {
            this.repository = repository;
            reset();
        }

        void getNextPage(int nextPageIndex, long startDate, long endDate) {
            unregister();
            nextPageLiveData = repository.getNextPage(nextPageIndex, startDate, endDate);
            loadMoreState.setValue(new LoadMoreState(true, null));
            nextPageLiveData.observeForever(this);
        }

        @Override
        public void onChanged(@Nullable Resource<EizzyApiRespone<ArrayList<SettlementItem>>> result) {
            if (result == null) {
                reset();
            } else {
                switch (result.status) {
                    case SUCCESS:
                        hasMore = Boolean.TRUE.equals(result.data);
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false, null));
                        break;
                    case ERROR:
                        hasMore = true;
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false,
                                result.message));
                        break;
                }
            }
        }

        private void unregister() {
            if (nextPageLiveData != null) {
                nextPageLiveData.removeObserver(this);
                nextPageLiveData = null;
            }
        }

        private void reset() {
            unregister();
            hasMore = true;
            loadMoreState.setValue(new LoadMoreState(false, null));
        }

        MutableLiveData<LoadMoreState> getLoadMoreState() {
            return loadMoreState;
        }
    }
}
