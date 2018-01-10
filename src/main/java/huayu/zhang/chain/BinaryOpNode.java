package huayu.zhang.chain;

public abstract class BinaryOpNode implements Node {
  protected Node left_;
  protected Node right_;

  public BinaryOpNode(Node left, Node right) {
    left_ = left;
    right_ = right;
  }

  public abstract double diffLeft();
  public abstract double diffRight();

  public void setLeft(Node left) { left_ = left; }
  public void setRight(Node right) { right_ = right; }

  @Override
  public final double evalDiff(Node x) {
    return (this == x) ? 1.0 : (diffLeft() * left_.evalDiff(x) + diffRight() * right_.evalDiff(x));
  }
}
