package ch.l.jbank.service;

import ch.l.jbank.controller.dto.CreateWalletDto;
import ch.l.jbank.exception.WalletDataAlreadyExistsException;
import ch.l.jbank.repository.WalletRepository;
import org.springframework.stereotype.Service;
import ch.l.jbank.entities.Wallet;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {

       var walletDb = walletRepository.findByCpfOrEmail(dto.cpf(),dto.email());

       if ( walletDb.isPresent()) {
           throw new WalletDataAlreadyExistsException("Cpf or email already exists");
       }
       var wallet = new Wallet();
       wallet.setBalance(BigDecimal.ZERO);
       wallet.setName(dto.name());
       wallet.setCpf(dto.cpf());
       wallet.setEmail(dto.email());

       return walletRepository.save(wallet);
    }
}
