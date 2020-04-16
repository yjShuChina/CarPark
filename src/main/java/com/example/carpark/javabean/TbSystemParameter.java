package com.example.carpark.javabean;

public class TbSystemParameter {
    private Integer parameterId;

    private String parameterName;

    private String parameterValue;

    private String parameterExplain;

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName == null ? null : parameterName.trim();
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue == null ? null : parameterValue.trim();
    }

    public String getParameterExplain() {
        return parameterExplain;
    }

    public void setParameterExplain(String parameterExplain) {
        this.parameterExplain = parameterExplain == null ? null : parameterExplain.trim();
    }
}