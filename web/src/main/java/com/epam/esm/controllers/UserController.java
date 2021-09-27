package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *  The class that represents an API for basic user-related application operations
 *  @author Yauheni Vezhnavets
 **/

@RestController
@RequestMapping ("/users")
public class UserController {

    private static final String JSON = "application/json";
    private static final String USER_ID = "userId";
    private static final String GIFT_CERTIFICATE_ID = "certificateId";
    private static final String ORDER_ID = "orderId";

    private final UserService<User> userService;
    private final OrderService <Order> orderService;

    @Autowired
    public UserController(UserService<User> userService, OrderService<Order> orderService) {

        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Returns an {@link ResponseEntity} object contained {@link HttpStatus} status and a {@link List}
     * list of {@link User} users retrieved from database.
     *
     * @return {@link ResponseEntity} contained both {@link HttpStatus} status and {@link List} of {@link User} users.
     */
    @GetMapping(produces = JSON)
    public ResponseEntity<List<User>> getUsers() {

        List<User> listOfUsers = userService.findUsers();
        listOfUsers.stream().forEach(
                user -> user.add(
                        linkTo(methodOn(UserController.class).getUsers()).withRel("getUsers"),
                        linkTo(methodOn(UserController.class).getUser(user.getUserId())).withSelfRel()
                )
        );

        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }

    /**
     * Returns an {@link ResponseEntity} object contained {@link HttpStatus} status and a {@link User} object
     * from database.
     * @param id - id of {@link User} that has to be retrieved from database.
     * @return {@link ResponseEntity} contained both {@link HttpStatus} status and an {@link User} object.
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */

    @GetMapping(value = "/{userId}", produces = JSON)
    public ResponseEntity<User> getUser(@PathVariable(USER_ID) long id) throws ResourceNotFoundException {
        User user = userService.findUser(id);
        user.add(
                linkTo(methodOn(UserController.class).getUsers()).withRel("getUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel()
        );
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping(value = "/{userId}/orders", produces = JSON)
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable(USER_ID) long id) throws ResourceNotFoundException {
        List <OrderDto> orders = orderService.findOrders(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @GetMapping(value ="/{userId}/orders/{certificateId}", produces = JSON)
    public ResponseEntity <OrderDto> getOrder( @PathVariable (USER_ID) long userId,
                                    @PathVariable (GIFT_CERTIFICATE_ID) long giftCertificateId){
        OrderDto orderDto = orderService.findOrder(userId,giftCertificateId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }


    @PostMapping(value ="/{userId}/orders/{certificateId}", produces = JSON)
    public ResponseEntity createOrder( @PathVariable (USER_ID) long userId,
                                       @PathVariable (GIFT_CERTIFICATE_ID) long giftCertificateId){
        orderService.createOrder(userId,giftCertificateId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
