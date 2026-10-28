public class SpectrometerSession implements AutoCloseable {
    private final String sessionCode;
    private boolean open;

    public SpectrometerSession(String sessionCode) {
        this.sessionCode = sessionCode;
        this.open = true;
        System.out.println("[Resource Opened] Spectrometer session '" + sessionCode + "' calibrated.");
    }

    public void scanSample(String compoundName, int wavelengthNm) {
        if (!open) {
            throw new IllegalStateException("Session already closed.");
        }
        if (wavelengthNm < 200 || wavelengthNm > 800) {
            throw new IllegalArgumentException(
                    "Wavelength " + wavelengthNm + "nm out of valid optical range (200..800nm) for " + compoundName);
        }
        System.out.println("[Scan Complete] Sample '" + compoundName + "' read at " + wavelengthNm + "nm.");
    }

    @Override
    public void close() {
        open = false;
        System.out.println("[Resource Closed] Spectrometer session '" + sessionCode + "' safely powered down.");
    }
}
