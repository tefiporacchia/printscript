import java.util.ArrayList;
import java.util.List;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

public class AssignationTokenGenerator implements TokenGenerator {

  private List<String> assignations;

  public AssignationTokenGenerator() {
    assignations = new ArrayList<>();
    assignations.add("=");
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isAssignation(lexicalRangeState, input))
      return new TokenGeneratorResult(lexicalRangeState);

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
    Token token =
        new Token(
            DefaultTokenTypes.ASSIGN,
            index,
            index,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine()));
    return token;
  }

  private boolean isAssignation(LexicalRangeState lexicalRangeState, String input) {
    String followingChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    return assignations.contains(followingChar);
  }
}
