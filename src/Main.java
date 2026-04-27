import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Main {

    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    static boolean compareFeet(double a, double b) {
        return new Feet(a).equals(new Feet(b));
    }

    static boolean compareInches(double a, double b) {
        return new Inches(a).equals(new Inches(b));
    }

    public static void main(String[] args) {
        System.out.println(compareFeet(1.0, 1.0));
        System.out.println(compareInches(1.0, 1.0));
    }

    public static class QuantityMeasurementAppTest {

        @Test
        void testFeet_SameValue() {
            assertTrue(compareFeet(1.0, 1.0));
        }

        @Test
        void testFeet_DifferentValue() {
            assertFalse(compareFeet(1.0, 2.0));
        }

        @Test
        void testInches_SameValue() {
            assertTrue(compareInches(1.0, 1.0));
        }

        @Test
        void testInches_DifferentValue() {
            assertFalse(compareInches(1.0, 2.0));
        }

        @Test
        void testNullComparison() {
            Feet f = new Feet(1.0);
            assertFalse(f.equals(null));
        }

        @Test
        void testSameReference() {
            Inches i = new Inches(1.0);
            assertTrue(i.equals(i));
        }

        @Test
        void testDifferentType() {
            Feet f = new Feet(1.0);
            assertFalse(f.equals("1.0"));
        }
    }
}