package com.ty.zenxl.service;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ty.zenxl.entity.Status;
import com.ty.zenxl.exception.StatusException;
import com.ty.zenxl.repository.StatusRepository;
import com.ty.zenxl.request.StatusRequest;
import com.ty.zenxl.request.UpdateStatusRequest;
import com.ty.zenxl.response.StatusCategoryResponse;
import com.ty.zenxl.response.StatusResponse;
import com.ty.zenxl.response.ViewStatusResponse;

import lombok.RequiredArgsConstructor;

/**
 * Defines the mechanisms implemented for CRUD opeartions for a {@code Status}.
 *
 * @author Indrajit
 * @version 1.0
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ZenxlStatusService {

	private final StatusRepository statusRepository;

	public StatusResponse addStatus(StatusRequest request) {

		String statusCategory = request.getStatusCategory().toUpperCase();
		if (Boolean.TRUE.equals(
				statusRepository.existsByStatusNameAndStatusCategory(request.getStatusName(), statusCategory))) {
			throw new StatusException(STATUS_ALEADY_EXISTS_WITH_THE_MENTIONED_STATUS_CATEGORY);
		}

		Status status = Status.builder().statusName(request.getStatusName()).statusCategory(statusCategory)
				.description(request.getDescription()).isActive(true).build();

		Status savedStatus = statusRepository.save(status);
		if (savedStatus.getStatusName() != null) {
			return StatusResponse.builder().statusName(savedStatus.getStatusName())
					.statusCategory(savedStatus.getStatusCategory()).description(savedStatus.getDescription())
					.isActive(savedStatus.getIsActive()).build();
		}
		throw new StatusException(SOMETHING_WENT_WRONG);
	}

	public List<StatusCategoryResponse> findAllStatuses() {

		List<String> listOfCategoryNames = statusRepository.findAll().stream().map(Status::getStatusCategory)
				.distinct().collect(Collectors.toList());

		List<StatusCategoryResponse> listOfStatusCategoryResponses = new ArrayList<>();

		for (String statusCategory : listOfCategoryNames) {

			Optional<List<Status>> findStatusesByStatusCategoryName = statusRepository
					.findAllByStatusCategory(statusCategory);

			if (findStatusesByStatusCategoryName.isPresent()) {

				List<StatusResponse> statusResponses = findStatusesByStatusCategoryName.get().stream()
						.map(status -> StatusResponse.builder().statusName(status.getStatusName())
								.description(status.getDescription()).isActive(status.getIsActive()).build())
						.collect(Collectors.toList());

				listOfStatusCategoryResponses.add(StatusCategoryResponse.builder().statusCategory(statusCategory)
						.statusResponses(statusResponses).build());
			} else {
				listOfStatusCategoryResponses.add(StatusCategoryResponse.builder().statusCategory(statusCategory)
						.statusResponses(new ArrayList<>()).build());
			}
		}
		return listOfStatusCategoryResponses;
	}

	public List<String> findAllStatusCategories() {
		return statusRepository.findAll().stream().map(Status::getStatusCategory).distinct()
				.collect(Collectors.toList());
	}

	public String updateStatus(String statusName, String statusCategory, UpdateStatusRequest request) {

		Status status = statusRepository.findByStatusNameAndStatusCategory(statusName, statusCategory.toUpperCase())
				.orElseThrow(() -> new StatusException(STATUS_NOT_FOUND));
		if ((!(request.getStatusName().equals(status.getStatusName())
				&& request.getStatusCategory().toUpperCase().equals(status.getStatusCategory())))
				&& Boolean.TRUE.equals(statusRepository.existsByStatusNameAndStatusCategory(request.getStatusName(),
						request.getStatusCategory().toUpperCase()))) {
			throw new StatusException(STATUS_ALEADY_EXISTS_WITH_THE_MENTIONED_STATUS_CATEGORY);

		}
		statusRepository.delete(status);
		Status newStatus = Status.builder().statusName(request.getStatusName())
				.statusCategory(request.getStatusCategory().toUpperCase()).description(request.getDescription())
				.isActive(true).build();
		statusRepository.save(newStatus);
		return UPDATED_SUCCESSFULLY;
	}

	public String deleteStatus(String statusName, String statusCategory) {
		Status status = statusRepository.findByStatusNameAndStatusCategory(statusName, statusCategory.toUpperCase())
				.orElseThrow(() -> new StatusException(STATUS_NOT_FOUND_WITH_STATUS_NAME + statusName
						+ "status catagory" + statusCategory.toUpperCase()));

		statusRepository.delete(status);
		return DELETED_SUCCESSFULLY;
	}

	public String setStatus(String statusName, String statusCategory, boolean isActive) {

		Status status = statusRepository.findByStatusNameAndStatusCategory(statusName, statusCategory.toUpperCase())
				.orElseThrow(() -> new StatusException(STATUS_NOT_FOUND_WITH_STATUS_NAME + statusName
						+ "status catagory" + statusCategory.toUpperCase()));

		status.setIsActive(isActive);
		statusRepository.save(status);
		return STATUS_CHANGED_SUCCESSFULLY;
	}

	public ViewStatusResponse viewStatus(String statusName, String statusCategory) {

		Status status = statusRepository.findByStatusNameAndStatusCategory(statusName, statusCategory.toUpperCase())
				.orElseThrow(() -> new StatusException(STATUS_NOT_FOUND));
		return ViewStatusResponse.builder().statusCategory(status.getStatusCategory())
				.statusName(status.getStatusName()).description(status.getDescription()).build();
	}

}
