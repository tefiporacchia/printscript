import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.jetbrains.annotations.NotNull;

@Getter
public class KeywordTokenGenerator implements TokenGenerator {

  private List<String> keywords;

  public KeywordTokenGenerator() {
    keywords = new ArrayList<>();
    keywords.add("let");
    keywords.add("println");
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isKeyword(lexicalRangeState, input)) return new TokenGeneratorResult(lexicalRangeState);

    int index = lexicalRangeState.getIndex();
    String keyword = getNextWord(lexicalRangeState, input);
    Token token =
        new Token(
            DefaultTokenTypes.KEYWORD,
            index,
            index + keyword.length() - 1,
            new LexicalRange(
                lexicalRangeState.getColumn(),
                lexicalRangeState.getLine(),
                lexicalRangeState.getColumn() + keyword.length() - 1,
                lexicalRangeState.getLine()));
    LexicalRangeState newState =
        lexicalRangeState.updateState(
            index + keyword.length(),
            lexicalRangeState.getLine(),
            lexicalRangeState.getColumn() + keyword.length());
    System.out.println("UPDATED: " + newState.toString());
    return new TokenGeneratorResult(token, newState);
  }

  private boolean isKeyword(LexicalRangeState lexicalRangeState, String input) {
    return keywords.contains(getNextWord(lexicalRangeState, input));
  }

  @NotNull
  private String getNextWord(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();

    if (firstCharIsLetter(lexicalRangeState, input)) {

      string.append(input.charAt(lexicalRangeState.getIndex()));

      int i = lexicalRangeState.getIndex();

      while (i + 1 < input.length() && Character.isLetter(input.charAt(i + 1))) {
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
