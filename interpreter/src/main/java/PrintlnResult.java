public class PrintlnResult {
  private StringBuilder content = new StringBuilder();

  public void addContent(String moreText) {
    content.append(moreText);
  }

  public String getContent() {
    return content.toString();
  }
}
