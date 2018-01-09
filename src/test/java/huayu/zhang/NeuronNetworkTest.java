package huayu.zhang;

import huayu.zhang.chain.Node;
import huayu.zhang.chain.VariableNode;
import huayu.zhang.chain.NegationNode;
import huayu.zhang.chain.SinNode;
import huayu.zhang.chain.PolynomialNode;

import huayu.zhang.chain.PlusNode;
import huayu.zhang.chain.VectorSumNode;
import huayu.zhang.ml.model.Neuron;
import huayu.zhang.ml.model.Neuron.ActivationFuncType;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class NeuronNetworkTest extends TestCase
{
  private final static double EPSILON = 1e-8;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NeuronNetworkTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( NeuronNetworkTest.class );
    }

    public void testNeuron1() {
      List<Node> x = new ArrayList<>();
      x.add(new VariableNode(-1.0)); x.add(new VariableNode(0.0)); x.add(new VariableNode(1.0));
      List<Double> w0 = new ArrayList<>();
      w0.add(-2.0); w0.add(-1.0); w0.add(1.5);
      Neuron neuron = new Neuron(x, w0, ActivationFuncType.SIGMOID);
      double df = Math.exp(-3.5) / Math.pow(1 + Math.exp(-3.5), 2);
      assertEquals(neuron.weightDiff(0), -1.0 * df, EPSILON);
      assertEquals(neuron.weightDiff(1), 0.0 * df, EPSILON);
      assertEquals(neuron.weightDiff(2), 1.0 * df, EPSILON);
    }
}
