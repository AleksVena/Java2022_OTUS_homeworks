package kz.alseco.homework;

import kz.alseco.base.AbstractHibernateTest;
import kz.alseco.crm.model.Address;
import kz.alseco.crm.model.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import kz.alseco.crm.model.Client;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class HomeworkTest extends AbstractHibernateTest {

    @Test
    @DisplayName("must be faster")
    void shouldBeFasterWithCache() {

        List<Long> ids = new ArrayList<>();
        int count = 2000;
        for (int i = 0; i < count; i++) {
            var client = new Client(null, "Тимофей_" + i, new Address(null, "г. Нурсултан, проспект Назарбаев" + i),
                    List.of(new Phone(null, "88002000600" + i)));
            ids.add(dbServiceClient.saveClient(client).getId());
        }

        var startWC = System.currentTimeMillis();
        for (var id : ids) {
            dbServiceClient.getClient(id);
        }
        var endWC = System.currentTimeMillis();
        var withoutCacheTime = endWC - startWC;
        System.out.println("without cache " + withoutCacheTime);

        var startC = System.currentTimeMillis();
        for (var id : ids) {
            dbServiceClient.getClient(id);
        }
        var endC = System.currentTimeMillis();
        var withCacheTime = endC - startC;
        System.out.println("with cache " + withCacheTime);

        assertThat(withoutCacheTime).isGreaterThan(withCacheTime);
    }

}