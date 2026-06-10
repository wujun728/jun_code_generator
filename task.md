

这下面的两个模块的代码非常相似，功能也非常相似，只是功能略有不同。
D:\workspace_github\jun_code_generator\jun-code-freemarker

将下面的jun-code-spring-boot-starter 代码及功能迁移到jun_code_freemarker_deep这个模块中。保障升级后的功能正常。升级的代码功能更健壮，原有的代码及脚本与模板全部删除并合并到新的模块里面来。
D:\workspace_github\jun_code_generator\jun-code-freemarker\jun-code-spring-boot-starter

D:\workspace_github\jun_code_generator\jun-code-freemarker\jun_code_freemarker_deep





修复下面的问题：
1、前端ftl页面与代码生成模板杂合在一起了，不好区分。 D:\workspace_github\jun_code_generator\jun-code-freemarker\jun_code_freemarker_deep\jun_code_freemarker_deep_admin\src\main\resources\templates
2、升级为yaml文件。D:\workspace_github\jun_code_generator\jun-code-freemarker\jun_code_freemarker_deep\jun_code_freemarker_deep_admin\src\main\resources\application.properties

D:\workspace_github\jun_code_generator\jun-code-freemarker\jun_code_freemarker_deep






下面的这两个模块的功能严重重复，保留一个模块，即mybatisplus 能生成curd的代码即可，同时，合并模板可选模板。
D:\workspace_github\jun_code_generator\jun-mybatisplus-generator\
D:\workspace_github\jun_code_generator\jun-mybatisplus-generator_depp_mybatisplus\