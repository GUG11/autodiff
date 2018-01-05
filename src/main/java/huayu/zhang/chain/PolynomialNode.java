package huayu.zhang.chain;

public final class PolynomialNode extends UnaryOpNode {
  private double[] coeffs_; // a[0] + a[1] * x + a[2] * x^2 + ...

  public PolynomialNode(Node child, double[] coeffs) {
    super(child);
    coeffs_ = coeffs;
  }

  @Override
  public double eval() {
    double x = child_.eval();
    double y = coeffs_[coeffs_.length - 1];
    for (int i = coeffs_.length - 2; -1 < i; i--) {
      y = y * x + coeffs_[i];
    }
    return y;
  }

  @Override
  public double diffChild() {
    double x = child_.eval();
    double y = (coeffs_.length - 1) * coeffs_[coeffs_.length - 1];
    for (int i = coeffs_.length - 2; 0 < i; i--) {
      y = y * x + i * coeffs_[i];
    }
    return y;
  }
}
