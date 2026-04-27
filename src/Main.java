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

        double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value) || unit == null) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toFeet(value);
        }

        public double convertTo(LengthUnit target) {
            if (target == null) throw new IllegalArgumentException();
            double base = toBase();
            return target.fromFeet(base);
        }

        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (!Double.isFinite(value) || source == null || target == null) {
                throw new IllegalArgumentException();
            }
            double base = source.toFeet(value);
            return target.fromFeet(base);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quantity other = (Quantity) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {
        System.out.println(Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println(Quantity.convert(3.0, LengthUnit.YARD, LengthUnit.FEET));
        System.out.println(new Quantity(2.54, LengthUnit.CM).convertTo(LengthUnit.INCH));
    }

    public static class QuantityTest {

        @Test
        void testFeetToInch() {
            assertEquals(12.0, Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
        }

        @Test
        void testInchToFeet() {
            assertEquals(2.0, Quantity.convert(24.0, LengthUnit.INCH, LengthUnit.FEET));
        }

        @Test
        void testYardToInch() {
            assertEquals(36.0, Quantity.convert(1.0, LengthUnit.YARD, LengthUnit.INCH));
        }

        @Test
        void testCmToInch() {
            assertEquals(1.0, Quantity.convert(2.54, LengthUnit.CM, LengthUnit.INCH), 1e-6);
        }

        @Test
        void testZero() {
            assertEquals(0.0, Quantity.convert(0.0, LengthUnit.FEET, LengthUnit.INCH));
        }

        @Test
        void testNegative() {
            assertEquals(-12.0, Quantity.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH));
        }

        @Test
        void testRoundTrip() {
            double v = 5.0;
            double result = Quantity.convert(
                    Quantity.convert(v, LengthUnit.FEET, LengthUnit.INCH),
                    LengthUnit.INCH,
                    LengthUnit.FEET
            );
            assertEquals(v, result, 1e-6);
        }

        @Test
        void testInvalid() {
            assertThrows(IllegalArgumentException.class, () ->
                    Quantity.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));
        }
    }
}