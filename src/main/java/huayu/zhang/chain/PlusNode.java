package huayu.zhang.chain;

public final class PlusNode extends BinaryOpNode {
  public PlusNode(Node left, Node right) {
    super(left, right);
  }

  @Override
  public double eval() {
    return left_.eval() + right_.eval();
  }

  @Override
  public double diffLeft() { return 1.0; }

  @Override
  public double diffRight() { return 1.0; }
}
