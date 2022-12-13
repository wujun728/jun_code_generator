<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${conf.basePackage}.${table.lowerCamelName}.dao.${table.className}DAO">
    <resultMap id="${table.className}ResultMap" type="${conf.basePackage}.${table.lowerCamelName}.entity.${table.className}Entity">
	<#list table.columns as col>
		<#if col.name=="id">
        <id column="${col.name}" property="${col.camelName}" jdbcType="${col.myBatisType}"/>
		<#else>
        <result column="${col.name}" property="${col.camelName}" jdbcType="${col.myBatisType}"/>
		</#if>
	</#list>
    </resultMap>

    <sql id="select_column_list">SELECT ${table.selectView}</sql>
    <sql id="select_not_del">AND isDel = 0</sql>
    <sql id="order_by_sql">ORDER BY id DESC</sql>
    <sql id="insert_into_sql">INSERT INTO `${table.name}` (${table.insertView})</sql>
    <sql id="delete_from_sql">UPDATE `${table.name}`</sql>
    <sql id="update_table_sql">UPDATE `${table.name}`</sql>
    <sql id="select_count_sql">SELECT COUNT(1) FROM `${table.name}`</sql>
    <sql id="from_sql">FROM `${table.name}`</sql>
    <sql id="insert_table_sql">INSERT INTO `${table.name}`</sql>
    <sql id="limit_1_sql">LIMIT 1</sql>

    <insert id="insertBatchAllColumn" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
        <include refid="insert_into_sql"/>
        <trim prefix="VALUES" suffixOverrides=",">
            <if test="list != null">
                <foreach collection="list" item="item" index="index" separator=",">
                    <trim prefix="(" suffix=")" suffixOverrides=",">
                    <#list table.columns as col>
                        <#if col.name!="id">
                        ${r'#{item.'}${col.camelName},jdbcType=${col.myBatisType}},
                        </#if>
                    </#list>
                    </trim>
                </foreach>
            </if>
        </trim>
    </insert>
</mapper>
