package com.example.demo.analizer;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

import javax.naming.ConfigurationException;

public class ModelMapFailureAnalizer extends AbstractFailureAnalyzer<ConfigurationException> {
    @Override
    protected FailureAnalysis analyze(Throwable throwable, ConfigurationException exceptions) {
        StringBuilder description = new StringBuilder();
        description.append("Model Map conf failed " + exceptions.getMessage());

        return new FailureAnalysis(description.toString(),"Fix issue!",exceptions);
    }
}
