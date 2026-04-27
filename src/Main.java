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

        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException();
            double sumFeet = this.toBase() + other.toBase();
            double resultValue = this.unit.fromFeet(sumFeet);
            return new Quantity(resultValue, this.unit);
        }

        public static Quantity add(Quantity a, Quantity b) {
            if (a == null || b == null) throw new IllegalArgumentException();
            double sumFeet = a.toBase() + b.toBase();
            double resultValue = a.unit.fromFeet(sumFeet);
            return new Quantity(resultValue, a.unit);
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
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println(q1.add(q2));

        System.out.println(new Quantity(1.0, LengthUnit.YARD)
                .add(new Quantity(3.0, LengthUnit.FEET)));
    }

    public static class QuantityTest {

        @Test
        void testFeetPlusFeet() {
            assertEquals(new Quantity(3.0, LengthUnit.FEET),
                    Quantity.add(new Quantity(1.0, LengthUnit.FEET),
                            new Quantity(2.0, LengthUnit.FEET)));
        }

        @Test
        void testFeetPlusInch() {
            assertEquals(new Quantity(2.0, LengthUnit.FEET),
                    new Quantity(1.0, LengthUnit.FEET)
                            .add(new Quantity(12.0, LengthUnit.INCH)));
        }

        @Test
        void testInchPlusFeet() {
            assertEquals(new Quantity(24.0, LengthUnit.INCH),
                    new Quantity(12.0, LengthUnit.INCH)
                            .add(new Quantity(1.0, LengthUnit.FEET)));
        }

        @Test
        void testYardPlusFeet() {
            assertEquals(new Quantity(2.0, LengthUnit.YARD),
                    new Quantity(1.0, LengthUnit.YARD)
                            .add(new Quantity(3.0, LengthUnit.FEET)));
        }

        @Test
        void testCmPlusInch() {
            assertEquals(new Quantity(5.08, LengthUnit.CM),
                    new Quantity(2.54, LengthUnit.CM)
                            .add(new Quantity(1.0, LengthUnit.INCH)));
        }

        @Test
        void testZero() {
            assertEquals(new Quantity(5.0, LengthUnit.FEET),
                    new Quantity(5.0, LengthUnit.FEET)
                            .add(new Quantity(0.0, LengthUnit.INCH)));
        }

        @Test
        void testNegative() {
            assertEquals(new Quantity(3.0, LengthUnit.FEET),
                    new Quantity(5.0, LengthUnit.FEET)
                            .add(new Quantity(-2.0, LengthUnit.FEET)));
        }

        @Test
        void testNull() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Quantity(1.0, LengthUnit.FEET).add(null));
        }
    }
}