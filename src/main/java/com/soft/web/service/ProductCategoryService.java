package com.soft.web.service;

import com.soft.web.dao.ProductCategoryMapper;
import com.soft.web.vo.CateGoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    public List<CateGoryVO> selectTopCategory() {
        return productCategoryMapper.selectTopCategory();
    }

    public List<CateGoryVO> selectSecondCategory(Long pid) {
        return productCategoryMapper.selectSecondCategory(pid);
    }
}
