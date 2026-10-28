import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingRaceDemo {
    static class CoachReservation {
        private int seatsLeft;
        private final AtomicInteger bookedCount = new AtomicInteger(0);

        public CoachReservation(int initialSeats) {
            this.seatsLeft = initialSeats;
        }

        public void bookUnsafe(String passengerName) {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ignored) {
                }
                seatsLeft--;
                bookedCount.incrementAndGet();
                System.out.println("  [UNSAFE] Seat confirmed for " + passengerName
                        + " (seatsLeft=" + seatsLeft + ")");
            } else {
                System.out.println("  [UNSAFE] No seat left for " + passengerName);
            }
        }

        public synchronized void bookSynchronized(String passengerName) {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                seatsLeft--;
                bookedCount.incrementAndGet();
                System.out.println("  [SYNC]   Seat confirmed for " + passengerName
                        + " (seatsLeft=" + seatsLeft + ")");
            } else {
                System.out.println("  [SYNC]   Booking declined for " + passengerName + " (Coach Full)");
            }
        }

        public int getBookedCount() {
            return bookedCount.get();
        }

        public int getSeatsLeft() {
            return seatsLeft;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int totalThreads = 10;

        System.out.println("=== Phase 1: Unsynchronized Seat Booking (5 seats, 10 threads) ===");
        CoachReservation unsafeCoach = new CoachReservation(5);
        Thread[] groupOne = new Thread[totalThreads];
        for (int i = 0; i < totalThreads; i++) {
            final String passenger = "Passenger-" + (i + 1);
            groupOne[i] = new Thread(() -> unsafeCoach.bookUnsafe(passenger));
            groupOne[i].start();
        }
        for (Thread t : groupOne) {
            t.join();
        }
        System.out.println("Unsafe Run Result -> Total bookings succeeded: "
                + unsafeCoach.getBookedCount() + " | Final seatsLeft: " + unsafeCoach.getSeatsLeft());

        System.out.println("\n=== Phase 2: Synchronized Seat Booking (5 seats, 10 threads) ===");
        CoachReservation safeCoach = new CoachReservation(5);
        Thread[] groupTwo = new Thread[totalThreads];
        for (int i = 0; i < totalThreads; i++) {
            final String passenger = "Passenger-" + (i + 1);
            groupTwo[i] = new Thread(() -> safeCoach.bookSynchronized(passenger));
            groupTwo[i].start();
        }
        for (Thread t : groupTwo) {
            t.join();
        }
        System.out.println("Synchronized Run Result -> Total bookings succeeded: "
                + safeCoach.getBookedCount() + " | Final seatsLeft: " + safeCoach.getSeatsLeft());
    }
}
