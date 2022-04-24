import ast.node.MultiExpression;
import ast.node.Println;
import org.austral.ingsis.printscript.common.TokenConsumer;
import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class PrintlnParser extends TokenConsumer implements Parser<Println> {
  private final MultiExpressionParser expressionParser = new MultiExpressionParser(getStream());

  public PrintlnParser(@NotNull TokenIterator stream) {
    super(stream);
  }

  @Override
  public Println createNode() throws Exception {
    consume(DefaultTokenTypes.KEYWORD, "println");
    if (noOpenParenthesisFound()) throw new Exception("No open parenthesis found");
    consume(DefaultTokenTypes.SEPARATOR, "(");
    MultiExpression content = expressionParser.createNode();
    if (noCLosingParenthesisFound()) throw new Exception("No closing parenthesis found");
    consume(DefaultTokenTypes.SEPARATOR, ")");
    return new Println(content);
  }

  private boolean noCLosingParenthesisFound() {
    return peek(DefaultTokenTypes.SEPARATOR, ")") == null;
  }

  private boolean noOpenParenthesisFound() {
    return peek(DefaultTokenTypes.SEPARATOR, "(") == null;
  }
}
