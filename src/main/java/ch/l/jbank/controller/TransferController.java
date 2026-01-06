package ch.l.jbank.controller;

import ch.l.jbank.controller.dto.TransferMoneyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.l.jbank.service.TransferService;

@RestController
@RequestMapping(path = "/transfers")
@Tag(
        name = "Transfers",
        description = "Operações de movimentação de fundos entre contas bancárias."
)
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @Operation(
            summary = "Realizar transferência entre carteiras",
            description = "Transfere um valor de uma carteira de origem para uma carteira de destino. " +
                    "A operação é atômica (ACID) e verifica se a conta de origem possui saldo suficiente antes de processar."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou saldo insuficiente"),
            @ApiResponse(responseCode = "404", description = "Uma ou ambas as carteiras não foram encontradas"),
            @ApiResponse(responseCode = "422", description = "Erro de concorrência (conflito de versão)")
    })
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferMoneyDto dto) {
        transferService.transferMoney(dto);

        return ResponseEntity.ok().build();
    }
}
