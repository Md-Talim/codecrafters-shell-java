class Navigator {
  private String currentWorkingDirectory;

  Navigator() {
    this.currentWorkingDirectory = System.getProperty("user.dir");
  }

  public String getWorkingDirectory() {
    return currentWorkingDirectory;
  }
}
