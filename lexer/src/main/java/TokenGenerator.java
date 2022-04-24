public interface TokenGenerator {

  TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input);
}
