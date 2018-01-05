package huayu.zhang.chain;

public final class SinNode extends UnaryOpNode {
  public SinNode(Node child) {
    super(child);
  }

  @Override
  public double eval() {
    return Math.sin(child_.eval());  // TODO: cache child
  }

  @Override
  public double diffChild() {
    return Math.cos(child_.eval());
  }
}
