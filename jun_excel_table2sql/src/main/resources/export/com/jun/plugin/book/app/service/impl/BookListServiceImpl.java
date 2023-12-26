package com.jun.plugin.book.app.service.impl;

import com.jun.plugin.book.app.entity.BookList;
import com.jun.plugin.book.app.dao.BookListMapper;
import com.jun.plugin.book.app.service.BookListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 我的书单 服务实现类
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Service
public class BookListServiceImpl extends ServiceImpl<BookListMapper, BookList> implements BookListService {

}
