package cn.nine.crm.query;

import org.springframework.util.StringUtils;

public class BaseQuery {
    private Integer currentPage = 1;

    private Integer pageSize = 10;

    private Integer start = 0;

    //关键字查询
    private String keyword;

    public String getKeyword() {
        return StringUtils.isEmpty(keyword)? null : keyword.trim();
    }

    /**
     * 返回开始位置 limit #{begin},#{pageSize}
     * @return
     */
    public Integer getStart(){
        Integer start = (this.currentPage - 1) * this.pageSize;
        if(start == null){
            return 0;
        }
        return start;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
