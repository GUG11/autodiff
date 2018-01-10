package huayu.zhang.ml.model;

import java.util.List;
import java.util.ArrayList;

import huayu.zhang.ml.DataEntry;


public class NeuronNetwork implements ModelBase {
  private List<LayerBase> layers_;

  public NeuronNetwork(List<LayerBase> layers) {
    layers_ = layers;
  }

  @Override
  public void fit(List<DataEntry> entries) {
  }

  @Override
  public List<Double> predict(List<Double> features) {
    // TODO
    return null;
  }
}
