package com.jewelry.KiraJewelry.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class ImageService {

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json"); 
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename(); // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName)); // to generated random string
                                                                                         // values for file name.

            File file = this.convertToFile(multipartFile, fileName); // to convert multipartFile to File
            String URL = this.uploadFile(file, fileName); // to get uploaded file link
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

}

/********************* 888 */
// package com.jewelry.KiraJewelry.service;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.net.URLEncoder;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.google.cloud.storage.Blob;
// import com.google.api.gax.paging.Page;
// import com.google.auth.Credentials;
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.cloud.storage.BlobId;
// import com.google.cloud.storage.BlobInfo;
// import com.google.cloud.storage.Storage;
// import com.google.cloud.storage.StorageOptions;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;

// @Service
// public class ImageService {

// private final String BUCKET_NAME = "demofirebase-b958b.appspot.com";
// private final String DOWNLOAD_URL =
// "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME
// + "/o/%s?alt=media";
// String folderName = "ProductionOrderList/";

// private String uploadFile(File file, String fileName) throws IOException {
// String folderName = "ProductionOrderList/";
// BlobId blobId = BlobId.of(BUCKET_NAME, folderName + fileName);
// BlobInfo blobInfo =
// BlobInfo.newBuilder(blobId).setContentType("image/png").build();

// InputStream inputStream =
// ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
// Credentials credentials = GoogleCredentials.fromStream(inputStream);
// Storage storage =
// StorageOptions.newBuilder().setCredentials(credentials).build().getService();

// storage.create(blobInfo, Files.readAllBytes(file.toPath()));

// return String.format(DOWNLOAD_URL, URLEncoder.encode(folderName + fileName,
// StandardCharsets.UTF_8));
// }

/**************************** 88 */
// private String uploadFile(File file, String fileName, String
// productionOrderId) throws IOException {

// String fullFileName = productionOrderId + "_" + fileName; // Include
// ProductionOrderId in file name
// BlobId blobId = BlobId.of(BUCKET_NAME, folderName + fullFileName);
// BlobInfo blobInfo =
// BlobInfo.newBuilder(blobId).setContentType("image/png").build();

// InputStream inputStream =
// ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
// Credentials credentials = GoogleCredentials.fromStream(inputStream);
// Storage storage =
// StorageOptions.newBuilder().setCredentials(credentials).build().getService();

// storage.create(blobInfo, Files.readAllBytes(file.toPath()));

// return String.format(DOWNLOAD_URL, URLEncoder.encode(folderName +
// fullFileName, StandardCharsets.UTF_8));
// }

// public List<String> listImages() {
// List<String> imageUrls = new ArrayList<>();
// try {
// System.setProperty("GOOGLE_APPLICATION_CREDENTIALS",
// "/serviceAccountKey.json");
// InputStream inputStream =
// this.getClass().getResourceAsStream("/serviceAccountKey.json");
// FirebaseOptions options = new FirebaseOptions.Builder()
// .setCredentials(GoogleCredentials.fromStream(inputStream))
// .build();
// FirebaseApp.initializeApp(options);

// Initialize Firebase Storage
// Storage storage = StorageOptions.newBuilder()
// .setCredentials(GoogleCredentials.getApplicationDefault())
// .build()
// .getService();

// Iterable<Blob> blobs = storage.list(BUCKET_NAME,
// Storage.BlobListOption.prefix(folderName)).iterateAll();
// for (Blob blob : blobs) {
// imageUrls
// .add(String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
// BUCKET_NAME,
// URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8)));
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
// return imageUrls;
// }

/************************************* */

// private File convertToFile(MultipartFile multipartFile, String fileName)
// throws IOException {
// File tempFile = new File(fileName);
// try (FileOutputStream fos = new FileOutputStream(tempFile)) {
// fos.write(multipartFile.getBytes());
// }
// return tempFile;
// }

// private String getExtension(String fileName) {
// return fileName.substring(fileName.lastIndexOf("."));
// }

// public String upload(MultipartFile multipartFile) {
// try {
// // Generate a unique file name
// String fileName = UUID.randomUUID().toString()
// .concat(this.getExtension(multipartFile.getOriginalFilename()));

// // Convert MultipartFile to File
// File file = this.convertToFile(multipartFile, fileName);

// // Upload file and get URL
// String URL = this.uploadFile(file, fileName);

// // Delete temporary file
// file.delete();

// return URL;
// } catch (Exception e) {
// e.printStackTrace();
// return "Image couldn't upload, Something went wrong";
// }
// }

/************************ 8 */
// public String upload(MultipartFile multipartFile, String productionOrderId) {
// try {
// // Generate a unique file name
// String fileName = UUID.randomUUID().toString()
// .concat(this.getExtension(multipartFile.getOriginalFilename()));

// // Convert MultipartFile to File
// File file = this.convertToFile(multipartFile, fileName);

// // Upload file and get URL
// String URL = this.uploadFile(file, fileName, productionOrderId);

// // Delete temporary file
// file.delete();

// return URL;
// } catch (Exception e) {
// e.printStackTrace();
// return "Image couldn't upload, Something went wrong";
// }
// }

// }
