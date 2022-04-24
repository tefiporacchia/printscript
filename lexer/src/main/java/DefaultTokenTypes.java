import lombok.Getter;
import org.austral.ingsis.printscript.common.TokenType;
import org.jetbrains.annotations.NotNull;

@Getter
public enum DefaultTokenTypes implements TokenType {
  KEYWORD("KEYWORD"),
  OPERATOR("OPERATOR"),
  LITERAL("LITERAL"),
  SEPARATOR("SEPARATOR"),
  IDENTIFIER("IDENTIFIER"),
  ASSIGN("ASSIGN"),
  SKIP_LINE("SKIP_LINE"),
  SPACE("SPACE");

  private String type;

  DefaultTokenTypes(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(@NotNull TokenType tokenType) {
    // todo:implement
    return false;
  }
}
