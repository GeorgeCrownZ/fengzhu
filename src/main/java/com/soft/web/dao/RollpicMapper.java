package com.soft.web.dao;

import com.soft.web.entity.Rollpic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.vo.RollPicVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author md
 * @since 2021-03-30
 */
@Repository
@Mapper
public interface RollpicMapper extends BaseMapper<Rollpic> {

    public List<RollPicVO> selectRollPics();

}
