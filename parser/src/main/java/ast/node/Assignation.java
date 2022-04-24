package ast.node;

import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Assignation implements Node {

  private final String name;
  private final MultiExpression val;

  public Assignation(String name, MultiExpression val) {
    this.name = name;
    this.val = val;
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "Assign(" + "name='" + name + '\'' + ", val=" + val + ')';
  }
}
