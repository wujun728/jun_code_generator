package com.jun.plugin.book.app.service.impl;

import com.jun.plugin.book.app.entity.User;
import com.jun.plugin.book.app.dao.UserMapper;
import com.jun.plugin.book.app.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
