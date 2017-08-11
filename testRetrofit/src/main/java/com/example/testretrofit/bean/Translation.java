package com.example.testretrofit.bean;

/**
 * Created by gaoliang on 2017/8/11.
 */

public class Translation {
    private int status;
    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    @Override
    public String toString() {
        return "status="+status+" from="+content.from+
                " to="+content.to+" vendor="+content.vendor+
                " out="+content.out+" errNo="+content.errNo;
    }
}
