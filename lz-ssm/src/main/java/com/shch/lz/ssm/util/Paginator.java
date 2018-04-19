package com.shch.lz.ssm.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Link at 12:38 on 4/11/18.
 */
@NoArgsConstructor
public class Paginator {
    @Getter
    private long total = 0L;
    @Getter
    @Setter
    private int page = 1;
    @Getter
    @Setter
    private long totalPage = 1;
    @Getter
    private int rows = 10;
    @Getter
    @Setter
    private int step = 5;
    @Getter
    @Setter
    private String param = "page";
    @Getter
    @Setter
    private String url = "";
    @Getter
    @Setter
    private String query = "";

    public Paginator(long total, int page, int rows, HttpServletRequest request) {
        setTotal(total);
        setPage(page);
        setRows(rows);
        setUrl(request.getRequestURI());
        setQuery(request.getQueryString());
    }

    public void setTotal(long total) {
        this.total = total;
        this.initTotalPage();
    }

    public void setRows(int rows) {
        if (rows > 10000) {
            rows = 10000;
        }
        this.rows = rows;
        this.initTotalPage();
    }

    public void initTotalPage() {
        totalPage = (total % rows) == 0 ? (total / rows) : ((total / rows) + 1);
        if (page > totalPage) {
            page = (int) totalPage;
        }
        if (page < 1) {
            page = 1;
        }
    }

    public String getHtml() {
        if (null != query) {
            String params = "";
            String[] querys = query.split("&");
            for (int i = 0; i < querys.length; i++) {
                if (querys[i].startsWith(param)) {
                    continue;
                }
                if ("".equals(params)) {
                    params = querys[i];
                } else {
                    params += "&" + querys[i];
                }
            }
            if (!"".equals(params)) {
                url += "?" + params;
            }
        }
        StringBuilder pages = new StringBuilder("");
//        String pages = "";
        int pageCount = (int) Math.ceil((double) total / rows);
        if (pageCount <= 1) {
            // only one page
            return pages.toString();
        }
        if (page > pageCount) {
            page = pageCount;
        }
        if (page <= 0) {
            page = 1;
        }
        // previous page
        pages = addPreviousPage(pages);
        // middle list
        // set step = 3
        if (pageCount > step) {
            // more than step pages
            int listBegin = (page - (int) Math.floor((double) step / 2));
            if (listBegin < 1) {
                listBegin = 1;
            }
            if (listBegin >= 2) {
                // first page
                pages = addFirstPage(pages);
            }
            // middle pages
            if (pageCount - page >= page - listBegin) {
                // have right step pages
                for (int i = listBegin; i < (listBegin + step); i++) {
                    pages = addStepPages(pages, i);
                }
                if (listBegin + step <= pageCount) {
                    // last page
                    // < 1 ... x y z ... n >
                    pages = addLastPage(pages, pageCount);
                }
                // < 1 ... x y z >
            } else {
                // no right step pages
                // < 1... x y >
                for (int i = (pageCount - step) + 1; i < pageCount; i++) {
                    pages = addStepPages(pages, i);
                }
            }
        } else {
            // less than step pages
            // < 1 2 3 >
            for (int i = 1; i <= pageCount; i++) {
                pages = addStepPages(pages, i);
            }
        }
        // next page
        pages = addNextPage(pages, pageCount);
        return pages.toString();
    }

    private StringBuilder addPreviousPage(StringBuilder pages) {
        if (page > 1) {
            if (url.contains("?")) {
                pages = pages.append("<a class=\"prev\" href=\"" + url + "&" + param + "=" + (page - 1) + "\"> < </a>\n");
            } else {
                pages = pages.append("<a class=\"prev\" href=\"" + url + "?" + param + "=" + (page - 1) + "\"> < </a>\n");
            }
        } else {
            pages = pages.append("<a class=\"prev\" href=\"javascript:;\" style=\"color:#ccc\"> < </a>\n");
        }
        return pages;
    }

    private StringBuilder addNextPage(StringBuilder pages, int pageCount) {
        if (page < pageCount) {
            if (url.contains("?")) {
                pages = pages.append("<a class=\"next\" href=\"" + url + "&" + param + "=" + (page + 1) + "\"> > </a>\n");
            } else {
                pages = pages.append("<a class=\"next\" href=\"" + url + "?" + param + "=" + (page + 1) + "\"> > </a>\n");
            }
        } else {
            pages = pages.append("<a class=\"next\" href=\"javascript:;\" style=\"color:#ccc\"> > </a>\n");
        }
        return pages;
    }

    private StringBuilder addFirstPage(StringBuilder pages) {
        if (url.contains("?")) {
            pages = pages.append("<a href=\"" + url + "&" + param + "=1\">1</a> ... \n");
        } else {
            pages = pages.append("<a href=\"" + url + "?" + param + "=1\">1</a> ... \n");
        }
        return pages;
    }

    private StringBuilder addLastPage(StringBuilder pages, int pageCount) {
        if (url.contains("?")) {
            pages = pages.append(" ... <a href=\"" + url + "&" + param + "=" + pageCount + "\">" + pageCount + "</a>\n");
        } else {
            pages = pages.append(" ... <a href=\"" + url + "?" + param + "=" + pageCount + "\">" + pageCount + "</a>\n");
        }
        return pages;
    }

    private StringBuilder addStepPages(StringBuilder pages, int i) {
        if (i != page) {
            if (url.contains("?")) {
                pages = pages.append("<a href=\"" + url + "&" + param + "=" + i + "\">" + i + "</a>\n");
            } else {
                pages = pages.append("<a href=\"" + url + "?" + param + "=" + i + "\">" + i + "</a>\n");
            }
        } else {
            pages = pages.append("<span class=\"current\">" + i + "</span>\n");
        }
        return pages;
    }

}
