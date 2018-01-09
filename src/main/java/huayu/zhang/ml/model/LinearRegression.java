package huayu.zhang.ml.model;


import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import huayu.zhang.chain.Node;
import huayu.zhang.chain.MinusNode;
import huayu.zhang.chain.VariableNode;
import huayu.zhang.chain.VectorSumNode;
import huayu.zhang.chain.PolynomialNode;

import huayu.zhang.ml.DataEntry;


public class LinearRegression implements ModelBase {
  private List<Node> weights_;
  private VectorSumNode func_;
  private VariableNode output_;
  private MinusNode diff_;
  private PolynomialNode err_;
  private Random random_;
  private double relaxationFactor_;
  private int numEpoches_;
  
  public LinearRegression(List<Double> w0, double relaxation, int T) {
    random_ = new Random();
    weights_ = new ArrayList<>();
    for (int i = 0; i < w0.size(); i++) {
      weights_.add(new VariableNode(w0.get(i)));
    }
    func_ = new VectorSumNode(weights_);
    output_ = new VariableNode(0.0);
    diff_ = new MinusNode(func_, output_);
    err_ = new PolynomialNode(diff_, new double[]{0, 0, 0.5});
    relaxationFactor_ = relaxation;
    numEpoches_ = T;
  }


  public void SGDStep(DataEntry entry) {
    List<Double> dw = new ArrayList<>();
    func_.setCoeffs(entry.getFeatures());
    output_.setValue(entry.getTarget(0));
    // compute differentials
    for (Node wNode: weights_) {
      dw.add(err_.evalDiff((VariableNode)wNode));
    }
    // update weights
    for (int i = 0; i < dw.size(); i++) {
      ((VariableNode)weights_.get(i)).increase(-relaxationFactor_ * dw.get(i));
    }
  }

  public void setRelaxationFactor(double eta) { relaxationFactor_ = eta; }
  public void setNumEpoches(int T) { numEpoches_ = T; }

  public void setWeights(List<Double> w0) {
    assert weights_.size() == w0.size();
    for (int i = 0; i < w0.size(); i++) {
      ((VariableNode)weights_.get(i)).setValue(w0.get(i));
    }
  }

  public List<Double> getWeights() {
    List<Double> weightVals = new ArrayList<>();
    for (Node wNode: weights_) {
      weightVals.add(wNode.eval());
    }
    return weightVals;
  }

  @Override 
  public void fit(List<DataEntry> entries) {
    for (int t = 0; t < numEpoches_; t++) {
      int i = random_.nextInt();
      SGDStep(entries.get(i));
    }
  }

  @Override 
  public List<Double> predict(List<Double> features) {
    List<Double> outputs = new ArrayList<>();
    func_.setCoeffs(features);
    outputs.add(func_.eval());
    return outputs;
  }
}
