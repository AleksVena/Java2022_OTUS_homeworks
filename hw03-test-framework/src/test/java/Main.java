import executors.TestExecutor;

public class Main {

    public static void main(String[] args) throws Exception {
        TestExecutor testExecutor = new TestExecutor(MyTest.class.getName());
        testExecutor.execute();
    }
}
