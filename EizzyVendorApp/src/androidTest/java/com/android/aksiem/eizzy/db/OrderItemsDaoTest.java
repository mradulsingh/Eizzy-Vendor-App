package com.android.aksiem.eizzy.db;

import android.support.test.runner.AndroidJUnit4;

import com.android.aksiem.eizzy.util.OrderItemsUtil;
import com.android.aksiem.eizzy.vo.order.OrderDetailItem;

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

    }
}
