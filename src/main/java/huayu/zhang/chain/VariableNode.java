package huayu.zhang.chain;

/**
 * Leaf nodes of the expression
 * */
public final class VariableNode implements Node {
  private double val_;

  public VariableNode(double val) {
    val_ = val;
  }

  public void setValue(double val) { val_ = val; }
  public void increase(double val) { val_ += val; }
  public double getValue() { return val_; }

  @Override
  public double eval() { 
    return val_;
  }
  
  @Override
  public double evalDiff(Node x) {
    return this == x ? 1.0 : 0.0;
  }
}
