package com.redhat.ldemasi.example;

import com.redhat.ldemasi.example.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Routes extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(Routes.class);


    @Override
    public void configure() throws Exception {
        // Select example
        from("timer:select?period=10s")
                .routeId("getPerson")
                .setHeader("id").constant(1L)
                .to("mybatis:getPerson?statementType=SelectOne&inputHeader=id")
                .process(exchange -> {
                    Person person = exchange.getIn().getBody(Person.class);
                    log.info(">> Article Title "+ person.getName());
                });
        // Insert example
        from("timer:insert?period=10s")
                .routeId("setPerson")
                .process(exchange -> {
                    Person person = new Person();
                    person.setName(UUID.randomUUID().toString());
                    person.setId(simple("${random(100000)}").evaluate(exchange,Integer.class).longValue());
                    person.setAge(simple("${random(100)}").evaluate(exchange,Integer.class).intValue());
                    exchange.getIn().setHeader("person", person);
                })
                .to("mybatis:setPerson?statementType=Insert&inputHeader=person")
                .to("log:insertLog?showBody=true");
        // Update Example
        from("timer:update?repeatCount=1")
                .routeId("updatePerson")
                .process(exchange -> {
                    Person person = new Person();
                    person.setName("Admin");
                    person.setId(3L);
                    person.setAge(simple("${random(100)}").evaluate(exchange,Integer.class).intValue());
                    exchange.getIn().setHeader("person", person);
                })
                .to("mybatis:updatePerson?statementType=Update&inputHeader=person")
                .to("log:updateLog?showBody=true");

        // Consumer example

        from("mybatis:getAllPeople?delay=10000")
                .routeId("getAllPeople")
                .process(exchange -> {
                    logger.info(">> Incoming "+ exchange.getIn().getBody(Person.class).getName());
                });
    }
}
