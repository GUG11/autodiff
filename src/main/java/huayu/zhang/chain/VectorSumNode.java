package huayu.zhang.chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VectorSumNode extends VectorOpNode {
  private List<Double> weights_;

  public VectorSumNode(List<Node> children) {
    super(children);
    weights_ = new ArrayList<>(Collections.nCopies(children.size(), 1.0));
  }

  public VectorSumNode(List<Node> children, List<Double> weights) {
    super(children);
    weights_ = weights;
  }

  public void setWeights(List<Double> weights) { weights_ = new ArrayList<>(weights); }
  public void setWeight(int index, double w) { weights_.set(index, w); }

  @Override
  public double eval() {
    double v = 0.0;
    for (int i = 0; i < children_.size(); i++) {
      v += children_.get(i).eval() * weights_.get(i);
    }
    return v;
  }

  @Override
  public double diffChild(int index) {
    return weights_.get(index);
  }
}
