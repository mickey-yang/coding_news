package com.myang.codingnews.services;

import com.myang.codingnews.model.Source;
import org.springframework.stereotype.Service;

@Service
public class SourceService {
    public Source save(Source source) {
        return source;
    }

    public Source findBySourceName(String sourceName) {
        return null;
    }
}
