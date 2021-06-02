package org.coderfun.member.core.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-12-11T21:10:47.104+0800")
@StaticMetamodel(MemberInfo.class)
public class MemberInfo_ {
	public static volatile SingularAttribute<MemberInfo, Long> memberId;
	public static volatile SingularAttribute<MemberInfo, String> registerIp;
	public static volatile SingularAttribute<MemberInfo, String> loginIp;
	public static volatile SingularAttribute<MemberInfo, Date> loginDate;
	public static volatile SingularAttribute<MemberInfo, String> name;
	public static volatile SingularAttribute<MemberInfo, String> gender;
	public static volatile SingularAttribute<MemberInfo, Date> birth;
	public static volatile SingularAttribute<MemberInfo, String> address;
	public static volatile SingularAttribute<MemberInfo, String> zipCode;
}
