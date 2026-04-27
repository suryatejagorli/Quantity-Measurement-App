import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Main {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        double toFeet(double value) {
            return value * toFeet;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quantity other = (Quantity) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }
    }

    public static void main(String[] args) {
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println(q1.equals(q2));
    }

    public static class QuantityTest {

        @Test
        void testFeetToFeet_SameValue() {
            assertTrue(new Quantity(1.0, LengthUnit.FEET)
                    .equals(new Quantity(1.0, LengthUnit.FEET)));
        }

        @Test
        void testInchToInch_SameValue() {
            assertTrue(new Quantity(1.0, LengthUnit.INCH)
                    .equals(new Quantity(1.0, LengthUnit.INCH)));
        }

        @Test
        void testFeetToInch_Equivalent() {
            assertTrue(new Quantity(1.0, LengthUnit.FEET)
                    .equals(new Quantity(12.0, LengthUnit.INCH)));
        }

        @Test
        void testInchToFeet_Equivalent() {
            assertTrue(new Quantity(12.0, LengthUnit.INCH)
                    .equals(new Quantity(1.0, LengthUnit.FEET)));
        }

        @Test
        void testDifferentValues() {
            assertFalse(new Quantity(1.0, LengthUnit.FEET)
                    .equals(new Quantity(2.0, LengthUnit.FEET)));
        }

        @Test
        void testNullComparison() {
            assertFalse(new Quantity(1.0, LengthUnit.FEET).equals(null));
        }

        @Test
        void testSameReference() {
            Quantity q = new Quantity(1.0, LengthUnit.FEET);
            assertTrue(q.equals(q));
        }
    }
}