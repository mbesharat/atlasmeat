package com.mohammadbesharat.atlasmeat.checkout.dto;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;

public record UpdateCheckoutStatusRerquest(
    Long checkoutId,
    CheckoutStatus status
) {}
