package com.android.aksiem.eizzy.db;

import android.support.test.runner.AndroidJUnit4;

import com.android.aksiem.eizzy.util.OrderItemsUtil;
import com.android.aksiem.eizzy.vo.OrderDetailItem;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.android.aksiem.eizzy.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class OrderItemsDaoTest extends DbTest {

    @Test
    public void insertAndLoadOrderItems() throws InterruptedException {
        List<OrderDetailItem> orderDetailItems = OrderItemsUtil.createOrderItem(1);
        db.beginTransaction();
        try {
            db.orderItemDao().insertOrderItems(orderDetailItems);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        List<OrderDetailItem> list = getValue(db.orderItemDao().getOrderItems());
        assertThat(list.size(), is(1));
        OrderDetailItem first = list.get(0);
    }
}
