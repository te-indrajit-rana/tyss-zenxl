package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.Role;
import com.ty.zenxl.repository.RoleRepository;

@DataJpaTest
class RolePersistTest {

	@Autowired
	private RoleRepository roleRepository;

	Role role1 = Role.builder().roleName("TEST1").build();

	@Test
	@DisplayName("Should Fetch Role Successfully")
	void shouldFetchRoleSuccessfully(){

		Role role = roleRepository.save(role1);
		assertTrue(roleRepository.existsByRoleName(role.getRoleName()));
		assertThat(role.getRoleName()).isEqualTo("TEST1");
	}

	@Test
	@DisplayName("Should Persist Role Successfully")
	void shouldPersistRoleSuccessfully(){

		roleRepository.save(role1);
		assertTrue(roleRepository.existsByRoleName("TEST1"));
	}

	@Test
	@DisplayName("Should Update Role Successfully")
	void shouldUpdateRoleSuccessfully(){

		Role savedRole = roleRepository.save(role1);
		String intialRoleName = savedRole.getRoleName();

		savedRole.setRoleName("TEST123");
		Role updatedRole = roleRepository.save(savedRole);
		String updatedRoleName = updatedRole.getRoleName();
		assertNotEquals(intialRoleName,updatedRoleName);
	}

	@Test
	@DisplayName("Should Delete Role Successfully")
	void shouldDeleteRoleSuccessfully(){

		Role savedRole = roleRepository.save(role1);
		roleRepository.deleteById(role1.getRoleId());
		assertFalse(roleRepository.existsByRoleName(savedRole.getRoleName()));
	}
}
