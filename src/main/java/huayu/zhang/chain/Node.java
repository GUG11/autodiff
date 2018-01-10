package huayu.zhang.chain;

public interface Node {
  abstract public double eval();
  abstract public double evalDiff(Node x);
}
