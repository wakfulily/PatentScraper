package tests;

public class Test {

	public static class AssertionException extends Exception {

		public AssertionException(String description) {
			super(description);
		}

	}

	public void run() throws Exception {
	}

	protected void assertCondition(boolean condition, String errorMessage) throws AssertionException {
		if (!condition) {
			throw new AssertionException(errorMessage);
		}
	}

}
