import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Main {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.393701 / 12.0);

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
        System.out.println(new Quantity(1.0, LengthUnit.YARD)
                .equals(new Quantity(3.0, LengthUnit.FEET)));

        System.out.println(new Quantity(1.0, LengthUnit.CM)
                .equals(new Quantity(0.393701, LengthUnit.INCH)));
    }

    public static class QuantityTest {

        @Test
        void testYardToFeet() {
            assertTrue(new Quantity(1.0, LengthUnit.YARD)
                    .equals(new Quantity(3.0, LengthUnit.FEET)));
        }

        @Test
        void testYardToInch() {
            assertTrue(new Quantity(1.0, LengthUnit.YARD)
                    .equals(new Quantity(36.0, LengthUnit.INCH)));
        }

        @Test
        void testCmToInch() {
            assertTrue(new Quantity(1.0, LengthUnit.CM)
                    .equals(new Quantity(0.393701, LengthUnit.INCH)));
        }

        @Test
        void testDifferentValues() {
            assertFalse(new Quantity(1.0, LengthUnit.YARD)
                    .equals(new Quantity(2.0, LengthUnit.FEET)));
        }

        @Test
        void testSameReference() {
            Quantity q = new Quantity(2.0, LengthUnit.YARD);
            assertTrue(q.equals(q));
        }

        @Test
        void testNullComparison() {
            assertFalse(new Quantity(1.0, LengthUnit.CM).equals(null));
        }
    }
}