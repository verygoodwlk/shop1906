package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Shopcart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper extends BaseMapper<Shopcart> {

    List<Shopcart> queryByGid(@Param("gid") Integer[] gid, @Param("uid") Integer uid);
}
