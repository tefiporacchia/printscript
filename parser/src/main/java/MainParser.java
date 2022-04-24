import ast.node.Node;
import ast.node.NodeGroupResult;
import org.austral.ingsis.printscript.common.CoreTokenTypes;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.Content;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class MainParser extends TokenConsumer implements Parser<Node> {

  private final PrintlnParser printLnParser = new PrintlnParser(getStream());
  private final AssignmentParser assignmentParser = new AssignmentParser(getStream());
  private final DeclarationParser declarationParser = new DeclarationParser(getStream());

  public MainParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Node createNode() throws Exception {
    NodeGroupResult nodeGroup = new NodeGroupResult();

    Content<String> followingToken;

    while (peek(CoreTokenTypes.EOF) == null) {
      followingToken = peek(DefaultTokenTypes.KEYWORD);
      if (followingToken == null) {
        nodeGroup.addNode(assignmentParser.createNode());
      } else {
        if (isLet(followingToken)) {
          nodeGroup.addNode(declarationParser.createNode());
        } else if (isPrintln(followingToken)) {
          nodeGroup.addNode(printLnParser.createNode());
        } else throw new Exception("Unexpected keyword: " + followingToken.getContent());
      }
      consume(DefaultTokenTypes.SEPARATOR, ";");
    }

    return nodeGroup;
  }

  private boolean isPrintln(Content<String> followingToken) {
    return followingToken.getContent().equals("println");
  }

  private boolean isLet(Content<String> followingToken) {
    return followingToken.getContent().equals("let");
  }
}
