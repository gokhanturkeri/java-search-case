package com.zapu.searchwebservice.responses;

import java.util.List;

public class SearchResult<T> {
    private final List<T> result;
    private int page;
    private int pageSize;
    private int totalPage;
    private int count;
    private String rootUrl;

    public SearchResult(int page, int pageSize, int totalPage, int count, List<T> result) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.count = count;
        this.result = result;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCount() {
        return count;
    }

    public List<T> getResult() {
        return result;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }
}
