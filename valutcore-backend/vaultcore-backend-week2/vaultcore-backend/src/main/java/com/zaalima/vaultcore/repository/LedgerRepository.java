package com.zaalima.vaultcore.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zaalima.vaultcore.entity.LedgerEntry;

@Repository
public interface LedgerRepository extends JpaRepository<LedgerEntry, UUID> 
{
	
}
