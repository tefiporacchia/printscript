package ast.node;

import ast.visitor.MultiExpressionVisitor;

public interface MultiExpression extends Node {
  void accept(MultiExpressionVisitor visitor) throws Exception;

  MultiExpression addVariableWithOperator(Operator operator, Variable variable);
}
