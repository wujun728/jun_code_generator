package klg.query.sql.expr;

import klg.query.expr.Expression;

public class NoValueExpression extends SQLExpression{	
	public NoValueExpression(String field,klg.query.expr.Expression.Operator op){
		this.field=field;
		this.op=op;
	}

	@Override
	public String toSQL() {
		return field + " " +op.toString();
	}

	@Override
	public boolean hasEmptyValue() {
		return false;
	}
}
