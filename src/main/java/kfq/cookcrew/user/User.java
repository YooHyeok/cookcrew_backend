package kfq.cookcrew.user;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User implements UserDetails{

    @Id
    private String id;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    private String postcode;

    @Column // 상세주소 -> addr_detail
    private String addrDetail;

    @Column
    private String email;

    @Column(columnDefinition = "boolean default TRUE constraint enabled_user check(enabled in(TRUE,FALSE))") // default 'y'
    private Boolean enabled; // 회원 ID 사용 여부

//    @Column
//    private String checked;

    @Column
    private Date regDate;

    @Column
    @Nullable
    private String profilePath;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}