package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.request.inventory.*;
import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.model.dto.response.inventory.*;
import com.ecommerce.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/inventory")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Inventory", description = "Admin inventory management APIs")
public class AdminInventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @Operation(summary = "Create inventory item")
    public ResponseEntity<ApiResponse<InventoryItemResponse>> createInventoryItem(@RequestBody InventoryItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inventory item created", inventoryService.createInventoryItem(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update inventory item")
    public ResponseEntity<ApiResponse<InventoryItemResponse>> updateInventoryItem(
            @PathVariable Long id, @RequestBody InventoryItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Inventory item updated", inventoryService.updateInventoryItem(id, request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory item by ID")
    public ResponseEntity<ApiResponse<InventoryItemResponse>> getInventoryItemById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getInventoryItemById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all inventory items")
    public ResponseEntity<ApiResponse<PaginatedResponse<InventoryItemResponse>>> getAllInventoryItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getAllInventoryItems(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product inventory")
    public ResponseEntity<ApiResponse<List<InventoryItemResponse>>> getProductInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getProductInventory(productId)));
    }

    @PostMapping("/adjust")
    @Operation(summary = "Adjust stock")
    public ResponseEntity<ApiResponse<Void>> adjustStock(@RequestBody Map<String, Object> body) {
        Long productId = ((Number) body.get("productId")).longValue();
        Long warehouseId = ((Number) body.get("warehouseId")).longValue();
        int adjustment = ((Number) body.get("adjustment")).intValue();
        String reason = (String) body.get("reason");
        inventoryService.adjustStock(productId, warehouseId, adjustment, reason);
        return ResponseEntity.ok(ApiResponse.success("Stock adjusted"));
    }

    @PostMapping("/reserve")
    @Operation(summary = "Reserve stock")
    public ResponseEntity<ApiResponse<Void>> reserveStock(@RequestBody Map<String, Object> body) {
        Long productId = ((Number) body.get("productId")).longValue();
        Long warehouseId = ((Number) body.get("warehouseId")).longValue();
        int quantity = ((Number) body.get("quantity")).intValue();
        inventoryService.reserveStock(productId, warehouseId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Stock reserved"));
    }

    @PostMapping("/release")
    @Operation(summary = "Release stock")
    public ResponseEntity<ApiResponse<Void>> releaseStock(@RequestBody Map<String, Object> body) {
        Long productId = ((Number) body.get("productId")).longValue();
        Long warehouseId = ((Number) body.get("warehouseId")).longValue();
        int quantity = ((Number) body.get("quantity")).intValue();
        inventoryService.releaseStock(productId, warehouseId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Stock released"));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer stock between warehouses")
    public ResponseEntity<ApiResponse<Void>> transferStock(@RequestBody Map<String, Object> body) {
        Long productId = ((Number) body.get("productId")).longValue();
        Long fromWarehouseId = ((Number) body.get("fromWarehouseId")).longValue();
        Long toWarehouseId = ((Number) body.get("toWarehouseId")).longValue();
        int quantity = ((Number) body.get("quantity")).intValue();
        inventoryService.transferStock(productId, fromWarehouseId, toWarehouseId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Stock transferred"));
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock items")
    public ResponseEntity<ApiResponse<List<InventoryItemResponse>>> getLowStockItems() {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getLowStockItems()));
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out of stock items")
    public ResponseEntity<ApiResponse<List<InventoryItemResponse>>> getOutOfStockItems() {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getOutOfStockItems()));
    }

    // Purchase Orders
    @PostMapping("/purchase-orders")
    @Operation(summary = "Create purchase order")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Purchase order created", inventoryService.createPurchaseOrder(request)));
    }

    @GetMapping("/purchase-orders/{id}")
    @Operation(summary = "Get purchase order by ID")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> getPurchaseOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getPurchaseOrderById(id)));
    }

    @PutMapping("/purchase-orders/{id}/status")
    @Operation(summary = "Update purchase order status")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updatePurchaseOrderStatus(
            @PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", inventoryService.updatePurchaseOrderStatus(id, status)));
    }

    @GetMapping("/purchase-orders")
    @Operation(summary = "Get all purchase orders")
    public ResponseEntity<ApiResponse<PaginatedResponse<PurchaseOrderResponse>>> getAllPurchaseOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getAllPurchaseOrders(PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    // Warehouses
    @GetMapping("/warehouses")
    @Operation(summary = "Get all warehouses")
    public ResponseEntity<ApiResponse<List<WarehouseResponse>>> getAllWarehouses() {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getAllWarehouses()));
    }

    // Suppliers
    @GetMapping("/suppliers")
    @Operation(summary = "Get all suppliers")
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> getAllSuppliers() {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getAllSuppliers()));
    }
}
