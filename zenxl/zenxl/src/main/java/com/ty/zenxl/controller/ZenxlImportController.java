package com.ty.zenxl.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ty.zenxl.request.BillOfEntryRequest;
import com.ty.zenxl.request.DutySummaryRequest;
import com.ty.zenxl.request.ImportInvoiceRequest;
import com.ty.zenxl.request.ImportInvoiceUpdateRequest;
import com.ty.zenxl.request.ShipmentRequest;
import com.ty.zenxl.request.ShipmentUpdateRequest;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlImportService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * Zenxl import controller for invoice, shipment item and bill of entry.
 * 
 * 
 * 
 * @author Abhishek
 * @author Indrajit
 * @author Swathi
 * @version 1.0
 */

@RestController
@RequestMapping("api/v1/zenxl/import")
@RequiredArgsConstructor
@SecurityRequirement(name = "zenxl-api")
public class ZenxlImportController {

	private final ZenxlImportService importService;

	@PostMapping("/add-invoice")
	public ResponseEntity<ZenxlResponseBody> addInvoice(@Valid @RequestBody ImportInvoiceRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(importService.addInvoice(request));
	}

	@PostMapping(value = "/upload-import-document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ZenxlResponseBody> uploadImportDocument(@RequestPart MultipartFile file,
			@RequestHeader String data) throws IOException {
		return ResponseEntity.status(HttpStatus.CREATED).body(importService.uploadDocument(file, data));
	}

	@GetMapping("/view-invoice")
	public ResponseEntity<ZenxlResponseBody> viewInvoice(@RequestHeader Long invoiceNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.viewInvoice(invoiceNumber));
	}

	@GetMapping("/view-invoice-list")
	public ResponseEntity<ZenxlResponseBody> viewAllInvoice() {
		return ResponseEntity.status(HttpStatus.OK).body(importService.viewAllInvoices());
	}

	@PutMapping("/update-invoice")
	public ResponseEntity<ZenxlResponseBody> updateInvoice(@RequestHeader Long invoiceNumber,
			@Valid @RequestBody ImportInvoiceUpdateRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.updateInvoice(invoiceNumber, request));
	}

	@DeleteMapping("/delete-invoice")
	public ResponseEntity<ZenxlResponseBody> deleteInvoice(@RequestHeader Long invoiceNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.deleteInvoice(invoiceNumber));
	}

	@PutMapping("/update-invoice-remark")
	public ResponseEntity<ZenxlResponseBody> updateInvoiceRemark(@RequestHeader Long invoiceNumber,
			@RequestHeader String remark) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.updateInvoiceRemark(invoiceNumber, remark));
	}

	@PostMapping("/add-shipment-item")
	public ResponseEntity<ZenxlResponseBody> addShipmentItem(@RequestHeader Long importInvoiceNumber,
			@Valid @RequestBody ShipmentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(importService.addShipmentItem(importInvoiceNumber, request));
	}

	@GetMapping("/view-shipment-item")
	public ResponseEntity<ZenxlResponseBody> viewShipmentItem(@RequestHeader Long internalOrderNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.viewShipmentItem(internalOrderNumber));
	}

	@GetMapping("/view-shipment-item-list")
	public ResponseEntity<ZenxlResponseBody> viewAllShipmentItems() {
		return ResponseEntity.status(HttpStatus.OK).body(importService.viewAllShipmentItems());
	}

	@PutMapping("/update-shipment-item")
	public ResponseEntity<ZenxlResponseBody> updateShipmentItem(@RequestHeader Long internalOrderNumber,
			@Valid @RequestBody ShipmentUpdateRequest request) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(importService.updateShipmentItem(internalOrderNumber, request));
	}

	@DeleteMapping("/delete-shipment-item")
	public ResponseEntity<ZenxlResponseBody> deleteShipmentItem(@RequestHeader Long internalOrderNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.deleteShipmentItem(internalOrderNumber));
	}

	@PostMapping("/add-bill-of-entry")
	public ResponseEntity<ZenxlResponseBody> addBillOfEntry(@RequestHeader Long importInvoiceNumber,
			@Valid @RequestBody BillOfEntryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(importService.addBillOfEntry(importInvoiceNumber, request));
	}

	@GetMapping("/view-bill-of-entry")
	public ResponseEntity<ZenxlResponseBody> viewBillOfEntry(@RequestHeader Long importInvoiceNumber) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.viewBillOfEntry(importInvoiceNumber));
	}

	@GetMapping("/view-bill-of-entry-list")
	public ResponseEntity<ZenxlResponseBody> viewAllBillOfEntry() {
		return ResponseEntity.status(HttpStatus.OK).body(importService.viewAllBillOfEntry());
	}

	@PutMapping("/update-bill-of-entry")
	public ResponseEntity<ZenxlResponseBody> updateBillOfEntry(@RequestHeader Integer billOfEntryId,
			@Valid @RequestBody DutySummaryRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.updateBillOfEntry(billOfEntryId, request));
	}

	@DeleteMapping("/delete-bill-of-entry")
	public ResponseEntity<ZenxlResponseBody> deleteBillOfEntry(@RequestHeader Integer billOfEntryId) {
		return ResponseEntity.status(HttpStatus.OK).body(importService.deleteBillOfEntry(billOfEntryId));
	}

}
