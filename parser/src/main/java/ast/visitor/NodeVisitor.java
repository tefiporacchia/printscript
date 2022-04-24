package ast.visitor;

import ast.node.Assignation;
import ast.node.Declaration;
import ast.node.NodeGroupResult;
import ast.node.Println;

public interface NodeVisitor {
  void visit(NodeGroupResult nodeGroupResult) throws Exception;

  void visit(Declaration declaration) throws Exception;

  void visit(Assignation assignation) throws Exception;

  void visit(Println printLn) throws Exception;
}
