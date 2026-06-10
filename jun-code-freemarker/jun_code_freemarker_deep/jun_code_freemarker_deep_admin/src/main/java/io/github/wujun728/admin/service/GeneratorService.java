package io.github.wujun728.admin.service;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * GeneratorService
 */
public interface GeneratorService {

    String getTemplateConfig() throws IOException;

    Map<String, String> getResultByParams(Map<String, Object> params) throws IOException, TemplateException;

}