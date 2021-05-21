package com.soft.web.dao;

import com.soft.web.entity.Global;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.web.vo.GlobalWechatVO;
import com.soft.web.vo.MinePicVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author md
 * @since 2021-04-10
 */
@Mapper
@Repository
public interface GlobalMapper extends BaseMapper<Global> {

    public MinePicVO selectMinePic();

    public GlobalWechatVO selectWechatSettings();
}
