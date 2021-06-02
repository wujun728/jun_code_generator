package org.coderfun.common.log.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-11-30T10:43:38.952+0800")
@StaticMetamodel(LoginLog.class)
public class LoginLog_ {
	public static volatile SingularAttribute<LoginLog, Long> id;
	public static volatile SingularAttribute<LoginLog, String> successed;
	public static volatile SingularAttribute<LoginLog, String> message;
	public static volatile SingularAttribute<LoginLog, String> os;
	public static volatile SingularAttribute<LoginLog, String> browser;
	public static volatile SingularAttribute<LoginLog, Date> optime;
	public static volatile SingularAttribute<LoginLog, String> opip;
	public static volatile SingularAttribute<LoginLog, String> opusername;
}
