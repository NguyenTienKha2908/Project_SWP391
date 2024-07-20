package com.jewelry.KiraJewelry.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

import io.github.cdimascio.dotenv.Dotenv;
import com.jewelry.KiraJewelry.dto.Image;

@Service
public class ImageService {

    private static final String BUCKET_NAME = "kirajewelry-a2n2k.appspot.com";
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kirajewelry-a2n2k.appspot.com/o/%s?alt=media";

    Dotenv dotenv = Dotenv.configure()
            .directory("src/main/resources")
            .filename("key.env") // explicitly specify the filename
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    // Add to when run deploy
    // private String firebaseURL = dotenv.get("GOOGLE_APPLICATION_CREDENTIALS");
    // Add to when run local
    String firebaseServiceKey = dotenv.get("GOOGLE_APPLICATION_CREDENTIALS");
    String firebaseURL = Paths.get("src", "main", "resources", firebaseServiceKey).toString();
    // Add to when run local


    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private String uploadFileForCustomerProductionOrder(File file, String fileName, String customerId,
            String productionOrderId) throws IOException {
        String folderName = "Customer_Production_Order";
        String filePath = folderName + "/" + customerId + "_" + productionOrderId + "_" + fileName;
        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }

    private String uploadFileForDesignStaff(File file, String fileName, String designStaffId, String productionOrderId)
            throws IOException {
        String folderName = "Customer_Design";
        String subFolderName = designStaffId;
        String filePath = folderName + "/" + subFolderName + "/" + designStaffId + "_" + productionOrderId + "_"
                + fileName;
        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }

    private String uploadFileForProductionStaff(File file, String fileName, String productionStaffId,
            String productionOrderId) throws IOException {
        String folderName = "Customer_Progress_Photo";
        String subFolderName = productionStaffId;
        String filePath = folderName + "/" + subFolderName + "/" + productionStaffId + "_" + productionOrderId + "_"
                + fileName;
        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }

    public String uploadFileForMaterial(File file, String fileName, String materialId) throws IOException {
        String folderName = "Material";
        String filePath = folderName + "/" + materialId + "_" + fileName;
        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }

    private String uploadFileForDiamond(File file, String fileName, String diamondId) throws IOException {
        String folderName = "Diamond";
        String filePath = folderName + "/" + diamondId + "_" + fileName;
        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }

    public String uploadFileForCategory(File file, String fileName, String categoryId) throws IOException {
        String folderName = "Category";
        String filePath = folderName + "/" + categoryId + "_" + fileName;
        BlobId blobId = BlobId.of(BUCKET_NAME, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        }

        return String.format(DOWNLOAD_URL, URLEncoder.encode(filePath, StandardCharsets.UTF_8));
    }

    public List<String> listAllImages(String folderName) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        System.out.println("Firebase URL: " + firebaseURL);

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            Bucket bucket = storage.get(BUCKET_NAME);

            Iterable<Blob> blobs = bucket.list(Storage.BlobListOption.prefix(folderName + "/")).iterateAll();
            for (Blob blob : blobs) {
                if (!blob.isDirectory()) {
                    String fileName = blob.getName();
                    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
                    String imageUrl = String.format(DOWNLOAD_URL, encodedFileName);
                    imageUrls.add(imageUrl);
                }
            }
        }

        return imageUrls;
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

    public List<String> getImgByCustomerID(String customerId, String productionOrderId) throws IOException {
        List<String> listImg = listAllImages("Customer_Production_Order");

        List<String> filteredImages = new ArrayList<>();
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Customer_Production_Order%2F" + customerId + "_" + productionOrderId + "_")) {
                filteredImages.add(imgUrl);
            }
        }

        return filteredImages;
    }

    public List<String> getImgByStaffId(String staffId, String productionOrderId) throws IOException {
        List<String> listImg = listAllImages("Customer_Production_Order");

        List<String> filteredImages = new ArrayList<>();
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Customer_Design%2F" + staffId + "%2F_" + productionOrderId + "_")) {
                filteredImages.add(imgUrl);
            }
        }

        return filteredImages;
    }

    public List<String> getImgByStaffId(String staffId) throws IOException {
        List<String> listImg = listAllImages("Customer_Design/" + staffId);
        System.out.println(listImg);
        List<String> filteredImages = new ArrayList<>();
        for (String imgUrl : listImg) {
            if (imgUrl.contains("/Customer_Design%2F" + staffId + "%2F" + staffId)) {
                filteredImages.add(imgUrl);
                System.out.println(imgUrl);
            }
        }

        return filteredImages;
    }

    public Map<String, List<String>> getImgOrderedByStaffId(String staffId) throws IOException {
        List<String> listImg = listAllImages("Customer_Design/" + staffId);
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

    public Map<String, List<String>> getImgOrderedByPRStaffId(String staffId) throws IOException {
        List<String> listImg = listAllImages("Customer_Progress_Photo/" + staffId);
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
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile, String folderName, String key) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            String url;

            if (folderName.equals("Material")) {
                url = this.uploadFileForMaterial(file, fileName, key);
            } else if (folderName.equals("Diamond")) {
                url = this.uploadFileForDiamond(file, fileName, key);
            } else if (folderName.equals("Category")) {
                url = this.uploadFileForCategory(file, fileName, key);
            } else {
                url = this.uploadFile(file, fileName);
            }

            file.delete();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    public String uploadForProductionOrder(MultipartFile multipartFile, String folderName, String key,
            String productionOrderId) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            File file = this.convertToFile(multipartFile, fileName);
            String url;

            if (folderName.equals("Customer_Production_Order")) {
                url = this.uploadFileForCustomerProductionOrder(file, fileName, key, productionOrderId);
            } else if (folderName.equals("Customer_Design")) {
                url = this.uploadFileForDesignStaff(file, fileName, key, productionOrderId);
            } else if (folderName.equals("Customer_Progress_Photo")) {
                url = this.uploadFileForProductionStaff(file, fileName, key, productionOrderId);
            } else {
                url = this.uploadFile(file, fileName);
            }

            file.delete();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    public boolean deleteImage(String imageUrl) throws IOException {
        String blobName = URLDecoder.decode(
                imageUrl.substring(imageUrl.indexOf("/o/") + 3, imageUrl.indexOf("?alt=media")),
                StandardCharsets.UTF_8.toString());

        try (FileInputStream serviceAccount = new FileInputStream(firebaseURL)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            BlobId blobId = BlobId.of(BUCKET_NAME, blobName);
            return storage.delete(blobId);
        }
    }
}
