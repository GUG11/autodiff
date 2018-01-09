package huayu.zhang.chain;

public final class SigmoidNode extends UnaryOpNode {
  public SigmoidNode(Node child) {
    super(child);
  }

  @Override
  public double eval() {
    return 1.0 / (1.0 + Math.exp(-child_.eval()));  // TODO: cache child
  }

  @Override
  public double diffChild() {
    double s = this.eval();
    return s * (1.0 - s);
  }
}
