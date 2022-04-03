package com.humga.moneytransferservice.controller;

import com.humga.moneytransferservice.exceptions.NotFoundException;
import com.humga.moneytransferservice.exceptions.UnauthorizedException;
import com.humga.moneytransferservice.model.*;

import com.humga.moneytransferservice.service.AcquiringService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin //CORS on: фронт, загруженный из одного источника сможет обращаться к приложению, запущенному на другом
public class OperationsController {
    AcquiringService service;

    public OperationsController(AcquiringService service) {
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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    ResponseErrDTO handleNotFound(NotFoundException e) {
        return new ResponseErrDTO(e.getMessage(), 2);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    ResponseErrDTO handleInternalServerError(RuntimeException e) {
        return new ResponseErrDTO("Внутренняя ошибка сервера", 3);
    }
}
