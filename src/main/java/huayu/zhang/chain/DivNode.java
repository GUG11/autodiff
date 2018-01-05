package huayu.zhang.chain;

public final class DivNode extends BinaryOpNode {
  public DivNode(Node left, Node right) {
    super(left, right);
  }

  @Override
  public double eval() {
    return left_.eval() / right_.eval();
  }

  @Override
  public double diffLeft() { return 1.0 / right_.eval(); }

  @Override
  public double diffRight() { 
    double rightVal = right_.eval();
    return -left_.eval() / (rightVal * rightVal); 
  }
}
