package com.humga.moneytransferservice.service;

import com.humga.moneytransferservice.exceptions.*;
import com.humga.moneytransferservice.model.*;
import com.humga.moneytransferservice.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OperationsService {

    TransactionLog transactionLog;
    AcquiringService acquiringService;

    public OperationsService(TransactionLog transactionLog, AcquiringService acquiringService) {
        this.transactionLog = transactionLog;
        this.acquiringService = acquiringService;
    }

    /**
     * Выполняет перевод денег
     *
     * @param reqDTO - DTO клиентского запроса на перевод
     * @return - DTO успешно выполненной операции (в случае успешного перевода)
     */
    public Response200DTO transfer(TransferRequestDTO reqDTO) {
        //создаем из dto объект запроса в IPSP сервис
        TransactionAuthorizationRequest authReq = new TransactionAuthorizationRequest(
                reqDTO.getCardFromNumber(), reqDTO.getCardToNumber(), reqDTO.getCardFromCVV(),
                reqDTO.getCardFromValidTill(), BigDecimal.valueOf(reqDTO.getAmount().getValue()),
                reqDTO.getAmount().getCurrency());

        //создаем транзакцию для сохранения в лог транзакций, пока без таймстемпа, id и статуса
        Transaction transaction = new Transaction(reqDTO.getCardFromNumber(), reqDTO.getCardToNumber(),
                reqDTO.getAmount().getValue(), reqDTO.getAmount().getCurrency());

        //запрашиваем IPSP провести транзакцию
        AuthorizationStatus authStatus = acquiringService.authorizeTransaction(authReq);

        //разбираем результат возвращенный сервисом IPSP:
        switch (authStatus) {
            case UNAUTHORIZED: {
                transactionLog.put(transaction.setFail());
                throw new UnauthorizedException("Отказ. Карта/карты не авторизованы.");
            }
            case INSUFFICIENT: {
                transactionLog.put(transaction.setFail());
                throw new UnauthorizedException("Отказ. Недостаточно средств.");
            }
            case ERROR: {
                transactionLog.put(transaction.setFail());
                throw new RuntimeException("Ошибка банковского сервиса.");
            }
            case AUTHORIZED: {
                transactionLog.put(transaction.setSuccess());
                return new Response200DTO(Long.toString(transaction.getId()), null);
            }
            default: throw new RuntimeException("Неизвестная ошибка сервиса.");
        }
    }

    /**
     * Выполняет проверку присланного клиентов кода верифкации
     *
     * @param reqDTO - DTO запроса клиента на верификацию
     * @return DTO успешно выполненной операции (в случае успешного перевода)
     */
    public Response200DTO confirmOperation(ConfirmOperationRequestDTO reqDTO) {
        //проверяем результат верификации проверочного кода
        if (!acquiringService.verifyConfirmationCode(reqDTO.getOperationId(), reqDTO.getCode())) {
            throw new UnauthorizedException("Неверный проверочный код.");
        }

        /* Предоставленный в условии задания FRONT, никак не отображает полученный проверочный код,
         * для выполнения требований спецификации OpenApi нашего сервиса, просто возвращаем,
         * тот который получили в реквесте. */
        return new Response200DTO(reqDTO.getOperationId(), reqDTO.getCode());
    }
}
