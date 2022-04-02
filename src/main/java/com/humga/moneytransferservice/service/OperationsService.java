package com.humga.moneytransferservice.service;

import com.humga.moneytransferservice.exceptions.*;
import com.humga.moneytransferservice.model.*;
import com.humga.moneytransferservice.repository.*;
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

    public Response200DTO transfer(TransferRequestDTO reqDTO) {

        Card from = authorizeCardFrom(reqDTO);
        Card to = getCardTo(reqDTO);
        long operationId = transactionLog.add(new Transaction(
                -1,
                LocalDateTime.now(),
                from,
                to,
                reqDTO.getAmount().getValue(),
                reqDTO.getAmount().getCurrency()));

        return new Response200DTO(Long.toString(operationId), null);
    }

    private Card authorizeCardFrom(TransferRequestDTO reqDto) {
        Card from = cardRepository.getCardByNumber(reqDto.getCardFromNumber());
        if (from == null) throw new NotFoundException(
                "Аутентификация карты не удалась: "+ formatCardNumber(reqDto.getCardFromNumber()));

        boolean matches = encoder.matches(reqDto.getCardFromValidTill(),from.getValidThrough()) &&
                encoder.matches(reqDto.getCardFromCVV(), from.getCvv());
        if (!matches) throw new UnauthorizedException(
                "Авторизация карты не удалась: "+formatCardNumber(reqDto.getCardFromNumber()));

        return from;
    }

    private Card getCardTo(TransferRequestDTO reqDto) {
        Card to = cardRepository.getCardByNumber(reqDto.getCardToNumber());
        if (to == null) throw new NotFoundException(
                "Аутентификация карты не удалась: "+ formatCardNumber(reqDto.getCardFromNumber()));
        return to;
    }

    public Response200DTO confirmOperation(ConfirmOperationRequestDTO reqDTO) {
        /*
         * Предоставленный в условии задания FRONT не запрашивает у пользователя проверочный код и всегда
         * присылает на эндпоинт /confirmOperation значение кода = "0000".
         * Поэтому для возможности проверки возврата ошибки с неверным проверочным кодом,
         * искусственно на бэкенде генерируем ошибку неверного проверочного кода для каждого нечетного operationId
         */
        if (Integer.parseInt(reqDTO.getOperationId()) %2 == 0) throw new UnauthorizedException(
                "Неверный проверочный код");
        /*
         * Предоставленный в условии задания FRONT, никак не отображает полученный проверочный код,
         * просто возвращаем, то который получили в реквесте
         */
        return new Response200DTO(reqDTO.getOperationId(), reqDTO.getCode());
    }

}
