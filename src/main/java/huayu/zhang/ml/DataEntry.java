package huayu.zhang.ml;

import java.util.List;
import java.util.ArrayList;

public class DataEntry {
  private List<Double> features_;
  private List<Double> targets_;

  public DataEntry(List<Double> features, List<Double> targets) {
    features_ = features;
    targets_ = targets;
  }

  public DataEntry(List<Double> features, double target) {
    features_ = features;
    targets_ = new ArrayList<>();
    targets_.add(target);
  }

  public List<Double> getFeatures() { return features_; }
  public List<Double> getTargets() { return targets_; }
  public double getTarget(int index) { return targets_.get(index); }
}
