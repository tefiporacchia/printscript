import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;

public class SpaceTokenGenerator implements TokenGenerator {
  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isSpace(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    int index = lexicalRangeState.getIndex();
    Token token =
        new Token(
            DefaultTokenTypes.SPACE,
            index,
            index,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine()));

    LexicalRangeState newState =
        lexicalRangeState.updateState(
            lexicalRangeState.getIndex() + 1,
            lexicalRangeState.getLine(),
            lexicalRangeState.getColumn() + 1);

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isSpace(LexicalRangeState lexicalRangeState, String input) {
    return input.charAt(lexicalRangeState.getIndex()) == ' ';
  }
}
