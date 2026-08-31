package com.ecommerce.service.impl;

import com.ecommerce.exception.*;
import com.ecommerce.model.entity.*;
import com.ecommerce.model.enums.InventoryStatus;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.model.dto.request.inventory.*;
import com.ecommerce.model.dto.response.inventory.*;
import com.ecommerce.model.dto.response.PaginatedResponse;
import com.ecommerce.repository.*;
import com.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    @Override
    @Transactional
    public InventoryItemResponse createInventoryItem(InventoryItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", request.getWarehouseId()));

        InventoryItem item = InventoryItem.builder()
                .product(product)
                .warehouse(warehouse)
                .quantity(request.getQuantity())
                .reservedQuantity(0)
                .reorderPoint(request.getReorderPoint() != null ? request.getReorderPoint() : 0)
                .reorderQuantity(request.getReorderQuantity() != null ? request.getReorderQuantity() : 0)
                .lowStockThreshold(request.getLowStockThreshold() != null ? request.getLowStockThreshold() : 5)
                .binLocation(request.getBinLocation())
                .batchNumber(request.getBatchNumber())
                .lotNumber(request.getLotNumber())
                .serialNumber(request.getSerialNumber())
                .expiresAt(request.getExpiresAt())
                .manufacturedAt(request.getManufacturedAt())
                .build();

        if (request.getVariantId() != null) {
            ProductVariant variant = variantRepository.findById(request.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Variant", "id", request.getVariantId()));
            item.setVariant(variant);
        }

        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", request.getSupplierId()));
            item.setSupplier(supplier);
        }

        item.setAvailableQuantity(item.getQuantity() - item.getReservedQuantity());

        InventoryItem saved = inventoryItemRepository.save(item);
        log.info("Inventory item created for product {} at warehouse {}", product.getId(), warehouse.getId());
        return mapToInventoryItemResponse(saved);
    }

    @Override
    @Transactional
    public InventoryItemResponse updateInventoryItem(Long id, InventoryItemRequest request) {
        InventoryItem item = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "id", id));

        if (request.getQuantity() != null) item.setQuantity(request.getQuantity());
        if (request.getReorderPoint() != null) item.setReorderPoint(request.getReorderPoint());
        if (request.getReorderQuantity() != null) item.setReorderQuantity(request.getReorderQuantity());
        if (request.getLowStockThreshold() != null) item.setLowStockThreshold(request.getLowStockThreshold());
        if (request.getBinLocation() != null) item.setBinLocation(request.getBinLocation());

        item.setAvailableQuantity(item.getQuantity() - item.getReservedQuantity());
        updateItemStatus(item);

        InventoryItem saved = inventoryItemRepository.save(item);
        return mapToInventoryItemResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryItemResponse getInventoryItemById(Long id) {
        InventoryItem item = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "id", id));
        return mapToInventoryItemResponse(item);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<InventoryItemResponse> getAllInventoryItems(Pageable pageable) {
        Page<InventoryItem> items = inventoryItemRepository.findAll(pageable);
        return PaginatedResponse.of(
                items.getContent().stream().map(this::mapToInventoryItemResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), items.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemResponse> getProductInventory(Long productId) {
        return inventoryItemRepository.findByProductId(productId).stream()
                .map(this::mapToInventoryItemResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void adjustStock(Long productId, Long warehouseId, int adjustment, String reason) {
        InventoryItem item = inventoryItemRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "product+warehouse"));

        int previousQuantity = item.getQuantity();
        item.setQuantity(item.getQuantity() + adjustment);
        item.setAvailableQuantity(item.getQuantity() - item.getReservedQuantity());
        updateItemStatus(item);
        inventoryItemRepository.save(item);

        InventoryTransaction transaction = InventoryTransaction.builder()
                .inventoryItem(item)
                .type("ADJUSTMENT")
                .quantity(adjustment)
                .previousQuantity(previousQuantity)
                .newQuantity(item.getQuantity())
                .referenceType("MANUAL")
                .notes(reason)
                .build();
        inventoryTransactionRepository.save(transaction);

        log.info("Stock adjusted for product {}: {} -> {} ({})", productId, previousQuantity, item.getQuantity(), reason);
    }

    @Override
    @Transactional
    public void reserveStock(Long productId, Long warehouseId, int quantity) {
        InventoryItem item = inventoryItemRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "product+warehouse"));

        if (item.getAvailableQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product " + productId);
        }

        item.setReservedQuantity(item.getReservedQuantity() + quantity);
        item.setAvailableQuantity(item.getQuantity() - item.getReservedQuantity());
        inventoryItemRepository.save(item);
    }

    @Override
    @Transactional
    public void releaseStock(Long productId, Long warehouseId, int quantity) {
        InventoryItem item = inventoryItemRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "product+warehouse"));

        item.setReservedQuantity(Math.max(0, item.getReservedQuantity() - quantity));
        item.setAvailableQuantity(item.getQuantity() - item.getReservedQuantity());
        inventoryItemRepository.save(item);
    }

    @Override
    @Transactional
    public void transferStock(Long productId, Long fromWarehouseId, Long toWarehouseId, int quantity) {
        InventoryItem fromItem = inventoryItemRepository.findByProductIdAndWarehouseId(productId, fromWarehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "source"));
        InventoryItem toItem = inventoryItemRepository.findByProductIdAndWarehouseId(productId, toWarehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem", "destination"));

        if (fromItem.getAvailableQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock at source warehouse");
        }

        fromItem.setQuantity(fromItem.getQuantity() - quantity);
        fromItem.setAvailableQuantity(fromItem.getQuantity() - fromItem.getReservedQuantity());
        toItem.setQuantity(toItem.getQuantity() + quantity);
        toItem.setAvailableQuantity(toItem.getQuantity() - toItem.getReservedQuantity());

        inventoryItemRepository.save(fromItem);
        inventoryItemRepository.save(toItem);

        InventoryTransaction tx1 = InventoryTransaction.builder()
                .inventoryItem(fromItem).type("TRANSFER_OUT").quantity(-quantity)
                .previousQuantity(fromItem.getQuantity() + quantity).newQuantity(fromItem.getQuantity())
                .referenceType("TRANSFER").notes("Transfer to warehouse " + toWarehouseId).build();
        InventoryTransaction tx2 = InventoryTransaction.builder()
                .inventoryItem(toItem).type("TRANSFER_IN").quantity(quantity)
                .previousQuantity(toItem.getQuantity() - quantity).newQuantity(toItem.getQuantity())
                .referenceType("TRANSFER").notes("Transfer from warehouse " + fromWarehouseId).build();
        inventoryTransactionRepository.save(tx1);
        inventoryTransactionRepository.save(tx2);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemResponse> getLowStockItems() {
        return inventoryItemRepository.findLowStockItems().stream()
                .map(this::mapToInventoryItemResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemResponse> getOutOfStockItems() {
        return inventoryItemRepository.findOutOfStockItems().stream()
                .map(this::mapToInventoryItemResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", request.getSupplierId()));

        PurchaseOrder po = PurchaseOrder.builder()
                .supplier(supplier)
                .subtotal(request.getSubtotal())
                .total(request.getTotal())
                .shippingCost(request.getShippingCost())
                .tax(request.getTax())
                .discount(request.getDiscount())
                .status("DRAFT")
                .paymentStatus("UNPAID")
                .expectedDeliveryDate(request.getExpectedDeliveryDate())
                .paymentTerms(request.getPaymentTerms())
                .notes(request.getNotes())
                .build();

        PurchaseOrder saved = purchaseOrderRepository.save(po);
        log.info("Purchase order created: {} for supplier {}", saved.getId(), supplier.getName());
        return mapToPurchaseOrderResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderResponse getPurchaseOrderById(Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", "id", id));
        return mapToPurchaseOrderResponse(po);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse updatePurchaseOrderStatus(Long id, String status) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", "id", id));
        po.setStatus(status);
        if ("RECEIVED".equals(status)) {
            po.setActualDeliveryDate(LocalDateTime.now());
        }
        PurchaseOrder saved = purchaseOrderRepository.save(po);
        return mapToPurchaseOrderResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse<PurchaseOrderResponse> getAllPurchaseOrders(Pageable pageable) {
        Page<PurchaseOrder> orders = purchaseOrderRepository.findAll(pageable);
        return PaginatedResponse.of(
                orders.getContent().stream().map(this::mapToPurchaseOrderResponse).collect(Collectors.toList()),
                pageable.getPageNumber(), pageable.getPageSize(), orders.getTotalElements()
        );
    }

    @Override
    @Transactional
    public WarehouseResponse createWarehouse(String name, String code, String address, String city, String state, String country, String postalCode, boolean isDefault, boolean isActive) {
        Warehouse warehouse = Warehouse.builder()
                .name(name).code(code).address(address).city(city).state(state)
                .country(country).postalCode(postalCode).isDefault(isDefault).isActive(isActive)
                .build();
        Warehouse saved = warehouseRepository.save(warehouse);
        return mapToWarehouseResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll().stream().map(this::mapToWarehouseResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SupplierResponse createSupplier(String name, String contactPerson, String email, String phone, String address, String city, String country) {
        Supplier supplier = Supplier.builder()
                .name(name).contactPerson(contactPerson).email(email).phone(phone)
                .address(address).city(city).country(country).isActive(true)
                .build();
        Supplier saved = supplierRepository.save(supplier);
        return mapToSupplierResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream().map(this::mapToSupplierResponse).collect(Collectors.toList());
    }

    private void updateItemStatus(InventoryItem item) {
        if (item.getQuantity() == 0) {
            item.setStatus(InventoryStatus.OUT_OF_STOCK);
        } else if (item.getQuantity() <= item.getLowStockThreshold()) {
            item.setStatus(InventoryStatus.LOW_STOCK);
        } else {
            item.setStatus(InventoryStatus.IN_STOCK);
        }
        item.setInStock(item.getQuantity() > 0);
    }

    private InventoryItemResponse mapToInventoryItemResponse(InventoryItem item) {
        return InventoryItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .variantId(item.getVariant() != null ? item.getVariant().getId() : null)
                .warehouseId(item.getWarehouse().getId())
                .warehouseName(item.getWarehouse().getName())
                .quantity(item.getQuantity())
                .reservedQuantity(item.getReservedQuantity())
                .availableQuantity(item.getAvailableQuantity())
                .status(item.getStatus() != null ? item.getStatus().name() : null)
                .inStock(item.isInStock())
                .reorderPoint(item.getReorderPoint())
                .reorderQuantity(item.getReorderQuantity())
                .lowStockThreshold(item.getLowStockThreshold())
                .binLocation(item.getBinLocation())
                .batchNumber(item.getBatchNumber())
                .lotNumber(item.getLotNumber())
                .serialNumber(item.getSerialNumber())
                .expiresAt(item.getExpiresAt())
                .createdAt(item.getCreatedAt())
                .build();
    }

    private PurchaseOrderResponse mapToPurchaseOrderResponse(PurchaseOrder po) {
        return PurchaseOrderResponse.builder()
                .id(po.getId())
                .supplierId(po.getSupplier().getId())
                .supplierName(po.getSupplier().getName())
                .subtotal(po.getSubtotal())
                .total(po.getTotal())
                .shippingCost(po.getShippingCost())
                .tax(po.getTax())
                .discount(po.getDiscount())
                .status(po.getStatus())
                .paymentStatus(po.getPaymentStatus())
                .expectedDeliveryDate(po.getExpectedDeliveryDate())
                .actualDeliveryDate(po.getActualDeliveryDate())
                .paymentTerms(po.getPaymentTerms())
                .notes(po.getNotes())
                .createdAt(po.getCreatedAt())
                .build();
    }

    private WarehouseResponse mapToWarehouseResponse(Warehouse wh) {
        return WarehouseResponse.builder()
                .id(wh.getId())
                .name(wh.getName())
                .code(wh.getCode())
                .address(wh.getAddress())
                .city(wh.getCity())
                .state(wh.getState())
                .country(wh.getCountry())
                .postalCode(wh.getPostalCode())
                .isDefault(wh.isDefault())
                .isActive(wh.isActive())
                .build();
    }

    private SupplierResponse mapToSupplierResponse(Supplier s) {
        return SupplierResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .contactPerson(s.getContactPerson())
                .email(s.getEmail())
                .phone(s.getPhone())
                .address(s.getAddress())
                .city(s.getCity())
                .country(s.getCountry())
                .isActive(s.isActive())
                .build();
    }
}
