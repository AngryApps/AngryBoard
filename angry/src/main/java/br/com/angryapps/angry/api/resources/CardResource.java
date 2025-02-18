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
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public SingleDataResponse<CardVM> upsertCard(@Valid @RequestBody CardVM cardVM) {
        Column column = columnRepository.findById(cardVM.getColumnId())
                                        .orElseThrow(() -> new NotFoundResponseException("Column not found"));

        var firstPosition = new AtomicInteger(cardVM.getPosition());

        List<Card> cards;
        if (cardVM.getId() != null) {
            cards = cardRepository.findByColumnIdAndPositionGreaterThanEqualAndIdNotOrderByPositionAsc(cardVM.getColumnId(), cardVM.getPosition(), cardVM.getId());
        } else {
            cards = cardRepository.findByColumnIdAndPositionGreaterThanEqualOrderByPositionAsc(cardVM.getColumnId(), cardVM.getPosition());
        }

        cards.forEach(c -> c.setPosition(firstPosition.incrementAndGet()));

        Card newCard = cardMapper.mapToCard(cardVM, column);
        cardRepository.save(newCard);

        cardRepository.saveAll(cards);

        return ApiResponses.single("Card created", cardMapper.mapToCardVM(newCard));
    }

    @GetMapping
    public ListDataResponse<CardVM> getAllCards() {
        List<CardVM> cards = cardRepository.findAll(Sort.by(Sort.Direction.ASC, "position"))
                                           .stream()
                                           .map(cardMapper::mapToCardVM)
                                           .toList();

        return ApiResponses.list(cards);
    }

    @GetMapping(path = "/{id}")
    public SingleDataResponse<CardVM> getCardById(@PathVariable("id") UUID id) {
        Optional<Card> cardById = cardRepository.findById(id);
        Card card = cardById.orElseThrow(() -> new NotFoundResponseException("Card not found"));

        CardVM cardVM = cardMapper.mapToCardVM(card);
        return ApiResponses.single(cardVM);
    }

    @DeleteMapping(path = "/{id}")
    public BaseResponse deleteCardById(@PathVariable("id") UUID id) {
        cardRepository.deleteById(id);

        return ApiResponses.ok("Card deleted successfully");
    }
}
