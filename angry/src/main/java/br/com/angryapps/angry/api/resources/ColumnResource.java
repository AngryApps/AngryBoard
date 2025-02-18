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
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public SingleDataResponse<ColumnVM> upsertColumn(@Valid @RequestBody ColumnVM columnVM) {
        var firstPosition = new AtomicInteger(columnVM.getPosition());

        List<Column> columns;
        if (columnVM.getId() != null) {
            columns = columnRepository.findByPositionGreaterThanEqualAndIdNotOrderByPositionAsc(columnVM.getPosition(), columnVM.getId());
        } else {
            columns = columnRepository.findByPositionGreaterThanEqualOrderByPositionAsc(columnVM.getPosition());
        }

        columns.forEach(c -> c.setPosition(firstPosition.incrementAndGet()));

        Column newColumn = columnMapper.mapToColumn(columnVM);
        columnRepository.save(newColumn);

        // I reorder all the columns that are greater than the new column
        columnRepository.saveAll(columns);

        return ApiResponses.single("Column created", columnMapper.mapToColumnVM(newColumn));
    }

    @GetMapping
    public ListDataResponse<ColumnVM> getAllColumns() {
        List<ColumnVM> columns = columnRepository.findAll(Sort.by(Sort.Direction.ASC, "position"))
                                                 .stream()
                                                 .map(columnMapper::mapToColumnVM)
                                                 .toList();

        return ApiResponses.list(columns);
    }

    @GetMapping(path = "/{id}")
    public SingleDataResponse<ColumnVM> getColumnById(@PathVariable UUID id) {
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
