package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.exceptions.NotFoundResponseException;
import br.com.angryapps.angry.api.mappers.CardMapper;
import br.com.angryapps.angry.api.responses.ApiResponses;
import br.com.angryapps.angry.api.responses.BaseResponse;
import br.com.angryapps.angry.api.responses.ListDataResponse;
import br.com.angryapps.angry.api.responses.SingleDataResponse;
import br.com.angryapps.angry.api.vm.CardVM;
import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
public class CardResource {

    private CardRepository cardRepository;
    private ColumnRepository columnRepository;
    private CardMapper cardMapper;

    @Autowired
    public CardResource(CardRepository cardRepository, ColumnRepository columnRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
        this.cardMapper = cardMapper;
    }

    @PostMapping
    CardVM createCard(@Valid @RequestBody CardVM cardVM) {
        Column column = columnRepository.findById(cardVM.getColumnId())
                .orElseThrow(() -> new RuntimeException("Column not found"));

        Card card = cardMapper.mapToCard(cardVM, column);
        Card savedCard = cardRepository.save(card);

        return cardMapper.mapToCardVM(savedCard);
    }

    @PutMapping
    SingleDataResponse<CardVM> updateCard(@Valid @RequestBody CardVM cardVM) {

        Optional<Column> columnById = columnRepository.findById(cardVM.getColumnId());
        Column column = columnById.orElseThrow(() -> new NotFoundResponseException("ColumnId not found"));

        Card card = cardMapper.mapToCard(cardVM, column);
        Card savedCard = cardRepository.save(card);

        CardVM savedCardVM = cardMapper.mapToCardVM(savedCard);

        return ApiResponses.single(savedCardVM);

    }

    @GetMapping
    ListDataResponse<CardVM> getAllCards() {
        List<CardVM> cards = cardRepository.findAll()
                .stream()
                .map(cardMapper::mapToCardVM)
                .toList();

        return ApiResponses.list(cards);
    }

    @GetMapping(path = "/{id}")
    SingleDataResponse<CardVM> getCardById(@PathVariable("id") UUID id) {
        Optional<Card> cardById = cardRepository.findById(id);
        Card card = cardById.orElseThrow(() -> new NotFoundResponseException("Card not found"));

        CardVM cardVM = cardMapper.mapToCardVM(card);
        return ApiResponses.single(cardVM);
    }

    @DeleteMapping(path = "/{id}")
    BaseResponse deleteCardById(@PathVariable("id") UUID id) {
        cardRepository.deleteById(id);

        return ApiResponses.ok("Card deleted successfully");
    }
}
