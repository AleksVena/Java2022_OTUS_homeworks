package executors;

public class TestsResult {
    private final int allTestCount;
    private int successTests = 0;
    private int failTests = 0;

    public TestsResult(int allTestCount) {
        this.allTestCount = allTestCount;
    }

    public int getAllTestCount() {
        return allTestCount;
    }

    public int getSuccessTests() {
        return successTests;
    }

    public int getFailTests() {
        return failTests;
    }

    public void incrementSuccess() {
        successTests++;
    }

    public void incrementFail() {
        failTests++;
    }
}
