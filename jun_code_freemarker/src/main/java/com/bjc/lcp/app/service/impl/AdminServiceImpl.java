package com.bjc.lcp.app.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjc.lcp.app.entity.AdminEntity;
import com.bjc.lcp.app.mapper.AdminMapper;
import com.bjc.lcp.app.service.AdminService;

/**
 * @description 管理员表服务层实现
 * @author wujun
 * @date 2023-12-26
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminEntity> implements AdminService {

	@Autowired
    private AdminMapper adminMapper;

}



