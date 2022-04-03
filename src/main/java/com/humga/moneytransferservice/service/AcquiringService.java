package com.humga.moneytransferservice.service;


import com.humga.moneytransferservice.exceptions.UnauthorizedException;
import com.humga.moneytransferservice.model.AuthorizationStatus;
import com.humga.moneytransferservice.model.TransactionAuthorizationRequest;
import org.springframework.stereotype.Service;


@Service
public class AcquiringService {
    /**
     * Заглушка.
     * Имитирует сервис IPSP провайдера по исполнению перевода денег.
     * Имитирует валидацию cvv кода карты. Все карты кроме карт с cvv = 999 авторизуются.
     * Имитирует авторизацию номера карты отправителя и получателя в эквайринговом API. Все карты кроме
     * карт с номером 9999-9999-9999-9999 авторизуются.
     * Имитирует авторизацию суммы. Все переводы на сумму не более 999999 авторизуются
     * Имитирует ошибку сервиса IPSP провайдера при номере карты получателя = 9999-9999-9999-8888
     *
     * @param req - результат операции
     */
    public AuthorizationStatus authorizeTransaction(TransactionAuthorizationRequest req) {

        if (req.getCardFromNumber() == 9999_9999_9999_9999L || req.getCardToNumber() == 9999_9999_9999_9999L) {
            return AuthorizationStatus.UNAUTHORIZED;
        }

        if (req.getCardFromNumber() == 999) return AuthorizationStatus.UNAUTHORIZED;

        if (req.getAmount().longValue() > 999999) return AuthorizationStatus.INSUFFICIENT;

        if (req.getCardToNumber() == 9999_9999_9999_8888L) {
            return AuthorizationStatus.ERROR;
        }

        return AuthorizationStatus.AUTHORIZED;
    }

    /**
     * Заглушка.
     * Имитирует верификацию сервисом IPSP проверочного кода.
     * Предоставленный в условии задания FRONT не запрашивает у пользователя проверочный код и всегда
     * присылает значение кода = "0000". Поэтому для возможности проверки возврата ошибки с неверным
     * проверочным кодом, генерируем ошибку неверного проверочного кода для каждого нечетного operationId
     *
     * @param operationId id операции,
     * @param code code операци
     * @return true если код верный, иначе false
     */
    public boolean verifyConfirmationCode (String operationId, String code) {
        return Integer.parseInt(operationId) % 2 != 0;
    }
}
