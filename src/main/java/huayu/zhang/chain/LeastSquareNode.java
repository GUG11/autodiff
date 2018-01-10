package huayu.zhang.chain;

public final class LeastSquareNode extends BinaryOpNode {
  private PolynomialNode err_;

  public LeastSquareNode(Node left, Node right) {
    super(left, right);
    err_ = new PolynomialNode(new MinusNode(left, right),
        new double[]{0.0, 0.0, 0.5});
  }

  @Override
  public double eval() {
    return err_.eval();
  }

  @Override
  public double diffLeft() { return err_.evalDiff(left_); }

  @Override
  public double diffRight() { return err_.evalDiff(right_); }
}
