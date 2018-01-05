package huayu.zhang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import huayu.zhang.ml.model.LinearRegression;
import huayu.zhang.ml.DataEntry;

/**
 * Unit test for simple App.
 */
public class LinearRegTest extends TestCase
{
  private final static double EPSILON = 1e-8;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LinearRegTest ( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LinearRegTest.class );
    }

    /**
     * y = 1.5 * x1 + 0.5 * x2
     * (1, 1, 2); (0.5, 2, 1.75)
     */
    public void testVariableNode1() {
      LinearRegression lm = new LinearRegression(
          Collections.nCopies(2, 0.0), 0.1, 10);
      List<Double> f1 = new ArrayList<>(); f1.add(1.0); f1.add(1.0);
      List<Double> f2 = new ArrayList<>(); f2.add(0.5); f2.add(2.0);
      List<Double> w = null;
      List<Double> wExpected = new ArrayList<>();
      DataEntry e1 = new DataEntry(f1, 2);
      DataEntry e2 = new DataEntry(f2, 1.75);
      lm.SGDStep(e1);
      w = lm.getWeights();
      wExpected.add(0.2); wExpected.add(0.2);
      assertEquals(w, wExpected);
      // another entry
      lm.setRelaxationFactor(0.5);
      w = new ArrayList<>(); w.add(-0.5); w.add(0.5);
      lm.setWeights(w);
      lm.SGDStep(e2);
      w = lm.getWeights();
      wExpected.set(0, -0.25); wExpected.set(1, 1.5);
      assertEquals(w, wExpected);
    }
}
