package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardResource {

    private CardRepository cardRepository;
    private ColumnRepository columnRepository;

    @Autowired
    public CardResource(CardRepository cardRepository, ColumnRepository columnRepository) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
    }

}
