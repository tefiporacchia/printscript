import ast.node.Expression;
import ast.node.MultiExpression;
import ast.node.Operator;
import ast.node.Variable;
import ast.visitor.MultiExpressionVisitor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Evaluator implements MultiExpressionVisitor {

  private String output;
  private Map<String, String> variablesWithValue;
  private Map<String, String> variableWithTypes;

  public Evaluator() {
    variablesWithValue = new HashMap<>();
    variableWithTypes = new HashMap<>();
    output = "";
  }

  public Evaluator(Map<String, String> variablesWithValue) {
    this.variablesWithValue = variablesWithValue;
    variableWithTypes = new HashMap<>();
  }

  @Override
  public void visitExpression(Expression expression) throws Exception {
    String leftOperand = getValue(expression.getLeft());
    String rightOperand = getValue(expression.getRight());
    Operator operator = expression.getOperator();

    if (isConcatenation(leftOperand, rightOperand, operator)) {
      concatenate(leftOperand, rightOperand);
    } else if (isNumericOperation(leftOperand, rightOperand)) {
      numericOperation(leftOperand, rightOperand, operator);
    }
  }

  @Override
  public void visitVariable(Variable variable) {
    if (variableHasAssignedValue(variable)) {
      output = variablesWithValue.get(variable.getVarName());
    } else {
      output = variable.getVarName();
    }
  }

  private boolean variableHasAssignedValue(Variable variable) {
    return variablesWithValue.containsKey(variable.getVarName());
  }

  private String getValue(MultiExpression multiExpression) throws Exception {
    multiExpression.accept(this);
    return output;
  }

  public void declareVariable(String name) {
    variablesWithValue.put(name, null);
  }

  public void assignVariable(String name) {
    variablesWithValue.put(name, output);
  }

  public void addVariableWithType(String name, String type) {
    variableWithTypes.put(name, type);
  }

  public String getVariableType(String name) {
    return variableWithTypes.get(name);
  }

  public boolean validateType(String type) {
    if (Objects.equals(type, "String")) {
      return isString(output);
    } else if (Objects.equals(type, "number")) {
      return isNumber(output);
    }
    return false;
  }

  private boolean isNumericOperation(String leftOperand, String rightOperand) {
    return isNumber(leftOperand) && isNumber(rightOperand);
  }

  private boolean isConcatenation(String leftOperand, String rightOperand, Operator operator) {
    return (operator == Operator.ADD)
        && (isString(leftOperand) && isString(rightOperand)
            || isString(leftOperand) && isNumber(rightOperand)
            || isNumber(leftOperand) && isString(rightOperand));
  }

  private boolean isString(String string) {
    return string.matches("\"[\\s\\S][^\"]*\"|'[\\s\\S][^']*'");
  }

  private boolean isNumber(String string) {
    return string.matches("-?[0-9]{1,9}(\\.[0-9]*)?");
  }

  private void concatenate(String left, String right) {
    output = "\"" + (left + right).replaceAll("[\"']", "") + "\"";
  }

  private void numericOperation(String leftOperand, String rightOperand, Operator operator) {
    Double leftValue = Double.parseDouble(leftOperand);
    Double rightValue = Double.parseDouble(rightOperand);

    output =
        switch (operator) {
          case ADD -> String.valueOf(leftValue + rightValue);
          case SUB -> String.valueOf(leftValue - rightValue);
          case MUL -> String.valueOf(leftValue * rightValue);
          case DIV -> String.valueOf(leftValue / rightValue);
          default -> "";
        };
  }
}
