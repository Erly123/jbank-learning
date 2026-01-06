package ch.l.jbank.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Dados necessários para realizar uma transferência entre contas")
public record TransferMoneyDto(
        @Schema(description = "ID da carteira de origem", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull UUID sender,

        @Schema(description = "Valor da transferência (mínimo 0.01)", example = "150.00")
        @NotNull @DecimalMin("0.01") BigDecimal value,

        @Schema(description = "ID da carteira de destino", example = "ad9f8836-984e-4f36-836b-67e4e08287d3")
        @NotNull UUID receiver) {

}
