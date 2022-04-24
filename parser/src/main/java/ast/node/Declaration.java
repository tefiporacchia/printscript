package ast.node;

import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Declaration implements Node {
  private final String varName;
  private final String type;
  private MultiExpression val;

  public Declaration(String varName, String type, MultiExpression val) {
    this.varName = varName;
    this.type = type;
    this.val = val;
  }

  public Declaration(String varName, String type) {
    this.varName = varName;
    this.type = type;
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    if (val != null) return "Dec(" + "varName=" + varName + ", type=" + type + ", val=" + val + ')';
    else return "Dec(" + "varName=" + varName + ", type=" + type + ')';
  }
}
