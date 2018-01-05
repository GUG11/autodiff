package huayu.zhang.ml;

import java.util.List;

public class DataEntry {
  private List<Double> features_;
  private double target_;

  public DataEntry(List<Double> features, double target) {
    features_ = features;
    target_ = target;
  }

  public List<Double> getFeatures() { return features_; }
  public double getTarget() { return target_; }
}
