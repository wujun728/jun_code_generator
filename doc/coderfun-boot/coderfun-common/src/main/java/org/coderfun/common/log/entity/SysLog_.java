package org.coderfun.common.log.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-11-30T10:43:38.980+0800")
@StaticMetamodel(SysLog.class)
public class SysLog_ {
	public static volatile SingularAttribute<SysLog, Long> id;
	public static volatile SingularAttribute<SysLog, String> name;
	public static volatile SingularAttribute<SysLog, String> moduleCode;
	public static volatile SingularAttribute<SysLog, String> method;
	public static volatile SingularAttribute<SysLog, String> params;
	public static volatile SingularAttribute<SysLog, String> successed;
	public static volatile SingularAttribute<SysLog, String> message;
	public static volatile SingularAttribute<SysLog, Long> executeTime;
	public static volatile SingularAttribute<SysLog, String> os;
	public static volatile SingularAttribute<SysLog, String> browser;
	public static volatile SingularAttribute<SysLog, Date> optime;
	public static volatile SingularAttribute<SysLog, String> opip;
	public static volatile SingularAttribute<SysLog, String> opusername;
}
