package org.talk.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talk.auth.consts.UserConst;
import org.talk.auth.dao.UserCustomMapper;
import org.talk.auth.dao.UserMapper;
import org.talk.auth.model.User;
import org.talk.auth.model.UserExample;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;

    public void setUserEmailVerified(List<Integer> ids) {
        userCustomMapper.orFields(ids, UserConst.EMAIL_VERIFIED_BIT);
    }

    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User findByUsername(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username)
                .andDeletedIdEqualTo(0);
        User user = userMapper.selectByExample(example)
                .stream().findFirst().orElse(null);
        return user;
    }

    public User findByMobile(String mobile) {
        UserExample example = new UserExample();
        example.or().andMobileEqualTo(mobile)
                .andDeletedIdEqualTo(0);
        User user = userMapper.selectByExample(example)
                .stream().findFirst().orElse(null);
        return user;
    }

    public User findByEmail(String email) {
        UserExample example = new UserExample();
        example.or().andEmailEqualTo(email).andDeletedIdEqualTo(0);
        User user = userMapper.selectByExample(example)
                .stream().findFirst().orElse(null);
        return user;
    }

    public Integer addUser(User user) {
        user.setAddTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);
        return user.getId();
    }
}
