package com.humga.moneytransferservice.service;

import com.humga.moneytransferservice.model.Card;
import com.humga.moneytransferservice.model.Response200Dto;
import com.humga.moneytransferservice.model.Transaction;
import com.humga.moneytransferservice.model.TransferRequestDto;
import com.humga.moneytransferservice.repository.CardRepository;
import com.humga.moneytransferservice.repository.TransactionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.humga.moneytransferservice.utils.Utils.formatCardNumber;

@Service
public class OperationsService {

    CardRepository cardRepository;
    TransactionLog transactionLog;
    PasswordEncoder encoder;

    public OperationsService(CardRepository cardRepository, TransactionLog transactionLog, PasswordEncoder encoder) {
        this.cardRepository = cardRepository;
        this.transactionLog = transactionLog;
        this.encoder = encoder;
    }

    public Response200Dto transfer(TransferRequestDto reqDTO) {

        Card from = authorizeCardFrom(reqDTO);
        Card to = getCardTo(reqDTO);
        long operationId = transactionLog.add(new Transaction(
                -1,
                LocalDateTime.now(),
                from,
                to,
                reqDTO.getAmount().getValue(),
                reqDTO.getAmount().getCurrency()));

        return new Response200Dto(Long.toString(operationId), null);
    }

    private Card authorizeCardFrom(TransferRequestDto reqDto) {
        Card from = cardRepository.getCardByNumber(reqDto.getCardFromNumber());
        if (from == null) throw new RuntimeException(
                "Аутентификация карты не удалась: "+ formatCardNumber(reqDto.getCardFromNumber()));

        boolean matches = encoder.matches(reqDto.getCardFromValidTill(),from.getValidThrough()) &&
                encoder.matches(reqDto.getCardFromCVV(), from.getCvv());
        if (!matches) throw new AccessDeniedException("Авторизация карты не удалась");

        return from;
    }

    private Card getCardTo(TransferRequestDto reqDto) {
        Card to = cardRepository.getCardByNumber(reqDto.getCardToNumber());
        if (to == null) throw new RuntimeException(
                "Аутентификация карты не удалась: "+ formatCardNumber(reqDto.getCardFromNumber()));
        return to;
    }

}
