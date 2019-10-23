package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> queryByUid(Integer uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public int insertAddress(Address address) {

        if(address.getIsdefault() == 1){
            //说明当前新的地址是默认收货地址
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("isdefault", 1);
            queryWrapper.eq("uid", address.getUid());
            Address defaultAddress = addressMapper.selectOne(queryWrapper);

            //改成非默认
            defaultAddress.setIsdefault(0);

            //修改
            addressMapper.updateById(defaultAddress);
        }

        return addressMapper.insert(address);
    }

    @Override
    public Address queryById(Integer id) {
        return addressMapper.selectById(id);
    }
}
