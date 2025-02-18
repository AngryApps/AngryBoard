package br.com.angryapps.angry.db;

import br.com.angryapps.angry.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    List<Card> findByColumnIdAndPositionGreaterThanEqualAndIdNotOrderByPositionAsc(UUID id, int positionIsGreaterThan, UUID id1);

    List<Card> findByColumnIdAndPositionGreaterThanEqualOrderByPositionAsc(UUID columnId, int position);
}
