package br.com.angryapps.angry.db;

import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID>, PagingAndSortingRepository<Card, UUID> {

    Card save(Card card);

    Optional<Card> findById(UUID id);

    void deleteById(UUID id);

    Iterable<Card> findAll();

    Page<Card> findAll(Pageable pageable);

    Iterable<Card> findAll(Sort sort);

    Iterable<Card> findByColumnId(UUID columnId);

    Iterable<Card> findByColumnIdAndColumnPositionGreaterThan(Column columnId, int columnPosition);

    Iterable<Card> findByColumnIdAndColumnPositionLessThan(UUID columnId, int columnPosition);

    Iterable<Card> findByColumnIdAndColumnPositionBetween(UUID columnId, int columnPosition1, int columnPosition2);
}
