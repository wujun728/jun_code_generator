package org.coderfun.boot.core.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-11-15T16:48:32.371+0800")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> loginName;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> salt;
	public static volatile SingularAttribute<User, Date> birthday;
	public static volatile SingularAttribute<User, String> gender;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SingularAttribute<User, String> avatar;
	public static volatile SingularAttribute<User, Date> createDate;
	public static volatile SingularAttribute<User, String> state;
	public static volatile SingularAttribute<User, String> description;
	public static volatile SingularAttribute<User, Integer> loginCount;
	public static volatile SingularAttribute<User, Date> previousVisit;
	public static volatile SingularAttribute<User, Date> lastVisit;
}
