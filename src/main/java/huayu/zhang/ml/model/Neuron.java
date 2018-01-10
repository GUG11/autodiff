package huayu.zhang.ml.model;

import huayu.zhang.chain.Node;
import huayu.zhang.chain.MultNode;
import huayu.zhang.chain.ReLuNode;
import huayu.zhang.chain.SigmoidNode;
import huayu.zhang.chain.ScaleNode;
import huayu.zhang.chain.VariableNode;
import huayu.zhang.chain.VectorSumNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Neuron implements Node {
  public static enum ActivationFuncType { IDENTITY, RELU, SIGMOID };
  private List<Node> inputNodes_;
  private List<Node> weightNodes_;
  private List<Node> multNodes_;
  private Node dotNode_;
  private Node activationNode_;

  public Neuron(List<Node> inputNodes, double w0, ActivationFuncType acType, boolean biased) {
    int numWeights = biased ? inputNodes.size() + 1 : inputNodes.size();
    List<Double> wv = new ArrayList<>(Collections.nCopies(numWeights, w0));
    init(inputNodes, wv, acType);
  }

  public Neuron(List<Node> inputNodes, List<Double> w0, ActivationFuncType acType) {
    init(inputNodes, w0, acType);
  }

  /**
   * If inputNodes.size() == w0.size(), the neuron does not have a bias term
   * If inputNodes.size() + 1 == w0.size(), the neuron have a bias term with initial value w0[w0.size()-1]
   * */
  public void init(List<Node> inputNodes, List<Double> w0, ActivationFuncType acType) {
    assert inputNodes.size() == w0.size() || inputNodes.size() + 1 == w0.size();
    inputNodes_ = inputNodes;
    weightNodes_ = new ArrayList<>();
    multNodes_ = new ArrayList<>();
    for (int i = 0; i < inputNodes_.size(); i++) {
      weightNodes_.add(new VariableNode(w0.get(i)));
      multNodes_.add(new MultNode(weightNodes_.get(i), inputNodes_.get(i)));
    }
    if (inputNodes_.size() + 1 == w0.size()) {
      VariableNode b = new VariableNode(w0.get(inputNodes_.size()));
      weightNodes_.add(b);
      multNodes_.add(b);
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
  public double evalDiff(Node x) {
    return activationNode_.evalDiff(x);
  }

  public double weightDiff(int index) {
    return evalDiff(getWeightNode(index));
  }

  public Node getWeightNode(int index) {
    return weightNodes_.get(index);
  }

  public void setWeightValue(int index, double val) {
    ((VariableNode)weightNodes_.get(index)).setValue(val);
  }

  public void setWeightValues(List<Double> values) {
    assert weightNodes_.size() == values.size();
    for (int i = 0; i < values.size(); i++) {
      setWeightValue(i, values.get(i));
    }
  }
}
