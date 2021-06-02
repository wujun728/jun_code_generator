package org.coderfun.common.dict.service;

import org.coderfun.common.dict.dao.CodeClassDAO;
import org.coderfun.common.dict.entity.CodeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klg.common.dataaccess.BaseServiceImpl;

@Service
public class CodeClassServiceImpl  extends BaseServiceImpl<CodeClass, Long> implements CodeClassService{
	@Autowired
	CodeClassDAO codeClassDAO;
}
