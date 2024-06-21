package com.jewelry.KiraJewelry.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	// @Autowired
	// private CategoryService categoryService;

	@GetMapping("/images")
	public String getListFiles(Model model) {
		List<String> imageNames = storageService.listFiles();
		List<String> imageUrls = imageNames.stream()
				.map(name -> "/uploads/" + name) // Assuming '/uploads/' is the path to your images
				.collect(Collectors.toList());
		model.addAttribute("images", imageNames);
		model.addAttribute("imageUrls", imageUrls);
		return "images";
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	// @PostMapping("/images/{filename:.+}")
	// public ResponseEntity<String> deleteImage(@PathVariable String filename) {
	// try {
	// storageService.delete(filename);
	// return ResponseEntity.ok("Deleted the image successfully: " + filename);
	// } catch (Exception e) {
	// logger.error("Failed to delete file", e);
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	// .body("Failed to delete file: " + e.getMessage());
	// }
	// }

	// @GetMapping("/products")
	// public ResponseEntity<List<ProductionOrder>> getAllFlowers() {
	// 	List<ProductionOrder> list = this.productionOrderRepository.findAll();
	// 	return ResponseEntity.status(HttpStatus.OK).body(list);
	// }

	// @GetMapping("/productionOrder/{id}")
	// public ResponseEntity<ProductionOrder> getThisFlower(@PathVariable String id) {

	// 	Optional<ProductionOrder> flower = this.productionOrderRepository.findById(id);

	// 	if (flower.isEmpty())
	// 		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	// 	return ResponseEntity.status(HttpStatus.OK).body(flower.get());
	// }

	// @PostMapping("/productionOrder")
	// public ResponseEntity<ProductionOrder> saveFlower(@RequestBody ProductionOrder productionOrder) {
	// 	this.productionOrderRepository.save(productionOrder);
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(productionOrder);
	// }

	// @PutMapping("/productionOrder/{id}")
	// public ResponseEntity<ProductionOrder> editFlower(@PathVariable String id,
	// 		@RequestBody ProductionOrder productionOrder) {

	// 	ResponseEntity<ProductionOrder> output = getThisFlower(id);
	// 	if (!output.hasBody())
	// 		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

	// 	productionOrder.setProduction_Order_Id(id);
	// 	this.productionOrderRepository.save(productionOrder);
	// 	return ResponseEntity.status(HttpStatus.OK).body(productionOrder);
	// }

	// @DeleteMapping("/productionOrder/{id}")
	// public ResponseEntity<String> deleteFlower(@PathVariable String id) {

	// 	ResponseEntity<ProductionOrder> output = getThisFlower(id);
	// 	if (!output.hasBody())
	// 		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

	// 	this.productionOrderRepository.deleteById(id);
	// 	return ResponseEntity.status(HttpStatus.OK).body("Deleted");
	// }

}