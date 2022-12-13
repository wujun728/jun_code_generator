package com.jeasy.base.web.dto;

import com.jeasy.common.Func;
import lombok.Data;

import java.util.List;

/**
 * ResultPage
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public final class ResultPage {

    /**
     * 当前页码
     */
    private int pageNo = 1;
    /**
     * 每页显示数量
     */
    private int pageSize = 10;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 存放查询结果用的list
     */
    private List items;

    public ResultPage() {
    }

    public ResultPage(final Long totalCount, final Integer pageSize, final Integer pageNo, final List items) {
        this.totalCount = Func.isEmpty(totalCount) ? 0 : totalCount;
        this.pageSize = Func.isEmpty(pageSize) ? 0 : pageSize;
        this.pageNo = Func.isEmpty(pageNo) ? 0 : pageNo;
        this.totalPage = operatorTotalPage();
        this.items = items;
    }

    /**
     * 计算总页数
     *
     * @return
     */
    private long operatorTotalPage() {
        long pageCount = 0;
        if (pageSize != 0) {
            pageCount = totalCount / pageSize;
            if (totalCount % pageSize != 0) {
                pageCount++;
            }
        }

        return pageCount;
    }
}
