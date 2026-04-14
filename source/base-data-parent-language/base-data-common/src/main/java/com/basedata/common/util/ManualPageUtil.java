package com.basedata.common.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class ManualPageUtil {

    /**
     * @Description: 手动分页
     */
    public static <T> PageInfo<T> manualPage(int pageNum, int pageSize, List<T> list) {
        Integer total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * pageNum;
            if (toIndex > total) {
                toIndex = total;
            }
            list = list.subList(pageSize * (pageNum - 1), toIndex);
        }
        Page page = new Page(pageNum, pageSize);
        page.addAll(list);
        page.setPages((total * pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
    }
}
