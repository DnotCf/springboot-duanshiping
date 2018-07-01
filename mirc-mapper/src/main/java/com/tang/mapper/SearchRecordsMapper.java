package com.tang.mapper;

import com.tang.pojo.SearchRecords;
import com.tang.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

    public List<String> hotWords();
}