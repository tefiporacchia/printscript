import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.austral.ingsis.printscript.common.Token;

@Getter
public class DefaultLexer implements Lexer {

  private final List<TokenGenerator> tokenGenerators;

  public DefaultLexer() {
    this.tokenGenerators = new ArrayList<>();
    tokenGenerators.add(new StringTokenGenerator());
    tokenGenerators.add(new NumberTokenGenerator());
    tokenGenerators.add(new SpaceTokenGenerator());
    tokenGenerators.add(new SkipLineTokenGenerator());
    tokenGenerators.add(new KeywordTokenGenerator());
    tokenGenerators.add(new OperatorTokenGenerator());
    tokenGenerators.add(new AssignationTokenGenerator());
    tokenGenerators.add(new IdentifierTokenGenerator());
    tokenGenerators.add(new SeparatorTokenGenerator());
  }

  @Override
  public List<Token> getTokens(String input) {
    List<Token> tokens = new ArrayList<>();

    LexicalRangeState lexicalRangeState = new LexicalRangeState();

    while (lexicalRangeState.getIndex() <= input.length() - 1) {
      for (TokenGenerator tokenGenerator : tokenGenerators) {
        TokenGeneratorResult tokenGeneratorResult = tokenGenerator.read(lexicalRangeState, input);
        if (tokenGeneratorResult.tokenWasGenerated()) {
          if (isNotSkipLineOrSpace(tokenGeneratorResult)) {
            tokens.add(tokenGeneratorResult.getToken());
          }
          lexicalRangeState = tokenGeneratorResult.getLexicalRangeState();
          break;
        }
      }
    }

    return tokens;
  }

  private boolean isNotSkipLineOrSpace(TokenGeneratorResult tokenGeneratorResult) {
    return tokenGeneratorResult.getToken().getType() != DefaultTokenTypes.SKIP_LINE
        && tokenGeneratorResult.getToken().getType() != DefaultTokenTypes.SPACE;
  }
}
