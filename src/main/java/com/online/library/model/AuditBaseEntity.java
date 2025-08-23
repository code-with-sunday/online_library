package com.online.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AuditBaseEntity {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void onCreate() {
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updateDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditBaseEntity that)) return false;
        return Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, updateDate);
    }
}