import ast.node.Declaration;
import ast.node.MultiExpression;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class DeclarationParser extends TokenConsumer implements Parser<Declaration> {

  private final MultiExpressionParser multiExpressionParser =
      new MultiExpressionParser(getStream());

  public DeclarationParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Declaration createNode() throws Exception {
    consume(DefaultTokenTypes.KEYWORD, "let");
    String variable = getVariable();
    lookForSeparator();
    String type = getType();
    if (separatorFound()) return new Declaration(variable, type);
    lookForAssignment();
    MultiExpression multiExpression = multiExpressionParser.createNode();
    return new Declaration(variable, type, multiExpression);
  }

  private void lookForAssignment() throws Exception {
    if (noAssignmentFound()) throw new Exception("No = found");
    consume(DefaultTokenTypes.ASSIGN, "=");
  }

  private String getType() throws Exception {
    if (noIdentifierFound()) throw new Exception("No type found");
    String type = consume(DefaultTokenTypes.IDENTIFIER).getContent();
    return type;
  }

  private void lookForSeparator() throws Exception {
    if (noSeparatorFound()) throw new Exception("No : found");
    consume(DefaultTokenTypes.SEPARATOR, ":");
  }

  private String getVariable() throws Exception {
    if (noIdentifierFound()) throw new Exception("No identifier found");
    return consume(DefaultTokenTypes.IDENTIFIER).getContent();
  }

  private boolean noAssignmentFound() {
    return peek(DefaultTokenTypes.ASSIGN, "=") == null;
  }

  private boolean separatorFound() {
    return peek(DefaultTokenTypes.SEPARATOR, ";") != null;
  }

  private boolean noSeparatorFound() {
    return peek(DefaultTokenTypes.SEPARATOR) == null;
  }

  private boolean noIdentifierFound() {
    return peek(DefaultTokenTypes.IDENTIFIER) == null;
  }
}
