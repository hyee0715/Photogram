package com.hy.photogram.domain.subscribe;

import com.hy.photogram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {"from_user_id", "to_user_id"}
                )
        }
)
@Entity
public class Subscribe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User from_user;

    @ManyToOne
    private User to_user;

    private LocalDateTime create_date;

    @PrePersist
    public void createDate() {
        create_date = LocalDateTime.now();
    }
}
