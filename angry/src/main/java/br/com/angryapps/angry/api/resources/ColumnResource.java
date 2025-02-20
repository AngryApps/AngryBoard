package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.mappers.ColumnMapper;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;
import br.com.angryapps.angry.api.responses.ListDataResponse;
import br.com.angryapps.angry.api.responses.SingleDataResponse;
import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.api.vm.requests.PatchColumn;
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

    private ColumnMapper columnMapper;
    private ColumnRepository columnRepository;

    @Autowired
    public ColumnResource(ColumnRepository columnRepository, ColumnMapper columnMapper) {
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

        // Add it to the list to save together with all the others
        columns.add(newColumn);
        columnRepository.saveAll(columns);

        return ApiResponses.single("Column created", columnMapper.mapToColumnVM(newColumn));
    }

    @PatchMapping("{id}")
    public SingleDataResponse<ColumnVM> patchColumn(@PathVariable UUID id, @Valid @RequestBody PatchColumn patchColumn) {
        Column column = columnRepository.findById(id)
                                        .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        var firstPosition = new AtomicInteger(patchColumn.getPosition());

        List<Column> columns = columnRepository.findByPositionGreaterThanEqualAndIdNotOrderByPositionAsc(patchColumn.getPosition(), id);

        columns.forEach(c -> c.setPosition(firstPosition.incrementAndGet()));

        columnMapper.patchColumn(patchColumn, column);

        // Add it to the list to save together with all the others
        columns.add(column);
        columnRepository.saveAll(columns);

        // Remove the cards from the column to return a smaller json
        column.setCards(null);

        return ApiResponses.single("Column patched", columnMapper.mapToColumnVM(column));
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
