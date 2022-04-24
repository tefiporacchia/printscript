import lombok.Getter;
import org.austral.ingsis.printscript.common.Token;

@Getter
public class TokenGeneratorResult {
  private Token token;
  private LexicalRangeState lexicalRangeState;

  public TokenGeneratorResult(Token token, LexicalRangeState lexicalRangeState) {
    this.token = token;
    this.lexicalRangeState = lexicalRangeState;
  }

  public TokenGeneratorResult(LexicalRangeState lexicalRangeState) {
    this(null, lexicalRangeState);
  }

  public boolean tokenWasGenerated() {
    return token != null;
  }
}
