package klg.common.tree;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-10-24T02:16:19.428+0800")
@StaticMetamodel(EasyUITreeNode.class)
public class EasyUITreeNode_ {
	public static volatile SingularAttribute<EasyUITreeNode, Serializable> id;
	public static volatile SingularAttribute<EasyUITreeNode, Serializable> parentId;
	public static volatile SingularAttribute<EasyUITreeNode, String> name;
	public static volatile SingularAttribute<EasyUITreeNode, String> iconCls;
	public static volatile SingularAttribute<EasyUITreeNode, String> state;
	public static volatile SingularAttribute<EasyUITreeNode, Integer> orderNum;
}
