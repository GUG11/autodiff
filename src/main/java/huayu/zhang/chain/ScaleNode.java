package huayu.zhang.chain;

public final class ScaleNode extends UnaryOpNode {
  private double k_;

  public ScaleNode(Node child, double k) {
    super(child);
    k_ = k;
  }

  @Override
  public double eval() {
    return child_.eval() * k_;
  }

  @Override
  public double diffChild() {
    return k_;
  }
}
