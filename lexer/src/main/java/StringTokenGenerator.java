import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

public class StringTokenGenerator implements TokenGenerator {

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {

    if (!startsWithQuoteMarks(lexicalRangeState, input))
      return new TokenGeneratorResult(lexicalRangeState);
    if (hasClosingMark(lexicalRangeState, input)) {
      return createToken(lexicalRangeState, input);
    } else {
      throw new IllegalArgumentException("Missing closing quotemark");
    }
  }

  @NotNull
  private TokenGeneratorResult createToken(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();
    int index = lexicalRangeState.getIndex();
    int closingQuoteMark = getClosingQuoteMark(lexicalRangeState, input);
    string.append(input, index, index + closingQuoteMark + 2);
    Token token =
        new Token(
            DefaultTokenTypes.LITERAL,
            index,
            index + string.length() - 1,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn() + string.length() - 1,
                lexicalRangeState.getLine()));
    LexicalRangeState newState = updateState(lexicalRangeState, index, closingQuoteMark);
    return new TokenGeneratorResult(token, newState);
  }

  private LexicalRangeState updateState(
      LexicalRangeState lexicalRangeState, int index, int closingQuoteMark) {
    return lexicalRangeState.updateState(
        index + closingQuoteMark + 2,
        lexicalRangeState.getLine(),
        lexicalRangeState.getColumn() + closingQuoteMark + 2);
  }

  private boolean startsWithQuoteMarks(LexicalRangeState lexicalRangeState, String input) {
    char currChar = input.charAt(lexicalRangeState.getIndex());
    return currChar == '\'' || currChar == '"';
  }

  private boolean hasClosingMark(LexicalRangeState lexicalRangeState, String input) {
    int closingQuoteMark = getClosingQuoteMark(lexicalRangeState, input);
    return closingQuoteMark != -1;
  }

  private int getClosingQuoteMark(LexicalRangeState lexicalRangeState, String input) {
    char quotemark = input.charAt(lexicalRangeState.getIndex());
    return input.substring(lexicalRangeState.getIndex() + 1).indexOf(quotemark);
  }
}
