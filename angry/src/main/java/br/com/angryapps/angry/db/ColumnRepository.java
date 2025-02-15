package br.com.angryapps.angry.db;

import br.com.angryapps.angry.models.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ColumnRepository extends JpaRepository<Column, UUID> {

    List<Column> findByPositionGreaterThanEqualOrderByPositionAsc(int position);

    List<Column> findByPositionGreaterThanEqualAndIdNotOrderByPositionAsc(int position, UUID id);
}
