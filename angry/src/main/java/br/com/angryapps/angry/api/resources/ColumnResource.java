package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.mappers.ColumnMapper;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;
import br.com.angryapps.angry.api.responses.ListDataResponse;
import br.com.angryapps.angry.api.responses.SingleDataResponse;
import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Column;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/columns")
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
    SingleDataResponse<ColumnVM> createColumn(@Valid @RequestBody ColumnVM columnVM) {
        Column column = columnMapper.mapToColumn(columnVM);

        Column savedColumn = columnRepository.save(column);
        return ApiResponses.single(columnMapper.mapToColumnVM(savedColumn));
    }

    @PutMapping
    SingleDataResponse<ColumnVM> updateColumn(@Valid @RequestBody ColumnVM columnVM) {
        Column column = columnMapper.mapToColumn(columnVM);
        Column savedColumn = columnRepository.save(column);

        return ApiResponses.single(columnMapper.mapToColumnVM(savedColumn));
    }

    @GetMapping
    ListDataResponse<ColumnVM> getAllColumns() {
        List<ColumnVM> columns = columnRepository.findAll()
                                                 .stream()
                                                 .map(columnMapper::mapToColumnVM)
                                                 .toList();

        return ApiResponses.list(columns);
    }

    @GetMapping(path = "/{id}")
    SingleDataResponse<ColumnVM> getColumnById(@PathVariable UUID id) {
        Optional<Column> byId = columnRepository.findById(id);
        Column column = byId.orElseThrow(() -> new NotFoundResponseException("Column by id not found"));

        ColumnVM columnVM = columnMapper.mapToColumnVM(column);

        return ApiResponses.single(columnVM);
    }

    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteColumn(@PathVariable UUID id) {
        columnRepository.deleteById(id);

        return ApiResponses.ok("Column deleted successfully");
    }
}
