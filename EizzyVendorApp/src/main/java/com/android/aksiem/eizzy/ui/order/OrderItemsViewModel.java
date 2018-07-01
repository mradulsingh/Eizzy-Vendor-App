package com.android.aksiem.eizzy.ui.order;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.android.aksiem.eizzy.app.AppPrefManager;
import com.android.aksiem.eizzy.app.AppResourceManager;
import com.android.aksiem.eizzy.repository.OrderItemsRepository;
import com.android.aksiem.eizzy.util.AbsentLiveData;
import com.android.aksiem.eizzy.util.StringUtils;
import com.android.aksiem.eizzy.vo.EizzyApiRespone;
import com.android.aksiem.eizzy.vo.order.EizzyZone;
import com.android.aksiem.eizzy.vo.order.OrderDetailItem;
import com.android.aksiem.eizzy.vo.order.OrderListItem;
import com.android.aksiem.eizzy.vo.RequestConstants;
import com.android.aksiem.eizzy.vo.Resource;
import com.android.aksiem.eizzy.vo.order.OrderListWrapper;
import com.android.aksiem.eizzy.vo.order.TimestampedItemWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by pdubey on 09/04/18.
 */

public class OrderItemsViewModel extends ViewModel {

    private LiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> dummyTimestampedOrderItems;

    private LiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> timestampedOrderItems;


    private MutableLiveData<List<String>> orderIds = new MutableLiveData<>();

    private OrderItemsRepository orderItemsRepository;

    private final NextPageHandler nextPageHandler;

    private MutableLiveData<Integer> nextPageIndex = new MutableLiveData<>();

    private Integer pageIndex = 0;

    private Long status = RequestConstants.OrderItemsList.all;

    private Long startDate = 0l;

    private Long endDate = 0l;

    private String stateFilter = "100";

    private String orderIdToFetch;

    @Inject
    AppResourceManager appResourceManager;

    @Inject
    AppPrefManager appPrefManager;

    @Inject
    public OrderItemsViewModel(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
        nextPageHandler = new NextPageHandler(this.orderItemsRepository);
        nextPageIndex.setValue(pageIndex);
    }


    public LiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> getDummyOrderItems() {
        dummyTimestampedOrderItems = Transformations.switchMap(
                orderItemsRepository.loadDummyItems(),
                (items) -> {
                    if (items != null) {
                        List<OrderDetailItem> orderDetailItems = new ArrayList<>();
                        orderDetailItems.addAll(items.data);
                        Resource<List<OrderDetailItem>> resource = new Resource<>(items.status,
                                orderDetailItems, items.message);
                        return addTimestampToList(resource);
                    }
                    return AbsentLiveData.create();
                });
        return dummyTimestampedOrderItems;
    }

    public LiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> getAllOrderItems() {

        timestampedOrderItems = Transformations.switchMap(orderItemsRepository.loadItemsToList(
                pageIndex, status, stateFilter, startDate, endDate), (items) -> transform(items));
        return timestampedOrderItems;
    }

    public LiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> getAllItemsWithoutDb() {
        timestampedOrderItems = Transformations.switchMap(orderItemsRepository
                .loadItemsToListWithoutDb(pageIndex, status, stateFilter, startDate, endDate),
                (items) -> transform(items));
        return timestampedOrderItems;
    }

    public LiveData<Resource<EizzyApiRespone<OrderDetailItem>>> getDetailedItem() {
        if (orderIdToFetch == null) {
            return AbsentLiveData.create();
        }
        orderItemsRepository.setShouldGetDetailedItem(true);
        return orderItemsRepository.getDetailedItem(orderIdToFetch);
    }

    public LiveData<Resource<EizzyApiRespone<ArrayList<EizzyZone>>>> getEizzyZones() {
        return orderItemsRepository.getEizzyZones();
    }

    @VisibleForTesting
    public LiveData<LoadMoreState> getLoadMoreStatus() {
        return nextPageHandler.getLoadMoreState();
    }

    @VisibleForTesting
    public void loadNextPage() {
        if (nextPageHandler.hasMore && nextPageIndex.getValue() >= 0) {
            nextPageHandler.getNextPage(nextPageIndex, status, stateFilter, startDate, endDate);
        }
    }

