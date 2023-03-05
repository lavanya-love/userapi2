package com.example.userapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.Instant;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="userdetails")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @CreatedDate
    @Column(nullable = false, updatable = false,name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false, updatable = false,name = "last_modified_at")
    private Instant lastModifiedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.lastModifiedAt = Instant.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.lastModifiedAt = Instant.now();
    }

   // @Column(name = "processed_at")
    //private LocalDateTime processedAt;

    @Column(name = "address_first_line")
    private String firstLine;

    @Column(name = "county")
    private String county;

    @Column(name = "postcode")
    private String postcode;

}
