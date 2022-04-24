import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;

public class SkipLineTokenGenerator implements TokenGenerator {

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isLineBreak(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    int index = lexicalRangeState.getIndex();
    Token token =
        new Token(
            DefaultTokenTypes.SKIP_LINE,
            index,
            index,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine()));

    LexicalRangeState newState = lexicalRangeState.lineBreak();
    return new TokenGeneratorResult(token, newState);
  }

  private boolean isLineBreak(LexicalRangeState lexicalRangeState, String input) {
    return input.charAt(lexicalRangeState.getIndex()) == '\n';
  }
}
