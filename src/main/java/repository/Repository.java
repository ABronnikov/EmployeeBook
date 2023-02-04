package repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Repository<K, V> {

    void init();

    V save(V v);

    boolean update(K k, V v);

    boolean delete(K k);

    Optional<V> findById(K k);

    List<V> findAll();

    List<V> findByName(String name);

    List<V> findByCreatedDateInterval(LocalDateTime start, LocalDateTime end);

    void close();


}
