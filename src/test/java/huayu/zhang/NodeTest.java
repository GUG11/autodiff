package huayu.zhang;

import huayu.zhang.chain.Node;
import huayu.zhang.chain.MultNode;
import huayu.zhang.chain.NegationNode;
import huayu.zhang.chain.ReLuNode;
import huayu.zhang.chain.SigmoidNode;
import huayu.zhang.chain.SinNode;
import huayu.zhang.chain.PolynomialNode;
import huayu.zhang.chain.VariableNode;
import huayu.zhang.chain.LeastSquareNode;
import huayu.zhang.chain.VectorLeastSquareNode;

import huayu.zhang.chain.PlusNode;
import huayu.zhang.chain.VectorSumNode;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class NodeTest extends TestCase
{
  private final static double EPSILON = 1e-8;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NodeTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( NodeTest.class );
    }

    public void testVariableNode1() {
      double v = 1.0;
      Node n = new VariableNode(v);
      assertEquals(n.eval(), v);
      assertEquals(n.evalDiff(n), 1.0, EPSILON);
    }

    public void testVariableNode2() {
      double v1 = 1.0;
      double v2 = 2.0;
      VariableNode n = new VariableNode(v1);
      assertEquals(n.eval(), v1);
      assertEquals(n.getValue(), v1);
      n.setValue(v2);
      assertEquals(n.getValue(), v2);
      assertEquals(n.eval(), v2);
      assertEquals(n.evalDiff(n), 1.0, EPSILON);
    }

    public void testVariableNode3() {
      double v1 = 1.0;
      VariableNode n1 = new VariableNode(v1);
      VariableNode n2 = new VariableNode(v1);
      assertEquals(n1.evalDiff(n1), 1.0, EPSILON);
      assertEquals(n2.evalDiff(n2), 1.0, EPSILON);
      assertEquals(n1.evalDiff(n2), 0.0, EPSILON);
      assertEquals(n2.evalDiff(n1), 0.0, EPSILON);
    }

    public void testNegationNode1() {
      double v1 = 1.0;
      double v2 = 2.0;
      Node x = new VariableNode(v1);
      Node y = new NegationNode(x);
      assertEquals(y.eval(), -v1, EPSILON);
      ((VariableNode)x).setValue(v2);
      assertEquals(y.eval(), -v2, EPSILON);
      assertEquals(y.evalDiff((VariableNode)x), -1.0, EPSILON);
    }

    public void testNegationNode2() {
      double v = 0.5;
      VariableNode x1 = new VariableNode(v);
      VariableNode x2 = new VariableNode(v);
      NegationNode y = new NegationNode(x1);
      assertEquals(y.evalDiff(x1), -1.0, EPSILON);
      assertEquals(y.evalDiff(x2), 0.0, EPSILON);
      y.setChild(x2);
      assertEquals(y.evalDiff(x2), -1.0, EPSILON);
      assertEquals(y.evalDiff(x1), 0.0, EPSILON);
    }

    public void testSinNode1() {
      double th1 = Math.PI / 6;
      double th2 = -Math.PI / 3;
      Node x = new VariableNode(th1);
      Node y = new SinNode(x);
      assertEquals(y.eval(), Math.sin(th1), EPSILON);
      assertEquals(y.evalDiff((VariableNode)x), Math.cos(th1), EPSILON);
      ((VariableNode)x).setValue(th2);
      assertEquals(y.eval(), Math.sin(th2), EPSILON);
      assertEquals(y.evalDiff((VariableNode)x), Math.cos(th2), EPSILON);
    }

    public void testRelu() {
      VariableNode x = new VariableNode(1.0);
      Node v = new ReLuNode(x);
      assertEquals(v.eval(), x.eval(), EPSILON);
      assertEquals(v.evalDiff(x), 1.0, EPSILON);
      x.setValue(0.0);
      assertEquals(v.eval(), x.eval(), EPSILON);
      assertEquals(v.evalDiff(x), 1.0, EPSILON);
      x.setValue(-1.0);
      assertEquals(v.eval(), 0.0, EPSILON);
      assertEquals(v.evalDiff(x), 0.0, EPSILON);
    }

    public void testSigmoid() {
      VariableNode x = new VariableNode(1.0);
      Node v = new SigmoidNode(x);
      assertEquals(v.evalDiff(x), Math.exp(-x.eval()) / Math.pow(1 + Math.exp(-x.eval()), 2), EPSILON);
      x.setValue(0);
      assertEquals(v.evalDiff(x), Math.exp(-x.eval()) / Math.pow(1 + Math.exp(-x.eval()), 2), EPSILON);
      x.setValue(-1.0);
      assertEquals(v.evalDiff(x), Math.exp(-x.eval()) / Math.pow(1 + Math.exp(-x.eval()), 2), EPSILON);
    }

    public void testPolynomial1() {
      double v = 2;
      double[] as = {1.0, 2.5, 0.5, 3.0};
      Node x = new VariableNode(v);
      Node y = new PolynomialNode(x, as);
      assertEquals(y.eval(), as[0] + v * (as[1] + (v * (as[2] + v * as[3]))), EPSILON);
      assertEquals(y.evalDiff((VariableNode)x), as[1] + 2 * v * as[2] + 3 * v * v * as[3], EPSILON);
    }

    /* f(x1, x2) = x1 + sin(x2 - x1^2) */
    public void testCompositeFunc1() {
      double v0 = 0.0, v1 = 1.0, v2 = 2.0;
      VariableNode x1 = new VariableNode(v0);
      VariableNode x2 = new VariableNode(v1);
      Node w3 = new PolynomialNode(x1, new double[]{0, 0, 1.0});
      Node w4 = new NegationNode(w3);
      Node w5 = new PlusNode(w4, x2);
      Node w6 = new SinNode(w5);
      Node f = new PlusNode(x1, w6);
      assertEquals(f.eval(), v0 + Math.sin(v1), EPSILON);
      assertEquals(f.evalDiff(x1), 1.0, EPSILON);
      assertEquals(f.evalDiff(x2), Math.cos(v1), EPSILON);
      x1.setValue(v1);
      x2.setValue(v2);
      assertEquals(f.eval(), v1 + Math.sin(v2 - v1 * v1), EPSILON);
      assertEquals(f.evalDiff(x1), 1.0 - 2 * v1 * Math.cos(v2 - v1 * v1), EPSILON);
      assertEquals(f.evalDiff(x2), Math.cos(v2 - v1 * v1), EPSILON);
    }

    public void testVecSumNode1() {
      List<Double> A = new ArrayList<>();
      List<Node> xs = new ArrayList<>();
      int n = 1000;
      for (int i = 0; i < n; i++) {
        A.add((double)i);
        xs.add(new VariableNode(i));
      }
      Node f = new VectorSumNode(xs);
      assertEquals(f.eval(), A.stream().mapToDouble(v -> v).sum(), EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(0)), 1.0, EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(1)), 1.0, EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(10)), 1.0, EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(100)), 1.0, EPSILON);
      ((VectorSumNode)f).setCoeffs(A);
      assertEquals(f.eval(), A.stream().mapToDouble(v -> v * v).sum(), EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(0)), A.get(0), EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(5)), A.get(5), EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(25)), A.get(25), EPSILON);
      assertEquals(f.evalDiff((VariableNode)xs.get(999)), A.get(999), EPSILON);
    }

    public void testLSE1() {
      VariableNode x1 = new VariableNode(2.0);
      VariableNode x2 = new VariableNode(-1.0);
      Node y = new LeastSquareNode(x1, x2);
      assertEquals(y.eval(), 4.5, EPSILON);
      assertEquals(y.evalDiff(x1), 3.0, EPSILON);
      assertEquals(y.evalDiff(x2), -3.0, EPSILON);
    }

    public void testLSE2() {
      List<Node> x1 = new ArrayList<>();
      List<Node> x2 = new ArrayList<>();
      x1.add(new VariableNode(1.0));
      x1.add(new VariableNode(-2.0));
      x2.add(new VariableNode(-1.0));
      x2.add(new VariableNode(2.0));
      Node y = new VectorLeastSquareNode(x1, x2);
      assertEquals(y.eval(), 10.0, EPSILON);
      assertEquals(y.evalDiff(x1.get(0)), 2.0, EPSILON);
      assertEquals(y.evalDiff(x1.get(1)), -4.0, EPSILON);
      assertEquals(y.evalDiff(x2.get(0)), -2.0, EPSILON);
      assertEquals(y.evalDiff(x2.get(1)), 4.0, EPSILON);
    }

    /*
     * VecSumNode + SigmoidNode
     */
    public void testLogistic1() {
      List<VariableNode> x = new ArrayList<>();
      x.add(new VariableNode(-1.0)); x.add(new VariableNode(0.0)); x.add(new VariableNode(1.0));
      List<VariableNode> w = new ArrayList<>();
      w.add(new VariableNode(-2.0)); w.add(new VariableNode(-1.0)); w.add(new VariableNode(1.5));
      List<Node> m = new ArrayList<>();
      for (int i = 0; i < x.size(); i++) {
        m.add(new MultNode(x.get(i), w.get(i)));
      }
      SigmoidNode f = new SigmoidNode(new VectorSumNode(m));
      double df = Math.exp(-3.5) / Math.pow(1 + Math.exp(-3.5), 2);
      assertEquals(f.evalDiff(w.get(0)), df * -1.0, EPSILON);
      assertEquals(f.evalDiff(w.get(1)), df * 0.0, EPSILON);
      assertEquals(f.evalDiff(w.get(2)), df * 1.0, EPSILON);
    }
}
