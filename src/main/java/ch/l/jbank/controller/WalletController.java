package ch.l.jbank.controller;

import ch.l.jbank.controller.dto.CreateWalletDto;
import ch.l.jbank.controller.dto.DepositMoneyDto;
import ch.l.jbank.controller.dto.StatementDto;
import ch.l.jbank.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/wallets")
@Tag(
        name = "Wallets",
        description = "Operações relacionadas ao gerenciamento de contas, depósitos e extratos bancários."
)
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    @Operation(
            summary = "Criar uma nova carteira",
            description = "Registra uma nova carteira bancária no sistema com CPF, e-mail e nome do titular."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "210", description = "Carteira criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public ResponseEntity<Void> createWallet(@RequestBody @Valid CreateWalletDto dto){

        var wallet = walletService.createWallet(dto);

        return ResponseEntity.created(URI.create("/wallets/" + wallet.getWalletId().toString())).build();
    }

    @DeleteMapping(path = "/{walletId}")
    @Operation(
            summary = "Encerrar uma carteira",
            description = "Remove uma carteira do sistema. A conta só pode ser encerrada se o saldo estiver zerado."
    )
    public ResponseEntity<Void> deleteWallet(@PathVariable("walletId") UUID walletId) {

        var deleted = walletService.deleteWallet(walletId);

        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/{walletId}/deposits")
    @Operation(
            summary = "Realizar um depósito",
            description = "Adiciona fundos a uma carteira específica. O endereço IP do depositante é capturado para fins de auditoria."
    )
    public ResponseEntity<Void> depositMoney(@PathVariable("walletId") UUID walletId,
                                             @RequestBody @Valid DepositMoneyDto dto, HttpServletRequest servletRequest) {

        walletService.depositMoney(
                walletId,
                dto,
                servletRequest.getAttribute("x-user-ip").toString()
        );
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{walletId}/statements")
    @Operation(
            summary = "Consultar extrato detalhado",
            description = "Gera um extrato paginado contendo o histórico de transferências enviadas, recebidas e depósitos realizados."
    )
    public ResponseEntity<StatementDto> getStatements(@PathVariable("walletId") UUID walletId,
                                                      @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        var statement = walletService.getStatements(walletId, page, pageSize);

        return ResponseEntity.ok(statement);
    }

}
