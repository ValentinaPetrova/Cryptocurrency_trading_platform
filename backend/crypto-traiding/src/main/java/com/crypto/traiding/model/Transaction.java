package com.crypto.traiding.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Transaction(
    Long id,
    Long userId,
    String symbol,
    String type,
    BigDecimal quantity,
    BigDecimal price,
    BigDecimal totalValue,
    Instant timestamp
) {}
