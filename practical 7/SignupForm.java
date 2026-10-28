public class SignupForm {
    @NotBlank
    @MaxLength(12)
    private String username;

    @NotBlank
    @MaxLength(25)
    private String email;

    @NotBlank
    @MaxLength(10)
    private String referralCode;

    public SignupForm(String username, String email, String referralCode) {
        this.username = username;
        this.email = email;
        this.referralCode = referralCode;
    }

    @Override
    public String toString() {
        return "SignupForm[username='" + username + "', email='" + email
                + "', referralCode='" + referralCode + "']";
    }
}
