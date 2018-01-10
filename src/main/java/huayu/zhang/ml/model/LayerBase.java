package huayu.zhang.ml.model;

import huayu.zhang.chain.Node;
import huayu.zhang.ml.model.Neuron;
import huayu.zhang.ml.model.Neuron.ActivationFuncType;

import java.util.List;
import java.util.ArrayList;

public abstract class LayerBase {
  protected List<Node> inputNodes_;
  protected List<Node> outputNodes_;

  /**
   * leave the initialization of output nodes to subclasses 
   * */
  public LayerBase(List<Node> inputNodes) {
    inputNodes_ = inputNodes;
    outputNodes_ = new ArrayList<>();
  }

  public List<Node> getOutputNodes() { return outputNodes_; }
}
