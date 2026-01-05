package ch.l.jbank.service;

import ch.l.jbank.controller.dto.TransferMoneyDto;
import ch.l.jbank.entities.Transfer;
import ch.l.jbank.entities.Wallet;
import ch.l.jbank.exception.TransferException;
import ch.l.jbank.exception.WalletNotFoundException;
import ch.l.jbank.repository.TransferRepository;
import ch.l.jbank.repository.WalletRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class TransferService {
    private final TransferRepository transferRepository;

    private final WalletRepository walletRepository;

    public TransferService(TransferRepository transferRepository, WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void transferMoney(TransferMoneyDto dto) {

        var sender = walletRepository.findById(dto.sender())
                .orElseThrow(() -> new WalletNotFoundException("Sender does not exist"));

        var receiver = walletRepository.findById(dto.receiver())
                .orElseThrow(() -> new WalletNotFoundException("receiver does not exist"));

        if (sender.getBalance().compareTo(dto.value()) == -1) {
            throw new TransferException("inssuficient balance, you current balance is: " + sender.getBalance());
        }

        persistTransfer(dto, receiver, sender);
        updateWallet(dto, sender, receiver);

    }

    private void updateWallet(TransferMoneyDto dto, Wallet sender, Wallet receiver) {
        sender.setBalance(sender.getBalance().subtract(dto.value()));
        receiver.setBalance(receiver.getBalance().add(dto.value()));
        walletRepository.save(sender);
        walletRepository.save(receiver);
    }

    private void persistTransfer(TransferMoneyDto dto, Wallet receiver, Wallet sender) {
        var transfer = new Transfer();
        transfer.setReceiver(receiver);
        transfer.setSender(sender);
        transfer.setTransferValue(dto.value());
        transfer.setTransferDateTime(LocalDateTime.now());
        transferRepository.save(transfer);
    }
}
