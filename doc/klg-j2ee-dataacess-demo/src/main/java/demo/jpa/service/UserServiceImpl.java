package demo.jpa.service;
import klg.common.dataaccess.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.jpa.pojo.User;
import demo.jpa.respository.UserDAO;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService{
	@Autowired
	UserDAO userDAO;
}
