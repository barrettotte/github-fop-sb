package com.github.barrettotte.fopsb.model;

public class PageInfo {
    
    private String startCursor;
    private Boolean hasNextPage;
    private String endCursor;

    public PageInfo() {}

    public String getStartCursor() {
        return this.startCursor;
    }

    public void setStartCursor(final String startCursor) {
        this.startCursor = startCursor;
    }

    public Boolean getHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(final Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getEndCursor() {
        return this.endCursor;
    }

    public void setEndCursor(final String endCursor) {
        this.endCursor = endCursor;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("startCursor=\"").append(this.startCursor).append('"');
        sb.append("hasNextPage=").append(this.hasNextPage);
        sb.append("endCursor=").append(this.endCursor);
        sb.append('}');
        return sb.toString();
    }
}
