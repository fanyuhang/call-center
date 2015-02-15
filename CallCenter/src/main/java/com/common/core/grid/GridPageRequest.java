package com.common.core.grid;

import com.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * grid 分页参数，用于连接ligerui grid 和jpa data 分页
 * User: Allen
 * Date: 9/21/12
 */
public class GridPageRequest implements Pageable, Serializable {

    private int page;

    private int pagesize;

    private String sortname;

    private String sortorder;

    private Sort sort;

    public GridPageRequest() {
        //不能删除该方法
    }

    public GridPageRequest(int page, int pagesize) {
        this(page, pagesize, null, null);
    }

    public GridPageRequest(int page, int pagesize, String sortname, String sortorder) {
        this.page = page - 1;        //liger grid 传回来的page从1开始
        this.pagesize = pagesize;
        this.sortname = sortname;
        this.sortorder = sortorder;
        updateSort();
    }

    public void updateSort() {
        if (StringUtils.isNotEmpty(sortname)) {
            Sort.Order order = new Sort.Order(Constant.GRID_ORDER_ASC.equals(sortorder) ? Sort.Direction.ASC : Sort.Direction.DESC, sortname);
            this.sort = new Sort(order);
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page - 1;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
        updateSort();
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
        updateSort();
    }

    public void setSort(String sortname, String sortorder) {
        this.sortname = sortname;
        this.sortorder = sortorder;
        updateSort();
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return pagesize;
    }

    @Override
    public int getOffset() {
        return page * pagesize;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof GridPageRequest)) {
            return false;
        }

        GridPageRequest that = (GridPageRequest) obj;

        boolean pageEqual = this.page == that.page;
        boolean sizeEqual = this.pagesize == that.pagesize;

        boolean sortEqual = this.sort == null ? that.sort == null : this.sort.equals(that.sort);

        return pageEqual && sizeEqual && sortEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + page;
        result = 31 * result + pagesize;
        result = 31 * result + (null == sort ? 0 : sort.hashCode());

        return result;
    }
    
	@Override
	public String toString() {
		return "GridPageRequest [page=" + page + ", pagesize=" + pagesize
				+ ", sortname=" + sortname + ", sortorder=" + sortorder
				+ ", sort=" + sort + "]";
	}
    
    
}
