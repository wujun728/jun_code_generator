package ${conf.basePackage}.${table.lowerCamelName}.dao;

import ${conf.basePackage}.base.mybatis.dao.BaseDAO;
import ${conf.basePackage}.${table.lowerCamelName}.entity.${table.className}Entity;

/**
 * ${table.comment} DAO
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
public interface ${table.className}DAO extends BaseDAO<${table.className}Entity> {
}
