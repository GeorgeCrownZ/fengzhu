package com.soft.web.service;

import com.soft.web.dao.RollpicMapper;
import com.soft.web.entity.Rollpic;
import com.soft.web.vo.RollPicVO;
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
public class RollpicService {

    @Autowired
    RollpicMapper rollpicMapper;

    //  查询所有启用的轮播图
    public List<RollPicVO> selectRollPics() {
        return rollpicMapper.selectRollPics();
    }

}
