package huayu.zhang.chain;

public abstract class UnaryOpNode implements Node {
  protected Node child_;

  public UnaryOpNode(Node child) {
    child_ = child;
  }

  public abstract double diffChild();

  public void setChild(Node child) {
    child_ = child;
  }

  @Override
  public final double evalDiff(Node x) {
    return (this == x) ? 1.0 : diffChild() * child_.evalDiff(x);
  }
}
