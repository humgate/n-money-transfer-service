package com.humga.moneytransferservice.service;

import com.humga.moneytransferservice.exceptions.*;
import com.humga.moneytransferservice.model.*;
import com.humga.moneytransferservice.repository.*;
import org.springframework.stereotype.Service;

import static com.humga.moneytransferservice.utils.Utils.formatCardNumber;

@Service
public class AcquiringService {

    TransactionLog transactionLog;

    public AcquiringService(TransactionLog transactionLog) {
        this.transactionLog = transactionLog;
    }

    public Response200DTO transfer(TransferRequestDTO reqDTO) {

        Transaction transaction = new Transaction(reqDTO.getCardFromNumber(), reqDTO.getCardToNumber(),
                reqDTO.getAmount().getValue(), reqDTO.getAmount().getCurrency());

        authorizeTransaction(reqDTO);

        transaction.markDone();

        transactionLog.put(transaction);

        return new Response200DTO(Long.toString(transaction.getId()), null);
    }

    /**
     * Заглушка!
     * Имитирует авторизацию карты-источника в эквайринговом API. Все карты с CVV кодом 111 авторизуются.
     * Имитирует авторизацию номера карты-источника приемника в эквайринговом API. Все карты с номером,
     * больше 2222 авторизуются.
     * Имитирует авторизацию суммы. Все переводы более чем на 5000 не авторизуются
     *
     * @param reqDto - DTO трансфера полученный из запроса
     */
    private void authorizeTransaction(TransferRequestDTO reqDto) {

        if (!reqDto.getCardFromCVV().equals("111")) throw new UnauthorizedException(
                "Авторизация карты не удалась: "+ formatCardNumber(reqDto.getCardFromNumber()));

        if (reqDto.getCardToNumber() < 2222_0000_0000_0000L) throw new UnauthorizedException(
                "Авторизация карты не удалась: " + formatCardNumber(reqDto.getCardToNumber()));

        if (reqDto.getAmount().getValue() > 5000) throw new UnauthorizedException(
                "Авторизация карты не удалась. Недостаточно средств: " + formatCardNumber(reqDto.getCardFromNumber()));
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
