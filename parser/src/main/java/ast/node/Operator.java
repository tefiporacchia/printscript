package ast.node;

public enum Operator {
  ADD("+"),
  SUB("-"),
  MUL("*"),
  DIV("/");

  private final String string;

  Operator(String string) {
    this.string = string;
  }

  public String getString() {
    return string;
  }

  public static Operator getOperator(String string) {
    for (Operator operator : Operator.values()) {
      if (operator.getString().equals(string)) {
        return operator;
      }
    }
    return null;
  }
}
