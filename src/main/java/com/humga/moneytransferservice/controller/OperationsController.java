package com.humga.moneytransferservice.controller;

import com.humga.moneytransferservice.dto.ConfirmOperationRequestDTO;
import com.humga.moneytransferservice.dto.Response200DTO;
import com.humga.moneytransferservice.dto.ResponseErrDTO;
import com.humga.moneytransferservice.dto.TransferRequestDTO;
import com.humga.moneytransferservice.exceptions.UnauthorizedException;

import com.humga.moneytransferservice.service.OperationsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin //CORS on: фронт, загруженный из одного источника сможет обращаться к приложению, запущенному на другом
public class OperationsController {
    private final OperationsService service;

    public OperationsController(OperationsService service) {
        this.service = service;
    }

    @PostMapping(value = "/transfer")
        public Response200DTO transfer(@RequestBody TransferRequestDTO reqDto) {
        return service.transfer(reqDto);
    }

    @PostMapping(value = "/confirmOperation")
    public Response200DTO confirmOperation(@RequestBody ConfirmOperationRequestDTO reqDto) {
        return service.confirmOperation(reqDto);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnauthorizedException.class)
    ResponseErrDTO handleUnauthorized(UnauthorizedException e) {
        return new ResponseErrDTO(e.getMessage(), 1);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    ResponseErrDTO handleInternalServerError(RuntimeException e) {
        return new ResponseErrDTO("Внутренняя ошибка сервера", 2);
    }
}
