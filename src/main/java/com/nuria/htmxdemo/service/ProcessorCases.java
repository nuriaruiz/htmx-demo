package com.nuria.htmxdemo.service;

import com.nuria.htmxdemo.model.CaseNotification;

public class ProcessorCases {
    private String country;

    public ProcessorCases(String country) {
        this.country = country;
    }

    public CaseNotification process() {
        // for example, select cases from database of cases by country
        return new CaseNotification("1", country, 1);
    }
}
