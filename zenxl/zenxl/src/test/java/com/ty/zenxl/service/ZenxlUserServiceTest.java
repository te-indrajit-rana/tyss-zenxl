package com.ty.zenxl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.Role;
import com.ty.zenxl.entity.User;
import com.ty.zenxl.pojos.ZenxlConstantData;
import com.ty.zenxl.pojos.ZenxlCustomUserDetails;
import com.ty.zenxl.repository.UserRepository;
import com.ty.zenxl.request.SignUpRequest;
import com.ty.zenxl.request.UpdateUserRequest;
import com.ty.zenxl.response.UserResponse;

@ExtendWith(MockitoExtension.class)
class ZenxlUserServiceTest {

	@Mock
	private ZenxlUserService zenxlUserService;

	@MockBean
	private UserRepository userRepository;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	SignUpRequest signUpRequest = SignUpRequest.builder().username("test").email("test@gmail.com").gender("MALE")
			.password("test").role("ROLE_TEST").build();

	UserResponse userResponse = UserResponse.builder().username("test").email("test@gmail.com").build();

	User activeUser = User.builder().username("test").email("test@gmail.com").gender("MALE")
			.password(encoder.encode("test")).active(true).role(Role.builder().roleId(1).roleName("ROLE_TEST").build())
			.build();

	ZenxlCustomUserDetails customUserDetailsForActiveUser = new ZenxlCustomUserDetails(activeUser);

	UserResponse userInfo = UserResponse.builder().username(activeUser.getUsername()).email(activeUser.getEmail())
			.role(activeUser.getRole().getRoleName()).status(activeUser.getActive()).build();

	@Test
	@DisplayName("Should Add User Successfully")
	void shouldAddUserSuccessful() {
		when(zenxlUserService.addUser(any(SignUpRequest.class))).thenReturn(userResponse);
		UserResponse response = zenxlUserService.addUser(signUpRequest);
		assertEquals(userResponse.getUsername(), response.getUsername());
	}

	@Test
	@DisplayName("Should Find All Users Successfully")
	void shouldFindAllUsers() {
		List<UserResponse> list = List.of(userInfo);
		when(zenxlUserService.findAllUsers()).thenReturn(list);
		List<UserResponse> findAllUsers = zenxlUserService.findAllUsers();
		assertNotNull(findAllUsers);
		assertEquals(list.get(0), findAllUsers.get(0));
	}

	@Test
	@DisplayName("Should Update User Successfully")
	void shouldUpdateUserSuccessful() {
		when(zenxlUserService.updateUser(anyInt(), any(UpdateUserRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);
		UpdateUserRequest userBuild = UpdateUserRequest.builder().username(activeUser.getUsername())
				.email(activeUser.getEmail()).role(activeUser.getRole().getRoleName()).build();
		String updateUserMessage = zenxlUserService.updateUser(1, userBuild);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateUserMessage);
	}

	@Test
	@DisplayName("Should Delete User Successfully")
	void shouldDeleteUserSuccessful() {
		when(zenxlUserService.deleteUser(anyInt())).thenReturn(ZenxlConstantData.DELETED_SUCCESSFULLY);
		String deleteUserMsg = zenxlUserService.deleteUser(1);
		assertEquals(ZenxlConstantData.DELETED_SUCCESSFULLY, deleteUserMsg);
	}

	@Test
	@DisplayName("Should Change Status Successfully")
	void shouldChangeUserStatusSuccessful() {

		when(zenxlUserService.setUserStatus(anyInt(), anyBoolean()))
				.thenReturn(ZenxlConstantData.STATUS_CHANGED_SUCCESSFULLY);
		String setUserStatusMessage = zenxlUserService.setUserStatus(1, false);
		assertEquals(ZenxlConstantData.STATUS_CHANGED_SUCCESSFULLY, setUserStatusMessage);
	}

}
