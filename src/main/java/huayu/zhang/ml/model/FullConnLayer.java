package huayu.zhang.ml.model;

import huayu.zhang.chain.Node;
import huayu.zhang.ml.model.Neuron;
import huayu.zhang.ml.model.Neuron.ActivationFuncType;

import java.util.List;
import java.util.ArrayList;

public class FullConnLayer extends LayerBase {
  public FullConnLayer(List<Node> inputNodes, int numOutputs, double w0, 
      ActivationFuncType acType, boolean biased) {
    super(inputNodes);
    for (int i = 0; i < numOutputs; i++) {
      outputNodes_.add(new Neuron(inputNodes, w0, acType, biased));
    }
  }

  public void setWeights(int outIndex, List<Double> weightVals) {
    ((Neuron)outputNodes_.get(outIndex)).setWeightValues(weightVals);
  }
}
