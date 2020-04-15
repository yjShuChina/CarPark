package com.example.carpark.javabean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Scope("prototype")
public class CountData {
    private String datas;
    private BigDecimal counts;

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public BigDecimal getCounts() {
        return counts;
    }

    public void setCounts(BigDecimal counts) {
        this.counts = counts;
    }
}
