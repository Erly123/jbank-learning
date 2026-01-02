package ch.l.jbank.service;

import ch.l.jbank.controller.dto.CreateWalletDto;
import ch.l.jbank.controller.dto.DepositMoneyDto;
import ch.l.jbank.entities.Deposit;
import ch.l.jbank.exception.DeleteWalletException;
import ch.l.jbank.exception.WalletDataAlreadyExistsException;
import ch.l.jbank.exception.WalletNotFoundException;
import ch.l.jbank.repository.DepositRepository;
import ch.l.jbank.repository.WalletRepository;
import org.springframework.stereotype.Service;
import ch.l.jbank.entities.Wallet;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final DepositRepository depositRepository;

    public WalletService(WalletRepository walletRepository, DepositRepository depositRepository) {

        this.walletRepository = walletRepository;
        this.depositRepository = depositRepository;
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

    public boolean deleteWallet(UUID walletId) {

        var wallet = walletRepository.findById(walletId);

        if (wallet.isPresent()) {

            if (wallet.get().getBalance().compareTo(BigDecimal.ZERO) != 0) {
                throw new DeleteWalletException(
                        "the balance is not zero. The current amount is $" + wallet.get().getBalance());
            }

            walletRepository.deleteById(walletId);
        }

        return wallet.isPresent();
    }

    @Transactional
    public void depositMoney(UUID walletId,
                             DepositMoneyDto dto,
                             String ipAddress) {

        var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("there is no wallet with this id"));

        var deposit = new Deposit();
        deposit.setWallet(wallet);
        deposit.setDepositValue(dto.value());
        deposit.setDepositDateTime(LocalDateTime.now());
        deposit.setIpAddress(ipAddress);

        depositRepository.save(deposit);

        wallet.setBalance(wallet.getBalance().add(dto.value()));

        walletRepository.save(wallet);


    }
}
