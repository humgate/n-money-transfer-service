package com.humga.moneytransferservice.controller;

import com.humga.moneytransferservice.model.*;

import com.humga.moneytransferservice.service.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin //CORS on: фронт, загруженный из одного источника сможет обращаться к приложению, запущенному на другом
public class OperationsController {
    OperationsService service;

    public OperationsController(OperationsService service) {
        this.service = service;
    }

    @PostMapping(value = "/transfer")
        public Response200Dto transfer(@RequestBody TransferRequestDto reqDto) {
        //return new Response200Dto("12345", null);
        return service.transfer(reqDto);
    }

    @PostMapping(value = "/confirmOperation")
    public Response200Dto confirmOperation(@RequestBody ConfirmOperationRequestDto reqDto) {
        return new Response200Dto(reqDto.getOperationId(),"12345code");
    }
}
