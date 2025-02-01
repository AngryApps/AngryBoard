package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.mappers.ColumnMapper;
import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Column;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/columns")
public class ColumnResource {

    private CardRepository cardRepository;
    private ColumnRepository columnRepository;
    private ColumnMapper columnMapper;

    @Autowired
    public ColumnResource(CardRepository cardRepository, ColumnRepository columnRepository, ColumnMapper columnMapper) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
        this.columnMapper = columnMapper;
    }

    @PostMapping
    ColumnVM createColumn(@Valid @RequestBody ColumnVM columnVM) {
        Column column = columnMapper.mapToColumn(columnVM);

        Column savedColumn = columnRepository.save(column);
        return columnMapper.mapToColumnVM(savedColumn);
    }

    @PostMapping(value = "/update")
    ColumnVM updateColumn(@Valid @RequestBody ColumnVM columnVM) {
        Column column = columnMapper.mapToColumn(columnVM);
        Column savedColumn = columnRepository.save(column);
        return columnMapper.mapToColumnVM(savedColumn);
    }

    @GetMapping
    List<ColumnVM> getAllColumns() {
        List<Column> columns = columnRepository.findAll();
        return columns.stream().map(columnMapper::mapToColumnVM).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    ColumnVM getColumnById(@PathVariable UUID id) {
        Column column = columnRepository.findById(id).orElseThrow(() -> new RuntimeException("Column by id not found"));
        return columnMapper.mapToColumnVM(column);
    }
}
