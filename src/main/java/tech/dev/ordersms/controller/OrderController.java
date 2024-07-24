package tech.dev.ordersms.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.dev.ordersms.controller.dto.ApiResponse;
import tech.dev.ordersms.controller.dto.OrderResponse;
import tech.dev.ordersms.controller.dto.PaginationResponse;
import tech.dev.ordersms.service.OrderService;

import java.util.Map;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOnOrders = orderService.findTotalOrdersByCustomerId(customerId);

        return ResponseEntity.ok(
                new ApiResponse<>(Map.of("totalOrders", totalOnOrders),
                        pageResponse.getContent(),
                        PaginationResponse.fromPage(pageResponse))
        );
    }
}
