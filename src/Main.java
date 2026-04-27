import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(1.0 / 30.48);

    private final double toFeet;

    LengthUnit(double toFeet) { this.toFeet = toFeet; }

    public double toBase(double value) { return value * toFeet; }

    public double fromBase(double base) { return base / toFeet; }
}

enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKg;

    WeightUnit(double toKg) { this.toKg = toKg; }

    public double toBase(double value) { return value * toKg; }

    public double fromBase(double base) { return base / toKg; }
}

public class Main {

    static class Length {
        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            if (!Double.isFinite(value) || unit == null) throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        private double toBase() { return unit.toBase(value); }

        public Length convertTo(LengthUnit target) {
            return new Length(target.fromBase(toBase()), target);
        }

        public Length add(Length other, LengthUnit target) {
            double sum = this.toBase() + other.toBase();
            return new Length(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Length l = (Length) o;
            return Double.compare(this.toBase(), l.toBase()) == 0;
        }
    }

    static class Weight {
        private final double value;
        private final WeightUnit unit;

        public Weight(double value, WeightUnit unit) {
            if (!Double.isFinite(value) || unit == null) throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        private double toBase() { return unit.toBase(value); }

        public Weight convertTo(WeightUnit target) {
            return new Weight(target.fromBase(toBase()), target);
        }

        public Weight add(Weight other, WeightUnit target) {
            double sum = this.toBase() + other.toBase();
            return new Weight(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Weight w = (Weight) o;
            return Double.compare(this.toBase(), w.toBase()) == 0;
        }
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);
        System.out.println(l1.equals(l2));

        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);
        System.out.println(w1.equals(w2));

        System.out.println(w1.convertTo(WeightUnit.POUND));
        System.out.println(w1.add(w2, WeightUnit.KILOGRAM));
    }

    public static class TestCases {

        @Test
        void testWeightEquality() {
            assertTrue(new Weight(1.0, WeightUnit.KILOGRAM)
                    .equals(new Weight(1000.0, WeightUnit.GRAM)));
        }

        @Test
        void testWeightConversion() {
            assertEquals(2.20462,
                    new Weight(1.0, WeightUnit.KILOGRAM)
                            .convertTo(WeightUnit.POUND).value,
                    1e-5);
        }

        @Test
        void testWeightAddition() {
            assertEquals(new Weight(2.0, WeightUnit.KILOGRAM),
                    new Weight(1.0, WeightUnit.KILOGRAM)
                            .add(new Weight(1000.0, WeightUnit.GRAM),
                                    WeightUnit.KILOGRAM));
        }

        @Test
        void testCategorySeparation() {
            assertFalse(new Weight(1.0, WeightUnit.KILOGRAM)
                    .equals(new Length(1.0, LengthUnit.FEET)));
        }
    }
}