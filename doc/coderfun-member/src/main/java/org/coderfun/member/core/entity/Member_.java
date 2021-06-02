package org.coderfun.member.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import klg.common.dataaccess.entity.BaseEntity_;

@Generated(value="Dali", date="2018-12-11T22:29:03.866+0800")
@StaticMetamodel(Member.class)
public class Member_ extends BaseEntity_ {
	public static volatile SingularAttribute<Member, String> username;
	public static volatile SingularAttribute<Member, String> password;
	public static volatile SingularAttribute<Member, String> email;
	public static volatile SingularAttribute<Member, String> mobile;
	public static volatile SingularAttribute<Member, Integer> loginFailureCount;
	public static volatile SingularAttribute<Member, Boolean> isLocked;
	public static volatile SingularAttribute<Member, Date> lockedDate;
	public static volatile SingularAttribute<Member, Long> point;
	public static volatile SingularAttribute<Member, BigDecimal> balance;
	public static volatile SingularAttribute<Member, String> salt;
	public static volatile SingularAttribute<Member, String> avatar;
}
