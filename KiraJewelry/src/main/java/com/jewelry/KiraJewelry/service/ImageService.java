package com.jewelry.KiraJewelry.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.jewelry.KiraJewelry.dto.Image;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

@Service
public class ImageService {

    private static final String BUCKET_NAME = "kirajewelry-a2n2k.appspot.com";
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";

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

    private String uploadFileForCustomerProductionOrder(File file, String fileName, String customerId,
            String ProductionOrderId) throws IOException {
        String folderName = "Customer_Production_Order";
        String filePath = folderName + "/" + customerId + "_" + ProductionOrderId + "_" + fileName;
        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", filePath); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        String url = String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
        return url;
    }

    private String uploadFileForDesignStaff(File file, String fileName, String design_staff_id,
            String ProductionOrderId) throws IOException {
        String folderName = "Customer_Design";
        String subFolderName = String.valueOf(design_staff_id);
        String filePath = folderName + "/" + subFolderName + "/" + design_staff_id + "_" + ProductionOrderId + "_"
                + fileName;
        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", filePath); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        String url = String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
        return url;
    }

    private String uploadFileForProductionStaff(File file, String fileName, String production_staff_id,
            String ProductionOrderId) throws IOException {
        String folderName = "Customer_Progress_Photo";
        String subFolderName = String.valueOf(production_staff_id);
        String filePath = folderName + "/" + subFolderName + "/" + production_staff_id + "_" + ProductionOrderId + "_"
                + fileName;
        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", filePath); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        String url = String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
        return url;
    }

    private Image getImageByCustomerId(String savedUrl) {
        String key = savedUrl.substring(0, 6);
        String imageUrl = savedUrl.substring(6);
        return new Image(key, imageUrl);
    }

    private Image getImageByMaterialOrDiamondId(String savedUrl) {
        String key = savedUrl.substring(0, 1);
        String imageUrl = savedUrl.substring(1);
        return new Image(key, imageUrl);
    }

    public String uploadFileForMaterial(File file, String fileName, String materialId) throws IOException {
        String folderName = "Material";

        String filePath = folderName + "/" + materialId + "_" + fileName;

        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", filePath); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        String url = String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
        System.out.println(url);
        return url;
    }

    private String uploadFileForDiamond(File file, String fileName, String diamondId) throws IOException {
        String folderName = "Diamond";
        String filePath = folderName + "/" + diamondId + "_" + fileName;
        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", filePath); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        String url = String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
        return url;
    }

    public String uploadFileForCategory(File file, String fileName, String categoryId) throws IOException {
        String folderName = "Category";

        String filePath = folderName + "/" + categoryId + "_" + fileName;

        BlobId blobId = BlobId.of("kirajewelry-a2n2k.appspot.com", filePath); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";
        String url = String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
        System.out.println(url);
        return url;
    }

    public List<String> listAllImages(String FOLDER_NAME) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Bucket bucket = storage.get(BUCKET_NAME);

        // List all blobs in the specified folder
        Iterable<Blob> blobs = bucket.list(Storage.BlobListOption.prefix(FOLDER_NAME + "/")).iterateAll();
        for (Blob blob : blobs) {
            if (!blob.isDirectory()) {
                String fileName = blob.getName();
                String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
                String imageUrl = String.format(DOWNLOAD_URL, encodedFileName);
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }

    public List<String> getImgByCustomerID(String customerId, String production_order_id) throws IOException {
        List<String> listImg = listAllImages("Customer_Production_Order");

        List<String> filteredImages = new ArrayList<>();
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Customer_Production_Order%2F" + customerId + "_" + production_order_id + "_")) {
                filteredImages.add(imgUrl);
            }
        }

