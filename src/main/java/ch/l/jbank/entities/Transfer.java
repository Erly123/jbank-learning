package ch.l.jbank.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transfer_id")
    private UUID tranferId;

    @ManyToOne
    @JoinColumn(name = "wallet_receiver_id")
    private Wallet receiver;

    @ManyToOne
    @JoinColumn(name = "wallet_sender_id")
    private Wallet sender;

    @Column(name = "transfer_value")
    private BigDecimal transferValue;

    @Column(name = "transfer_date_time")
    private LocalDateTime transferDateTime;

    public Transfer() {
    }

    public UUID getTranferId() {
        return tranferId;
    }

    public void setTranferId(UUID tranferId) {
        this.tranferId = tranferId;
    }

    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }

    public Wallet getSender() {
        return sender;
    }

    public void setSender(Wallet sender) {
        this.sender = sender;
    }

    public BigDecimal getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(BigDecimal transferValue) {
        this.transferValue = transferValue;
    }

    public LocalDateTime getTransferDateTime() {
        return transferDateTime;
    }

    public void setTransferDateTime(LocalDateTime transferDateTime) {
        this.transferDateTime = transferDateTime;
    }
}
