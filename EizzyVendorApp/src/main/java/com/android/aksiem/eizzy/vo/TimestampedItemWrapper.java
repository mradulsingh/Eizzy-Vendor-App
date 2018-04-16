package com.android.aksiem.eizzy.vo;

/**
 * Created by pdubey on 15/04/18.
 */

public class TimestampedItemWrapper<T extends Timestamped> {

    public enum TimestampedItemWrapperType {
        ITEM, TIMESTAMP;
    }

    public final TimestampedItemWrapperType type;
    public final String timestamp;
    public final T item;

    public TimestampedItemWrapper(String timestamp, T item) {
        if (item != null && timestamp != null) {
            throw new IllegalArgumentException("OrderItem and Timestamp can't both be non-null");
        } else if (item == null && timestamp == null) {
            throw new IllegalArgumentException("OrderItem and Timestamp can't both be null");
        }
        if (item != null) {
            this.type = TimestampedItemWrapperType.ITEM;
        } else {
            this.type = TimestampedItemWrapperType.TIMESTAMP;
        }
        this.item = item;
        this.timestamp = timestamp;
    }

    public boolean isItem() {
        return this.type == TimestampedItemWrapperType.ITEM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimestampedItemWrapper<?> that = (TimestampedItemWrapper<?>) o;

        if (type != that.type) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null)
            return false;
        return item != null ? item.equals(that.item) : that.item == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
