package com.github.xabgesagtx.example.Thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestThread1 implements Runnable  {
    private List list;
    CountDownLatch cdl;

    public TestThread1(List list,CountDownLatch cdl){
        this.list=list;
        this.cdl=cdl;
    }

    @Override
    public void run() {

    }
}
