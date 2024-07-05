package com.jewelry.KiraJewelry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;
import com.jewelry.KiraJewelry.service.FilesStorageService;
import com.jewelry.KiraJewelry.service.ImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ImageController {

	@Autowired
	private ProductionOrderRepository productionOrderRepository;

	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	/*********** */
	@Autowired
	FilesStorageService storageService;

	@Autowired
	ImageService imageService;

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}