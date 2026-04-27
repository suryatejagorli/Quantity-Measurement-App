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
            double result = this.unit.fromFeet(sumFeet);
            return new Quantity(result, this.unit);
        }

        public static Quantity add(Quantity a, Quantity b, LengthUnit target) {
            if (a == null || b == null || target == null) {
                throw new IllegalArgumentException();
            }
            double sumFeet = a.toBase() + b.toBase();
            double result = target.fromFeet(sumFeet);
            return new Quantity(result, target);
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
        Quantity a = new Quantity(1.0, LengthUnit.FEET);
        Quantity b = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(Quantity.add(a, b, LengthUnit.FEET));
        System.out.println(Quantity.add(a, b, LengthUnit.INCH));
        System.out.println(Quantity.add(a, b, LengthUnit.YARD));
    }

    public static class QuantityTest {

        @Test
        void testTargetFeet() {
            assertEquals(new Quantity(2.0, LengthUnit.FEET),
                    Quantity.add(new Quantity(1.0, LengthUnit.FEET),
                            new Quantity(12.0, LengthUnit.INCH),
                            LengthUnit.FEET));
        }

        @Test
        void testTargetInch() {
            assertEquals(new Quantity(24.0, LengthUnit.INCH),
                    Quantity.add(new Quantity(1.0, LengthUnit.FEET),
                            new Quantity(12.0, LengthUnit.INCH),
                            LengthUnit.INCH));
        }

        @Test
        void testTargetYard() {
            assertEquals(new Quantity(0.6666666667, LengthUnit.YARD),
                    Quantity.add(new Quantity(1.0, LengthUnit.FEET),
                            new Quantity(12.0, LengthUnit.INCH),
                            LengthUnit.YARD));
        }

        @Test
        void testTargetCm() {
            assertEquals(new Quantity(5.08, LengthUnit.CM),
                    Quantity.add(new Quantity(2.54, LengthUnit.CM),
                            new Quantity(1.0, LengthUnit.INCH),
                            LengthUnit.CM));
        }

        @Test
        void testNullTarget() {
            assertThrows(IllegalArgumentException.class, () ->
                    Quantity.add(new Quantity(1.0, LengthUnit.FEET),
                            new Quantity(1.0, LengthUnit.FEET),
                            null));
        }
    }
}