INSERT INTO t_system_menu(code, pcode, pcodes, name, icon, url, num, levels, ismenu, tips, status, isopen)
VALUES ('${strutil.toLowerCase(g.entityName)}_list', 'EASYCODE', '[0],[easycode],', '${g.name}管理', '', '/${strutil.toLowerCase(g.entityName)}', 6, 2, 1, NULL, 1, NULL);
INSERT INTO t_system_menu(code, pcode, pcodes, name, icon, url, num, levels, ismenu, tips, status, isopen)
VALUES ('${strutil.toLowerCase(g.entityName)}_add', '${strutil.toLowerCase(g.entityName)}_list', '[0],[EASYCODE],[${strutil.toLowerCase(g.entityName)}_list],', '${g.name}新增', '', '/${strutil.toLowerCase(g.entityName)}/add', 1, 3, 0, NULL, 1, NULL);
INSERT INTO t_system_menu(code, pcode, pcodes, name, icon, url, num, levels, ismenu, tips, status, isopen)
VALUES ('${strutil.toLowerCase(g.entityName)}_update', '${strutil.toLowerCase(g.entityName)}_list', '[0],[EASYCODE],[${strutil.toLowerCase(g.entityName)}_list],', '${g.name}编辑', '', '/${strutil.toLowerCase(g.entityName)}/update', 2, 3, 0, NULL, 1, NULL);
INSERT INTO t_system_menu(code, pcode, pcodes, name, icon, url, num, levels, ismenu, tips, status, isopen)
VALUES ('${strutil.toLowerCase(g.entityName)}_delete', '${strutil.toLowerCase(g.entityName)}_list', '[0],[EASYCODE],[${strutil.toLowerCase(g.entityName)}_list],', '${g.name}删除', '', '/${strutil.toLowerCase(g.entityName)}/delete', 3, 3, 0, NULL, 1, NULL);
