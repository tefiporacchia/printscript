import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

public class IdentifierTokenGenerator implements TokenGenerator {
  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isIdentifier(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    int index = lexicalRangeState.getIndex();
    String identifier = getNextWord(lexicalRangeState, input);
    Token token =
        new Token(
            DefaultTokenTypes.IDENTIFIER,
            index,
            index + identifier.length() - 1,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn() + identifier.length() - 1,
                lexicalRangeState.getLine()));
    LexicalRangeState newState =
        lexicalRangeState.updateState(
            index + identifier.length(),
            lexicalRangeState.getLine(),
            lexicalRangeState.getColumn() + identifier.length());

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isIdentifier(LexicalRangeState lexicalRangeState, String input) {
    return firstCharIsLetter(lexicalRangeState, input);
  }

  @NotNull
  private String getNextWord(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();

    if (firstCharIsLetter(lexicalRangeState, input)) {

      string.append(input.charAt(lexicalRangeState.getIndex()));

      int i = lexicalRangeState.getIndex();

      while (i + 1 < input.length() && Character.isLetterOrDigit(input.charAt(i + 1))) {
        char nextChar = input.charAt(i + 1);
        string.append(nextChar);
        i++;
      }
    }

    return string.toString();
  }

  private boolean firstCharIsLetter(LexicalRangeState lexicalRangeState, String input) {
    return Character.isLetter(input.charAt(lexicalRangeState.getIndex()));
  }
}
