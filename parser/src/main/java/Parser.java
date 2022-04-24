import ast.node.Node;

public interface Parser<Node> {
  Node createNode() throws Exception;
}