        return filteredImages;
    }

    public List<String> getImgByStaffId(String staff_Id, String production_order_id) throws IOException {
        List<String> listImg = listAllImages("Customer_Production_Order");

        List<String> filteredImages = new ArrayList<>();
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Customer_Design%2F" + staff_Id + "%2F_" + production_order_id + "_")) {
                filteredImages.add(imgUrl);
            }
        }

        return filteredImages;
    }

    public List<String> getImgByStaffId(String staff_Id) throws IOException {
        List<String> listImg = listAllImages("Customer_Design/" + staff_Id);
        System.out.println(listImg);
        List<String> filteredImages = new ArrayList<>();
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Customer_Design%2F" + staff_Id + "%2F" + staff_Id)) {
                filteredImages.add(imgUrl);
                System.out.println(imgUrl);
            }
        }

        return filteredImages;
    }

    public Map<String, List<String>> getImgOrderedByStaffId(String staff_Id) throws IOException {
        List<String> listImg = listAllImages("Customer_Design/" + staff_Id);
        Map<String, List<String>> imagesByOrderId = new HashMap<>();

        for (String imgUrl : listImg) {
            String[] parts = imgUrl.split("_");
            if (parts.length > 1) {
                String orderId = parts[2];
                imagesByOrderId.computeIfAbsent(orderId, k -> new ArrayList<>()).add(imgUrl);
            }
        }

        return imagesByOrderId;
    }

    public Map<String, List<String>> getImgOrderedByPRStaffId(String staff_Id) throws IOException {
        List<String> listImg = listAllImages("Customer_Progress_Photo/" + staff_Id);
        Map<String, List<String>> imagesByOrderId = new HashMap<>();

        for (String imgUrl : listImg) {
            String[] parts = imgUrl.split("_");
            if (parts.length > 1) {
                String orderId = parts[3];
                imagesByOrderId.computeIfAbsent(orderId, k -> new ArrayList<>()).add(imgUrl);
            }
        }

        return imagesByOrderId;
    }

    public String getImgByMaterialID(String materialId) throws IOException {
        List<String> listImg = listAllImages("Material");

        String filteredImages = null;
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Material%2F" + materialId + "_")) {
                filteredImages = imgUrl;
                break;
            }
        }

        return filteredImages;
    }

    public String getImgByCateogryID(String categoryId) throws IOException {
        List<String> listImg = listAllImages("Category");

        String filteredImages = null;
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Category%2F" + categoryId + "_")) {
                filteredImages = imgUrl;
                break;
            }
        }

        return filteredImages;
    }

    public String getImgByDiamondID(String diamondId) throws IOException {
        List<String> listImg = listAllImages("Diamond");

        String filteredImages = null;
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Diamond%2F" + diamondId + "_")) {
                filteredImages = imgUrl;
                break;
            }
        }

        return filteredImages;
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

    public String upload(MultipartFile multipartFile, String FOLDER_NAME, String key) {
        try {
            String fileName = multipartFile.getOriginalFilename(); // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName)); // to generated random string
                                                                                         // values for file name.
            String URL = null;
            File file = this.convertToFile(multipartFile, fileName); // to convert multipartFile to File
            if (FOLDER_NAME.equals("Material")) {
                URL = this.uploadFileForMaterial(file, fileName, key); // to get uploaded file link
            } else if (FOLDER_NAME.equals("Diamond")) {
                URL = this.uploadFileForDiamond(file, fileName, key); // to get uploaded file link
            } else if (FOLDER_NAME.equals("Category")) {
                URL = this.uploadFileForCategory(file, fileName, key); // to get uploaded file link
            } else {
                URL = this.uploadFile(file, fileName); // to get uploaded file link
            }

            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    public String uploadForProductionOrder(MultipartFile multipartFile, String FOLDER_NAME, String key,
            String production_order_id) {
        try {
            String fileName = multipartFile.getOriginalFilename(); // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName)); // to generated random string
                                                                                         // values for file name.
            String URL = null;
            File file = this.convertToFile(multipartFile, fileName); // to convert multipartFile to File
            if (FOLDER_NAME.equals("Customer_Production_Order")) {
                URL = this.uploadFileForCustomerProductionOrder(file, fileName, key, production_order_id); // to get
                                                                                                           // uploaded
                                                                                                           // file link
            } else if (FOLDER_NAME.equals("Customer_Design")) {
                URL = this.uploadFileForDesignStaff(file, fileName, key, production_order_id); // to get
                                                                                               // uploaded
                                                                                               // file link
            } else if (FOLDER_NAME.equals("Customer_Progress_Photo")) {
                URL = this.uploadFileForProductionStaff(file, fileName, key, production_order_id); // to get
                                                                                               // uploaded
                                                                                               // file link
            } else {
                URL = this.uploadFile(file, fileName); // to get uploaded file link
            }

            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    public boolean deleteImage(String imageUrl) throws IOException {
        String bucketName = "kirajewelry-a2n2k.appspot.com";
        String blobName = URLDecoder.decode(
                imageUrl.substring(imageUrl.indexOf("/o/") + 3, imageUrl.indexOf("?alt=media")),
                StandardCharsets.UTF_8.toString());

        InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        BlobId blobId = BlobId.of(bucketName, blobName);
        return storage.delete(blobId);
    }

}
