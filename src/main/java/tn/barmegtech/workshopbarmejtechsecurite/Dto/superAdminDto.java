package tn.barmegtech.workshopbarmejtechsecurite.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tn.barmegtech.workshopbarmejtechsecurite.entites.superAdmin;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class superAdminDto extends RegisterRequeste{
	private String Diplome;
	public static superAdmin Toentite(superAdminDto superAdminDto)
	{
		return superAdmin.builder()
				.id(superAdminDto.getId())
				.fullname(superAdminDto.getFullname())
				.email(superAdminDto.getEmail())
				.password(superAdminDto.getPassword())
				.Diplome(superAdminDto.getDiplome())
				.build();
	}
	public static superAdminDto fromentite(superAdmin superAdmin)
	{
		return superAdminDto.builder()
				.id(superAdmin.getId())
				.fullname(superAdmin.getFullname())
				.email(superAdmin.getEmail())
				.password(superAdmin.getPassword())
				.Diplome(superAdmin.getDiplome())
				.build();
	}
	
}
