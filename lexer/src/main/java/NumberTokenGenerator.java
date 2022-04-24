import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

public class NumberTokenGenerator implements TokenGenerator {
  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isNumber(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    int index = lexicalRangeState.getIndex();
    String number = getNumber(lexicalRangeState, input);
    Token token =
        new Token(
            DefaultTokenTypes.LITERAL,
            index,
            index + number.length() - 1,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn() + number.length() - 1,
                lexicalRangeState.getLine()));
    LexicalRangeState newState =
        lexicalRangeState.updateState(
            index + number.length(),
            lexicalRangeState.getLine(),
            lexicalRangeState.getColumn() + number.length());
    // return token;
    return new TokenGeneratorResult(token, newState);
  }

  private boolean isNumber(LexicalRangeState lexicalRangeState, String input) {
    return firstCharIsDigit(lexicalRangeState, input);
  }

  @NotNull
  private String getNumber(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();

    if (firstCharIsDigit(lexicalRangeState, input)) {

      string.append(input.charAt(lexicalRangeState.getIndex()));

      int i = lexicalRangeState.getIndex();

      while (i + 1 < input.length()
          && (Character.isDigit(input.charAt(i + 1)) || input.charAt(i + 1) == '.')) {
        char nextChar = input.charAt(i + 1);
        string.append(nextChar);
        i++;
      }
    }

    return string.toString();
  }

  private boolean firstCharIsDigit(LexicalRangeState lexicalRangeState, String input) {
    return Character.isDigit(input.charAt(lexicalRangeState.getIndex()));
  }
}
