package org.coderfun.common.dict.service;

import org.coderfun.common.dict.dao.CodeItemDAO;
import org.coderfun.common.dict.entity.CodeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klg.common.dataaccess.BaseServiceImpl;

@Service
public class CodeItemServiceImpl  extends BaseServiceImpl<CodeItem, Long> implements CodeItemService{
	@Autowired
	CodeItemDAO codeItemDAO;
}
