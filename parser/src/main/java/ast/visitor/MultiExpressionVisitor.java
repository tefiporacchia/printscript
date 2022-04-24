package ast.visitor;

import ast.node.Expression;
import ast.node.Variable;

public interface MultiExpressionVisitor {
  void visitExpression(Expression expression) throws Exception;

  void visitVariable(Variable variable) throws Exception;
}
