package huayu.zhang.chain;

import java.util.List;
import java.util.ArrayList;

public final class VectorLeastSquareNode implements Node {
  private List<Node> errList_;
  private Node errSum_;

  public VectorLeastSquareNode(List<Node> lefts, List<Node> rights) {
    assert lefts.size() == rights.size();
    errList_ = new ArrayList<>();
    for (int i = 0; i < lefts.size(); i++) {
      errList_.add(new LeastSquareNode(lefts.get(i), rights.get(i)));
    }
    errSum_ = new VectorSumNode(errList_);
  }

  @Override
  public double eval() { return errSum_.eval(); }

  @Override
  public double evalDiff(Node x) {
    return (this == x) ? 1.0 : errSum_.evalDiff(x);
  }
}
