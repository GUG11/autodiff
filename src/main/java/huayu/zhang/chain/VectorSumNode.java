package huayu.zhang.chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VectorSumNode extends VectorOpNode {
  private List<Double> coeffs_;

  public VectorSumNode(List<Node> children) {
    super(children);
    coeffs_ = new ArrayList<>(Collections.nCopies(children.size(), 1.0));
  }

  public VectorSumNode(List<Node> children, List<Double> coeffs) {
    super(children);
    coeffs_ = coeffs;
  }

  public void setCoeffs(List<Double> coeffs) { coeffs_ = new ArrayList<>(coeffs); }
  public List<Double> getcoeffs() { return coeffs_; }
  public void setCoeff(int index, double w) { coeffs_.set(index, w); }

  @Override
  public double eval() {
    double v = 0.0;
    for (int i = 0; i < children_.size(); i++) {
      v += children_.get(i).eval() * coeffs_.get(i);
    }
    return v;
  }

  @Override
  public double diffChild(int index) {
    return coeffs_.get(index);
  }
}
