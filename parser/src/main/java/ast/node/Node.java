package ast.node;

import ast.visitor.NodeVisitor;

public interface Node {
  void accept(NodeVisitor visitor) throws Exception;
}
