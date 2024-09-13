package tn.barmegtech.workshopbarmejtechsecurite.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tn.barmegtech.workshopbarmejtechsecurite.entites.sousAdmin;
import tn.barmegtech.workshopbarmejtechsecurite.entites.superAdmin;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class sousAdminDto extends RegisterRequeste {
    private String spcialite;
    public static sousAdmin Toentite(sousAdminDto sousAdminDto) {
        return sousAdmin.builder()
                .id(sousAdminDto.getId())
                .fullname(sousAdminDto.getFullname())
                .email(sousAdminDto.getEmail())
                .password(sousAdminDto.getPassword())
                .spcialite(sousAdminDto.getSpcialite())
                .build();
    }


    public static sousAdminDto fromentite(sousAdmin sousAdmin) {
        return sousAdminDto.builder()
                .id(sousAdmin.getId())
                .fullname(sousAdmin.getFullname())
                .email(sousAdmin.getEmail())
                .password(sousAdmin.getPassword())
                .spcialite(sousAdmin.getSpcialite())
                .build();
    }
}