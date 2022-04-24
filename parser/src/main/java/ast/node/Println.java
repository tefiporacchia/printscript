package ast.node;

import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class Println implements Node {
  private final MultiExpression content;

  public Println(MultiExpression content) {
    this.content = content;
  }

  @Override
  public void accept(NodeVisitor visitor) throws Exception {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "print(" + "content=" + content + ')';
  }
}
