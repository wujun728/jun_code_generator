package com.jun.plugin.book.app.service.impl;

import com.jun.plugin.book.app.entity.BookHistory;
import com.jun.plugin.book.app.dao.BookHistoryMapper;
import com.jun.plugin.book.app.service.BookHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 阅读历史 服务实现类
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Service
public class BookHistoryServiceImpl extends ServiceImpl<BookHistoryMapper, BookHistory> implements BookHistoryService {

}
