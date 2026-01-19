package com.mohammadbesharat.atlasmeat.checkout.dto;

import com.mohammadbesharat.atlasmeat.checkout.domain.CheckoutStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateCheckoutStatusRerquest(
    @NotNull
    Long checkoutId,
    CheckoutStatus status
) {}
