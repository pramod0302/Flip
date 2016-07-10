package com.example.pramod.flip;

/**
 * Created by pramod on 10-07-2016.
 */
public class Params {
    private static final Integer DEFAULT_COUNT_LIMIT = 108;
    private Integer _count;
    private Integer _count_limit;

    public Params() {
        _count = 0;
        _count_limit = DEFAULT_COUNT_LIMIT;
    }

    public Integer getCount() {
        return _count;
    }
    public void setCount(Integer cnt) {
        _count = cnt;
    }
    public void increment_count() {
        _count++;
    }

    public Integer getCount_limit() {
        return _count_limit;
    }
    public void setCount_limit(Integer cnt_lim) {
        _count_limit = cnt_lim;
    }
}
