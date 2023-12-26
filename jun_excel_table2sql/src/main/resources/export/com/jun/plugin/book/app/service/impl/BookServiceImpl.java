package com.jun.plugin.book.app.service.impl;

import com.jun.plugin.book.app.entity.Book;
import com.jun.plugin.book.app.dao.BookMapper;
import com.jun.plugin.book.app.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图书库 服务实现类
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
