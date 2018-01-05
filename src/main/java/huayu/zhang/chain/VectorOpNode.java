package huayu.zhang.chain;

import java.util.ArrayList;
import java.util.List;

public abstract class VectorOpNode implements Node {
  protected List<Node> children_;

  public VectorOpNode(List<Node> children) {
    children_ = children;
  }

  public abstract double diffChild(int index);

  public void setChild(int index, Node node) { children_.set(index, node); }

  @Override
  public final double evalDiff(VariableNode x) {
    double df = 0.0;
    for (int i = 0; i < children_.size(); i++) {
      df += diffChild(i) * children_.get(i).evalDiff(x);
    }
    return df;
  }
}
