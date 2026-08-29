package com.ecommerce.model.dto.request.cart;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MoveToWishlistRequest {

    private Long cartItemId;

    private Long wishlistId;
}