    public void setOrderIds(List<String> listOrderIds) {
        this.orderIds.setValue(listOrderIds);
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public void setStateFilter(String stateFilter) {
        this.stateFilter = stateFilter;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public void setOrderIdToFetch(String orderIdToFetch) {
        this.orderIdToFetch = orderIdToFetch;
    }

    private LiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> transform(
            Resource<EizzyApiRespone<OrderListWrapper>> items) {

        MutableLiveData<Resource<List<TimestampedItemWrapper<OrderListItem>>>> toReturn =
                new MutableLiveData<>();

        if (items != null) {
            List<TimestampedItemWrapper<OrderListItem>> list = new ArrayList<>();
            switch (items.status) {
                case SUCCESS:
                    if (items.data != null && items.data.data != null
                            && items.data.data.items != null
                            && !items.data.data.items.isEmpty()) {

                        String prevTimestampString = null;

                        for (OrderListItem item : items.data.data.items) {
                            String timestampString = StringUtils.getTimestamp(
                                    item.getTimestamp(), appResourceManager);
                            if (prevTimestampString == null ||
                                    !prevTimestampString.equals(timestampString)) {
                                list.add(new TimestampedItemWrapper<>(timestampString,
                                        null));
                                prevTimestampString = timestampString;
                            }
                            list.add(new TimestampedItemWrapper<>(null, item));
                        }
                    }
                    break;
            }
            Resource<List<TimestampedItemWrapper<OrderListItem>>> resource = new Resource<>(
                    items.status, list, items.message);
            toReturn.setValue(resource);
            return toReturn;
        }
        return AbsentLiveData.create();
    }

    private LiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> addTimestampToList(
            Resource<List<OrderDetailItem>> liveDataItems) {

        List<OrderDetailItem> items = liveDataItems.data;
        List<TimestampedItemWrapper<OrderDetailItem>> timestampedItemWrappers = new ArrayList<>();

        if (items != null) {

            for (OrderDetailItem item : items) {
                timestampedItemWrappers.add(new TimestampedItemWrapper<>(
                        null, item));
            }

            MutableLiveData<Resource<List<TimestampedItemWrapper<OrderDetailItem>>>> orderItemWMLD =
                    new MutableLiveData<>();
            orderItemWMLD.setValue(Resource.success(timestampedItemWrappers));
            return orderItemWMLD;

        } else {

            return AbsentLiveData.create();

        }
    }

    public static class LoadMoreState {

        private final boolean running;
        private final String errorMessage;
        private boolean handleError = false;

        public LoadMoreState(boolean running, String errorMessage) {
            this.running = running;
            this.errorMessage = errorMessage;
        }

        public boolean isRunning() {
            return running;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getErrorMessageIfNotHandled() {
            if (handleError) {
                return null;
            }
            handleError = true;
            return errorMessage;
        }
    }

    @VisibleForTesting
    public static class NextPageHandler implements Observer<Resource<Boolean>> {

        @Nullable
        private LiveData<Resource<Boolean>> nextPageLiveData;
        private final MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
        private final OrderItemsRepository repository;
        private MutableLiveData<Integer> nextPageIndex;

        @VisibleForTesting
        boolean hasMore;

        public NextPageHandler(OrderItemsRepository repository) {
            this.repository = repository;
            reset();
        }

        public void getNextPage(MutableLiveData<Integer> nextPageIndex, long status,
                                String stateFilter, long startDate, long endDate) {

            unregister();
            this.nextPageIndex = nextPageIndex;
            nextPageLiveData = repository.getNextPage(nextPageIndex.getValue(), status, stateFilter,
                    startDate, endDate);
            loadMoreState.setValue(new LoadMoreState(true, null));
            nextPageLiveData.observeForever(this);
        }

        @Override
        public void onChanged(@Nullable Resource<Boolean> result) {
            if (result == null) {
                reset();
            } else {
                switch (result.status) {
                    case SUCCESS:
                        hasMore = Boolean.TRUE.equals(result.data);
                        nextPageIndex.setValue(hasMore ? nextPageIndex.getValue() + 1 : -1);
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false, null));
                        break;
                    case ERROR:
                        hasMore = true;
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false, result.message));
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

        public MutableLiveData<LoadMoreState> getLoadMoreState() {
            return loadMoreState;
        }
    }
}
