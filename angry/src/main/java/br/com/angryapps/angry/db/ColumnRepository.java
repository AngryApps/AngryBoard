package br.com.angryapps.angry.db;

import br.com.angryapps.angry.models.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface ColumnRepository extends PagingAndSortingRepository<Column, UUID> {

    Column save(Column column);

    Optional<Column> findById(UUID id);

    void deleteById(UUID id);

    Iterable<Column> findAll();

    Page<Column> findAll(Pageable pageable);

    Iterable<Column> findAll(Sort sort);
}
