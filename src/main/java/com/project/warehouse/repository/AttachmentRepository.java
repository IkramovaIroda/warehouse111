package com.project.warehouse.repository;

import com.project.warehouse.entity.Attachment;
import com.project.warehouse.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
