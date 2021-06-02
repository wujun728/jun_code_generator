package org.coderfun.boot.core.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-10-24T00:07:21.719+0800")
@StaticMetamodel(Role.class)
public class Role_ {
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, String> roleCode;
	public static volatile SingularAttribute<Role, String> description;
	public static volatile SingularAttribute<Role, Short> sort;
}
