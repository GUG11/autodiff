package huayu.zhang.chain;

public final class ReLuNode extends UnaryOpNode {
  public ReLuNode(Node child) {
    super(child);
  }

  @Override
  public double eval() {
    return Math.max(0, child_.eval());  // TODO: cache child
  }

  @Override
  public double diffChild() {
    return child_.eval() >= 0.0 ? 1.0 : 0.0;
  }
}
