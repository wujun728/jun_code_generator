package klg.query.jpa.expr;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import klg.query.expr.Expression;

public abstract class AttrExpression extends Expression {
	Attribute attr;
	public  Attribute getAttr(){
		return this.attr;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	public AttrExpression igCase() {
		ignoreCase = true;
		return this;
	}

	public AttrExpression igEmpty() {
		ignoreEmpty = true;
		return this;
	}
	
	public abstract Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) throws NullPointerException;
}
