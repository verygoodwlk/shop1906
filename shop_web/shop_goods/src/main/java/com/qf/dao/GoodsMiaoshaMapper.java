package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.GoodsMiaosha;

import java.util.List;

public interface GoodsMiaoshaMapper extends BaseMapper<GoodsMiaosha> {

    List<GoodsMiaosha> queryNow();

    List<GoodsMiaosha> queryNext();
}
