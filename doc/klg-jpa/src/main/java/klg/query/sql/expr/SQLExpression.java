package klg.query.sql.expr;

import klg.query.expr.Expression;

public abstract class SQLExpression extends Expression {
	String field;
	public abstract String toSQL () throws NullPointerException;
	public SQLExpression igCase() {
		ignoreCase = true;
		return this;
	}

	public SQLExpression igEmpty() {
		ignoreEmpty = true;
		return this;
	}
	
}
