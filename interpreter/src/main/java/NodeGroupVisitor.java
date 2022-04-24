import ast.node.Assignation;
import ast.node.Declaration;
import ast.node.MultiExpression;
import ast.node.Node;
import ast.node.NodeGroupResult;
import ast.node.Println;
import ast.visitor.NodeVisitor;
import lombok.Getter;

@Getter
public class NodeGroupVisitor implements NodeVisitor {

  private PrintlnResult printLnResult = new PrintlnResult();

  private Evaluator evaluator = new Evaluator();

  @Override
  public void visit(NodeGroupResult nodeGroupResult) throws Exception {
    for (Node node : nodeGroupResult.getNodes()) {
      node.accept(this);
    }
  }

  @Override
  public void visit(Declaration declaration) throws Exception {
    String type = declaration.getType();
    String name = declaration.getVarName();
    MultiExpression multiExpression = declaration.getVal();

    evaluator.declareVariable(name);

    if (multiExpression != null) {
      assignValue(type, name, multiExpression);
    }

    evaluator.addVariableWithType(name, type);
  }

  @Override
  public void visit(Assignation assignation) throws Exception {
    String name = assignation.getName();
    MultiExpression multiExpression = assignation.getVal();

    if (variableHasDefinedType(name)) {
      assignValue(evaluator.getVariableType(name), name, multiExpression);
    } else {
      throw new IllegalArgumentException("variable has no defined type");
    }
  }

  @Override
  public void visit(Println printLn) throws Exception {
    printLn.getContent().accept(evaluator);
    String output = evaluator.getOutput();
    this.printLnResult.addContent(output);
  }

  private boolean variableHasDefinedType(String name) {
    return evaluator.getVariableType(name) != null;
  }

  private void assignValue(String type, String name, MultiExpression multiExpression)
      throws Exception {
    multiExpression.accept(evaluator);
    if (!evaluator.validateType(type)) {
      throw new IllegalArgumentException("Not valid type");
    }
    evaluator.assignVariable(name);
  }
}
