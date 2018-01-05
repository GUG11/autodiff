package huayu.zhang.ml.model;

import java.util.List;

import huayu.zhang.ml.DataEntry;

public interface ModelBase {
  public void fit(List<DataEntry> entries);
  public Double predict(List<Double> features);
}
