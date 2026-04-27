import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(1.0 / 30.48);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    public double toBase(double value) {
        return value * toFeet;
    }

    public double fromBase(double baseValue) {
        return baseValue / toFeet;
    }
}

public class Main {

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
            return unit.toBase(value);
        }

        public Quantity convertTo(LengthUnit target) {
            if (target == null) throw new IllegalArgumentException();
            double base = toBase();
            return new Quantity(target.fromBase(base), target);
        }

        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException();
            double sum = this.toBase() + other.toBase();
            return new Quantity(this.unit.fromBase(sum), this.unit);
        }

        public static Quantity add(Quantity a, Quantity b, LengthUnit target) {
            if (a == null || b == null || target == null) {
                throw new IllegalArgumentException();
            }
            double sum = a.toBase() + b.toBase();
            return new Quantity(target.fromBase(sum), target);
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

        System.out.println(q1.convertTo(LengthUnit.INCH));
        System.out.println(Quantity.add(q1, q2, LengthUnit.YARD));
        System.out.println(q1.equals(q2));
    }

    public static class QuantityTest {

        @Test
        void testConvert() {
            assertEquals(new Quantity(12.0, LengthUnit.INCH),
                    new Quantity(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCH));
        }

        @Test
        void testAddTarget() {
            assertEquals(new Quantity(2.0, LengthUnit.FEET),
                    Quantity.add(new Quantity(1.0, LengthUnit.FEET),
                            new Quantity(12.0, LengthUnit.INCH),
                            LengthUnit.FEET));
        }

        @Test
        void testEquality() {
            assertTrue(new Quantity(36.0, LengthUnit.INCH)
                    .equals(new Quantity(1.0, LengthUnit.YARD)));
        }
    }
}