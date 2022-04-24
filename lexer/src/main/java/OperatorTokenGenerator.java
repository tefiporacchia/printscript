import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

@Getter
public class OperatorTokenGenerator implements TokenGenerator {

  private List<String> operators;

  public OperatorTokenGenerator() {
    operators = new ArrayList<>();
    operators.add("+");
    operators.add("-");
    operators.add("*");
    operators.add("/");
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isOperator(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(lexicalRangeState);
    LexicalRangeState newState = updateState(lexicalRangeState);

    return new TokenGeneratorResult(token, newState);
  }

  private LexicalRangeState updateState(LexicalRangeState lexicalRangeState) {
    return lexicalRangeState.updateState(
        lexicalRangeState.getIndex() + 1,
        lexicalRangeState.getLine(),
        lexicalRangeState.getColumn() + 1);
  }

  @NotNull
  private Token createToken(LexicalRangeState lexicalRangeState) {
    int index = lexicalRangeState.getIndex();
    return new Token(
        DefaultTokenTypes.OPERATOR,
        index,
        index,
        new LexicalRange(
            lexicalRangeState.getColumn(),
            lexicalRangeState.getLine(),
            lexicalRangeState.getColumn(),
            lexicalRangeState.getLine()));
  }

  private String getOperator(LexicalRangeState lexicalRangeState, String input) {
    String nextChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    if (operators.contains(nextChar)) {
      return nextChar;
    }
    return null;
  }

  private boolean isOperator(LexicalRangeState lexicalRangeState, String input) {
    String nextChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    return operators.contains(nextChar);
  }
}
