package huayu.zhang.chain;

public final class NegationNode extends UnaryOpNode {
  public NegationNode(Node child) {
    super(child);
  }

  @Override
  public double eval() {
    return -child_.eval();
  }

  @Override
  public double diffChild() {
    return -1.0;
  }
}
