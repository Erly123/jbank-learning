package ch.l.jbank.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Dados para criação de uma nova carteira")
public record CreateWalletDto(
        @Schema(description = "CPF válido do titular", example = "123.456.789-00")
        @CPF @NotBlank String cpf,

        @Schema(description = "E-mail único do usuário", example = "cliente@email.com")
        @Email @NotBlank String email,

        @Schema(description = "Nome completo do titular", example = "Erly")
        @NotBlank String name) {

}
