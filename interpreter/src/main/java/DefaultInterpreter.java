import ast.node.NodeGroupResult;

public class DefaultInterpreter implements Interpreter {

  private final NodeGroupVisitor visitor;

  public DefaultInterpreter(NodeGroupVisitor visitor) {
    this.visitor = visitor;
  }

  @Override
  public PrintlnResult interpret(NodeGroupResult nodeGroupResult) throws Exception {
    nodeGroupResult.accept(visitor);
    return visitor.getPrintLnResult();
  }
}
