package ast.node;

import ast.visitor.MultiExpressionVisitor;
import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Expression implements MultiExpression {

  private final MultiExpression left;
  private final Operator operator;
  private final MultiExpression right;

  public Expression(MultiExpression left, Operator operator, MultiExpression right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public void accept(NodeVisitor visitor) {}

  @Override
  public void accept(MultiExpressionVisitor visitor) throws Exception {
    visitor.visitExpression(this);
  }

  public MultiExpression addVariableWithOperator(Operator operator, Variable variable) {
    if (operator == Operator.SUB || operator == Operator.ADD) {
      return new Expression(this, operator, variable);
    } else {
      MultiExpression right = new Expression(this.right, operator, variable);
      return new Expression(left, this.operator, right);
    }
  }

  @Override
  public String toString() {
    String sb = "(" + left.toString() + ")" + operator + "(" + right + ")";
    return sb;
  }
}
