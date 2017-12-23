package org.simple.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 分页类
 */
@SuppressWarnings("rawtypes")
public class Page<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Page(int pageSize)
    {
        pageNumber = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
        result = Collections.emptyList();
        totalCount = -1L;
        autoCount = true;
        pageUrl = "errorPage.jsp";
        setPageSize(pageSize);
    }

    public Page(int pageSize, boolean autoCount)
    {
        pageNumber = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
        result = Collections.emptyList();
        totalCount = -1L;
        this.autoCount = true;
        pageUrl = "errorPage.jsp";
        setPageSize(pageSize);
        setAutoCount(autoCount);
    }

    public Page()
    {
        this(DEFAULT_PAGE_SIZE);
    }

    public Page(long start, long totalSize, int pageSize, List data)
    {
        pageNumber = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
        result = Collections.emptyList();
        totalCount = -1L;
        autoCount = true;
        pageUrl = "errorPage.jsp";
        this.pageSize = pageSize;
        this.start = start;
        totalCount = totalSize;
        result = data;
    }

    public long getTotalPageCount()
    {
        Assert.isTrue(pageSize > 0);
        if(totalCount % (long)pageSize == 0L) {
        	return totalCount / (long)pageSize;        	
        } else {
        	return totalCount / (long)pageSize + 1L;        	
        }
    }

    public int getFirstOfPage()
    {
        return (pageNumber - 1) * pageSize + 1;
    }

    public int getLastOfPage()
    {
        return pageNumber * pageSize;
    }

    public static int getDEFAULT_PAGE_SIZE()
    {
        return DEFAULT_PAGE_SIZE;
    }

    public static void setDEFAULT_PAGE_SIZE(int dEFAULTPAGESIZE)
    {
        DEFAULT_PAGE_SIZE = dEFAULTPAGESIZE;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public List getResult()
    {
        return result;
    }

    public void setResult(List result)
    {
        this.result = result;
    }

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }

    public boolean isAutoCount()
    {
        return autoCount;
    }

    public void setAutoCount(boolean autoCount)
    {
        this.autoCount = autoCount;
    }

    public String getPageUrl()
    {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl)
    {
        this.pageUrl = pageUrl;
    }

    public long getStart()
    {
        return start;
    }

    public void setStart(long start)
    {
        this.start = start;
    }
    
    public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
    public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public static String getAsc()
    {
        return "asc";
    }

    public static String getDesc()
    {
        return "desc";
    }

    public String getFormName()
    {
        return formName;
    }

    public void setFormName(String formName)
    {
        this.formName = formName;
    }

    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static int DEFAULT_PAGE_SIZE = 10;
    protected int pageNumber;
    protected int pageSize;
    protected List result;
    protected long totalCount;
    protected boolean autoCount;
    protected String pageUrl;
    protected String formName;
    protected String sort;
    //protected String orderBy;
    //protected String order;
    protected String sortName;
    protected String sortOrder;
    
    private long start;

}