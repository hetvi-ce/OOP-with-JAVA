import java.io.Serializable;

public class TrainPass implements Serializable {
    private static final long serialVersionUID = 105L;

    private final String passId;
    private final String passengerName;
    private final String route;
    private final double fare;
    private transient String otpPin;

    public TrainPass(String passId, String passengerName, String route, double fare, String otpPin) {
        this.passId = passId;
        this.passengerName = passengerName;
        this.route = route;
        this.fare = fare;
        this.otpPin = otpPin;
    }

    @Override
    public String toString() {
        return "TrainPass[id=" + passId + ", passenger=" + passengerName
                + ", route=" + route + ", fare=" + fare + ", transientOtpPin=" + otpPin + "]";
    }
}
