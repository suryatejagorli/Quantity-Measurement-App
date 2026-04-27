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

    public static void main(String[] args) {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println(f1.equals(f2));
    }

    public static class QuantityMeasurementAppTest {

        @Test
        void testEquality_SameValue() {
            Feet f1 = new Feet(1.0);
            Feet f2 = new Feet(1.0);
            assertTrue(f1.equals(f2));
        }

        @Test
        void testEquality_DifferentValue() {
            Feet f1 = new Feet(1.0);
            Feet f2 = new Feet(2.0);
            assertFalse(f1.equals(f2));
        }

        @Test
        void testEquality_NullComparison() {
            Feet f1 = new Feet(1.0);
            assertFalse(f1.equals(null));
        }

        @Test
        void testEquality_SameReference() {
            Feet f1 = new Feet(1.0);
            assertTrue(f1.equals(f1));
        }

        @Test
        void testEquality_DifferentType() {
            Feet f1 = new Feet(1.0);
            assertFalse(f1.equals("1.0"));
        }
    }
}