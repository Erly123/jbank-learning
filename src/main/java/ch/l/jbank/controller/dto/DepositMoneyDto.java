package ch.l.jbank.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Dados para depósito em conta")
public record DepositMoneyDto(
        @Schema(description = "Valor a ser depositado (mínimo 10.00)", example = "100.00")
        @NotNull @DecimalMin("10.00") BigDecimal value) {
}
