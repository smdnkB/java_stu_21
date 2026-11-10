package com.liu.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TestJob {

    @XxlJob("TestJob")
    public void TestJob(){
        System.out.println(new Date());
    }
}
