package huayu.zhang;

import huayu.zhang.chain.MinusNode;
import huayu.zhang.chain.Node;
import huayu.zhang.chain.VariableNode;
import huayu.zhang.chain.NegationNode;
import huayu.zhang.chain.SinNode;
import huayu.zhang.chain.PolynomialNode;

import huayu.zhang.chain.PlusNode;
import huayu.zhang.chain.VectorSumNode;
import huayu.zhang.ml.model.Neuron;
import huayu.zhang.ml.model.FullConnLayer;
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
  private final static double EPSILON = 1e-5;
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

    public void testNeuron2() {
      List<Node> x = new ArrayList<>();
      x.add(new VariableNode(-1.0)); x.add(new VariableNode(0.0)); x.add(new VariableNode(1.0));
      List<Double> w0 = new ArrayList<>();
      w0.add(-2.0); w0.add(-1.0); w0.add(1.5); w0.add(0.5);
      Neuron neuron = new Neuron(x, w0, ActivationFuncType.SIGMOID);
      double df = Math.exp(-4.0) / Math.pow(1 + Math.exp(-4.0), 2);
      assertEquals(neuron.weightDiff(0), -1.0 * df, EPSILON);
      assertEquals(neuron.weightDiff(1), 0.0 * df, EPSILON);
      assertEquals(neuron.weightDiff(2), 1.0 * df, EPSILON);
      assertEquals(neuron.weightDiff(3), 1.0 * df, EPSILON);
    }

    public void testTwoFCLayer1() {
      List<Node> inputNodes = new ArrayList<>();
      inputNodes.add(new VariableNode(1.0));
      inputNodes.add(new VariableNode(-1.0));
      List<Double> wA = new ArrayList<>(); wA.add(0.2); wA.add(0.3); wA.add(0.1);
      List<Double> wB = new ArrayList<>(); wB.add(0.5); wB.add(0.6); wB.add(0.4);
      List<Double> wC = new ArrayList<>(); wC.add(0.8); wC.add(0.9); wC.add(0.7);
      Neuron neuronA = new Neuron(inputNodes, wA, ActivationFuncType.RELU);
      Neuron neuronB = new Neuron(inputNodes, wB, ActivationFuncType.RELU);
      List<Node> hiddenNodes = new ArrayList<>();
      hiddenNodes.add(neuronA); hiddenNodes.add(neuronB);
      Neuron neuronC = new Neuron(hiddenNodes, wC, ActivationFuncType.SIGMOID);
      assertEquals(neuronA.eval(), 0.0, EPSILON);
      assertEquals(neuronB.eval(), 0.3, EPSILON);
      assertEquals(neuronC.eval(), 0.72512, EPSILON);
      Node outputNode = new VariableNode(1.0);
      Node errNode = new PolynomialNode(new MinusNode(neuronC, outputNode), new double[]{0, 0, 0.5});
      assertEquals(errNode.evalDiff(neuronA.getWeightNode(0)), -0.04383, EPSILON);
      assertEquals(errNode.evalDiff(neuronA.getWeightNode(1)), 0.04383, EPSILON);
      assertEquals(errNode.evalDiff(neuronA.getWeightNode(2)), -0.04383, EPSILON);
      assertEquals(errNode.evalDiff(neuronB.getWeightNode(0)), -0.04931, EPSILON);
      assertEquals(errNode.evalDiff(neuronB.getWeightNode(1)), 0.04931, EPSILON);
      assertEquals(errNode.evalDiff(neuronB.getWeightNode(2)), -0.04931, EPSILON);
      assertEquals(errNode.evalDiff(neuronC.getWeightNode(0)), 0.0, EPSILON);
      assertEquals(errNode.evalDiff(neuronC.getWeightNode(1)), -0.01644, EPSILON);
      assertEquals(errNode.evalDiff(neuronC.getWeightNode(2)), -0.05479, EPSILON);
    }

    public void testTwoFCLayer2() {
      List<Node> inputNodes = new ArrayList<>();
      inputNodes.add(new VariableNode(1.0));
      inputNodes.add(new VariableNode(-1.0));
      List<Double> wA = new ArrayList<>(); wA.add(0.2); wA.add(0.3); wA.add(0.1);
      List<Double> wB = new ArrayList<>(); wB.add(0.5); wB.add(0.6); wB.add(0.4);
      List<Double> wC = new ArrayList<>(); wC.add(0.8); wC.add(0.9); wC.add(0.7);
      FullConnLayer layer1 = new FullConnLayer(inputNodes, 2, 0.0, ActivationFuncType.RELU, true);
      FullConnLayer layer2 = new FullConnLayer(layer1.getOutputNodes(), 1, 0.0, ActivationFuncType.SIGMOID, true); 
      layer1.setWeights(0, wA);
      layer1.setWeights(1, wB);
      layer2.setWeights(0, wC);
      assertEquals(layer1.getOutputNodes().get(0).eval(), 0.0, EPSILON);
      assertEquals(layer1.getOutputNodes().get(1).eval(), 0.3, EPSILON);
      assertEquals(layer2.getOutputNodes().get(0).eval(), 0.72512, EPSILON);
      Node outputNode = new VariableNode(1.0);
      Node errNode = new PolynomialNode(new MinusNode(layer2.getOutputNodes().get(0), outputNode), new double[]{0, 0, 0.5});
      Neuron neuronA = (Neuron)layer1.getOutputNodes().get(0);
      Neuron neuronB = (Neuron)layer1.getOutputNodes().get(1);
      Neuron neuronC = (Neuron)layer2.getOutputNodes().get(0);
      assertEquals(errNode.evalDiff(neuronA.getWeightNode(0)), -0.04383, EPSILON);
      assertEquals(errNode.evalDiff(neuronA.getWeightNode(1)), 0.04383, EPSILON);
      assertEquals(errNode.evalDiff(neuronA.getWeightNode(2)), -0.04383, EPSILON);
      assertEquals(errNode.evalDiff(neuronB.getWeightNode(0)), -0.04931, EPSILON);
      assertEquals(errNode.evalDiff(neuronB.getWeightNode(1)), 0.04931, EPSILON);
      assertEquals(errNode.evalDiff(neuronB.getWeightNode(2)), -0.04931, EPSILON);
      assertEquals(errNode.evalDiff(neuronC.getWeightNode(0)), 0.0, EPSILON);
      assertEquals(errNode.evalDiff(neuronC.getWeightNode(1)), -0.01644, EPSILON);
      assertEquals(errNode.evalDiff(neuronC.getWeightNode(2)), -0.05479, EPSILON);

    }
}
