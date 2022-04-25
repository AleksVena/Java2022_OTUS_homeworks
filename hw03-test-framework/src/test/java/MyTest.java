import annotations.After;
import annotations.Before;
import annotations.Test;
import org.assertj.core.api.Assertions;

public class MyTest {

    @Before
    void beforeEach() {
        System.out.println(this.getClass().getName() + "::" + this.hashCode() + "::beforeEach");
    }

    @Test
    void test1() {
        System.out.println(this.getClass().getName() + "::" + this.hashCode() + "::test1");
        Assertions.assertThat(2).isEqualTo(2);
    }

    @Test
    void test2() {
        System.out.println(this.getClass().getName() + "::" + this.hashCode() + "::test2");
        Assertions.assertThat("test1").isEqualTo("test2");
    }

    @Test
    void test3() {
        System.out.println(this.getClass().getName() + "::" + this.hashCode() + "::test3");
        Assertions.assertThat("test3").isEqualTo("test3");
    }

    @After
    void afterEach() {
        System.out.println(this.getClass().getName() + "::" + this.hashCode() + "::afterEach");
    }
}
