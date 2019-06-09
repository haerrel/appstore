package base.appstore.exception;

public class ProblemNotFoundException extends RuntimeException {
    public ProblemNotFoundException(Long id) {
        super("Could not find problem " + id);
    }
}
