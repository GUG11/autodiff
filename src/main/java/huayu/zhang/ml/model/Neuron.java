package huayu.zhang.ml.model;

import huayu.zhang.chain.Node;
import huayu.zhang.chain.MultNode;
import huayu.zhang.chain.ReLuNode;
import huayu.zhang.chain.SigmoidNode;
import huayu.zhang.chain.ScaleNode;
import huayu.zhang.chain.VariableNode;
import huayu.zhang.chain.VectorSumNode;

import java.util.List;
import java.util.ArrayList;

public class Neuron implements Node {
  public static enum ActivationFuncType { IDENTITY, RELU, SIGMOID };
  private List<Node> inputNodes_;
  private List<Node> weightNodes_;
  private List<Node> multNodes_;
  private Node dotNode_;
  private Node activationNode_;

  public Neuron(List<Node> inputNodes, List<Double> w0, ActivationFuncType acType) {
    assert inputNodes.size() == w0.size();
    inputNodes_ = inputNodes;
    weightNodes_ = new ArrayList<>();
    multNodes_ = new ArrayList<>();
    for (int i = 0; i < inputNodes_.size(); i++) {
      weightNodes_.add(new VariableNode(w0.get(i)));
      multNodes_.add(i, new MultNode(weightNodes_.get(i), inputNodes_.get(i)));
    }
    dotNode_ = new VectorSumNode(multNodes_);
    switch (acType) {
      case IDENTITY:
        activationNode_ = new ScaleNode(dotNode_, 1.0);
        break;
      case RELU:
        activationNode_ = new ReLuNode(dotNode_);
        break;
      case SIGMOID:
        activationNode_ = new SigmoidNode(dotNode_);
        break;
      default:
        System.err.println("Illegal activation function type: " + acType);
    }
  }

  @Override
  public double eval() {
    return activationNode_.eval();
  }

  @Override
  public double evalDiff(VariableNode x) {
    return activationNode_.evalDiff(x);
  }

  public double weightDiff(int index) {
    return evalDiff((VariableNode)weightNodes_.get(index));
  }
}
