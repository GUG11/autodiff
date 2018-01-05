package huayu.zhang.chain;

public final class MultNode extends BinaryOpNode {
  public MultNode(Node left, Node right) {
    super(left, right);
  }

  @Override
  public double eval() {
    return left_.eval() * right_.eval();
  }

  @Override
  public double diffLeft() { return right_.eval(); }

  @Override
  public double diffRight() { return left_.eval(); }
}
