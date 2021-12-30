package bip.test.jpa_subselect.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Created by ramezani on 4/22/2020.
 */
public interface MyQueryByExampleExecutor<T> {
    Optional<T> findOne(Example<T> var1);

    List<T> findAll(Example<T> var1);

    List<T> findAll(Example<T> var1, Sort var2);

    Page<T> findAll(Example<T> var1, Pageable var2);

    long count(Example<T> var1);

    boolean exists(Example<T> var1);
}
