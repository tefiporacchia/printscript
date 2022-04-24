import java.util.List;
import org.austral.ingsis.printscript.common.Token;

public interface Lexer {
  List<Token> getTokens(String input);
}
