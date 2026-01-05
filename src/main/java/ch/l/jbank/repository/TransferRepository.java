package ch.l.jbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.l.jbank.entities.Transfer;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {

}
