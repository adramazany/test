package bip.test.ws.swagger.controller;

import bip.test.ws.swagger.model.RequiredPropertyModel;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by ramezani on 3/15/2020.
 */
@RestController()
@RequestMapping("/api/test")
public class WSSwaggerController {

    @GetMapping("/hello")
    public String hello(){
        System.out.println("testpath=");
        return "Hello World!";
    }

    @PostMapping("/testRequiredParameter")
    public String testRequiredParameter(@RequestParam(required = true) String requiredParam){
        System.out.println("requiredParam is :"+requiredParam);
        return "requiredParam is :"+requiredParam;
    }

    @PostMapping("/testRequiredProperty")
    public String testRequiredProperty(@Valid @RequestBody() RequiredPropertyModel requiredPropertyModel){
        System.out.println("requiredPropertyModel={id:"+requiredPropertyModel.getId()+",requiredField:"+requiredPropertyModel.getRequiredField()+"}");
        return "requiredPropertyModel={id:"+requiredPropertyModel.getId()+",requiredField:"+requiredPropertyModel.getRequiredField()+"}";
    }

}
