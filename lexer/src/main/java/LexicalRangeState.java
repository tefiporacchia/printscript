import lombok.Getter;

@Getter
public class LexicalRangeState {
  private int index;
  private int line;
  private int column;

  public LexicalRangeState() {
    this.line = 0;
    this.column = 0;
    this.index = 0;
  }

  public LexicalRangeState(int index, int line, int column) {
    this.index = index;
    this.line = line;
    this.column = column;
  }

  public LexicalRangeState updateState(int index, int line, int column) {
    return new LexicalRangeState(index, line, column);
  }

  public LexicalRangeState lineBreak() {
    return new LexicalRangeState(index + 1, line + 1, 0);
  }

  @Override
  public String toString() {
    return "LexicalRangeState{" + "index=" + index + ", line=" + line + ", column=" + column + '}';
  }
}
