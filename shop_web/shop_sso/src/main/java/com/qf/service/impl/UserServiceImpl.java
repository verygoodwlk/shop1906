package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * null.method();
     * null.field;
     * @param user
     * @return
     */
    @Override
    public int register(User user) {

        //验证用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        Integer count = userMapper.selectCount(queryWrapper);

        if(count > 0){
            //用户名已经存在
            return -1;
        }

        //保存用户
        return userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {

        User user = this.queryByUserName(username);

        if(user != null && user.getPassword().equals(password)){
            //登录成功
            return user;
        }

        return null;
    }

    /**
     * 通过用户名找到用户信息
     * @param username
     * @return
     */
    @Override
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public int updatePasswordByUserName(String username, String password) {

        User user = this.queryByUserName(username);
        user.setPassword(password);

        //修改数据库
        return userMapper.updateById(user);
    }
}
