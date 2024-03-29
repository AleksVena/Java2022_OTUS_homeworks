package kz.alseco.crm.service;

import kz.alseco.cachehw.HwCache;
import kz.alseco.cachehw.MyCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kz.alseco.core.repository.DataTemplate;
import kz.alseco.crm.model.Client;
import kz.alseco.core.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.valueOf;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cacheList = new MyCache<>();

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                //log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            //log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client cachedClient = cacheList.get(valueOf(id));
        if (Objects.nonNull(cachedClient)) {
            return Optional.of(cachedClient);
        }

        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            //log.info("client: {}", clientOptional);

            clientOptional.ifPresent(c -> cacheList.put(Long.toString(id), c));

            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}

