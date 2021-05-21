package com.soft.web.common;

import com.github.pagehelper.PageInfo;
import com.soft.web.utils.TableDataInfo;

import java.math.BigDecimal;
import java.util.List;

/******************
 * List工具类
 *
 ******************/
public class MyListUtils {

    /*****************************
     * 验证List不为空
     * 
     * @param list
     * @return 不为空返回true,为空返回false
     *****************************/
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(List list) {
        if (null != list && 0 < list.size()) {
            return true;
        } else {
            return false;
        }
    }

    public static TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    public static TableDataInfo getDataTable(List<?> list, long total)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

    public static TableDataInfo getDataTable(List<?> list, long total, BigDecimal price, BigDecimal expressFee)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(total);
        rspData.setPrice(price);
        rspData.setExpressFee(expressFee);
        return rspData;
    }

    public static TableDataInfo getDataTable(List<?> list, long total, BigDecimal price, BigDecimal expressFee, String comments, String systemComments)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(total);
        rspData.setPrice(price);
        rspData.setExpressFee(expressFee);
        rspData.setComments(comments);
        rspData.setSystemComments(systemComments);
        return rspData;
    }

    public static TableDataInfo getDataTable(List<?> list, long total, BigDecimal price,
                                             BigDecimal expressFee, String systemComments, String receiveName, String receiveTelphone, String address, String comments)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(total);
        rspData.setPrice(price);
        rspData.setExpressFee(expressFee);
        rspData.setSystemComments(systemComments);
        rspData.setReceiveName(receiveName);
        rspData.setReceiveTelphone(receiveTelphone);
        rspData.setAddress(address);
        rspData.setComments(comments);
        return rspData;
    }

    public static TableDataInfo getDataTable(List<?> list, long total, BigDecimal price,
                                             BigDecimal expressFee, String systemComments, String receiveName, String receiveTelphone, String address, String comments, BigDecimal freight, Long id, BigDecimal discount, String payComments)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(total);
        rspData.setPrice(price);
        rspData.setExpressFee(expressFee);
        rspData.setSystemComments(systemComments);
        rspData.setReceiveName(receiveName);
        rspData.setReceiveTelphone(receiveTelphone);
        rspData.setAddress(address);
        rspData.setComments(comments);
        rspData.setFreight(freight);
        rspData.setId(id);
        rspData.setDiscount(discount);
        rspData.setPayComments(payComments);
        return rspData;
    }

}
