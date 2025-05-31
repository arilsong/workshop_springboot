package com.arilsongomes.workshop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRespository extends JpaRepository<Item, UUID> {
}
