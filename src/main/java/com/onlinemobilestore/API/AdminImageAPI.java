package com.onlinemobilestore.API;

import com.onlinemobilestore.entity.Image;
import com.onlinemobilestore.entity.Product;
import com.onlinemobilestore.repository.ImageRepository;
import com.onlinemobilestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminImageAPI {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/image")
    public ResponseEntity<Image> handleImageUpload(@RequestParam("image") MultipartFile file, @RequestParam("productId") Integer productId) {
        try {
            // Lấy đường dẫn tuyệt đối đến thư mục resources
            ClassPathResource classPathResource = new ClassPathResource("src/main/resources/images/");
            File uploadPath = new File(classPathResource.getPath());
            // Tạo thư mục nếu nó không tồn tại
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            // Lưu file hình ảnh vào thư mục
            String fileName = file.getOriginalFilename();
            File uploadedFile = new File(uploadPath.getAbsolutePath(), fileName);
            file.transferTo(uploadedFile);
            // Trả về tên tệp hoặc đường dẫn tương đối nếu cần
            //System.out.println(uploadedFile.getAbsolutePath());

            Image newImage = new Image();
            newImage.setImageUrl("http://localhost:8080/admin/image/" + fileName);
            if(productRepository.findById(productId).isPresent()){
                newImage.setProduct(productRepository.findById(productId).get());
            }
            Image result = imageRepository.save(newImage);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ hoặc đăng ký nó nếu cần thiết
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            // Lấy đường dẫn tuyệt đối đến thư mục resources
            ClassPathResource classPathResource = new ClassPathResource("src/main/resources/images/");
            File imageFile = new File(classPathResource.getPath() + fileName);
            // Kiểm tra xem tệp có tồn tại không
            if (imageFile.exists()) {
                // Trả về hình ảnh dưới dạng Resource
                Resource resource = new UrlResource(imageFile.toURI());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=" + fileName)
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentLength(imageFile.length())
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/image/getImage/{idProduct}")
    public ResponseEntity<List<Image>> findImageByProduct(@PathVariable("idProduct") Integer idProduct) {
        List<Image> imageList = imageRepository.findByProductId(idProduct);
        if(!imageList.isEmpty()){
            return new ResponseEntity<>(imageList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/image/deleteImage/{imageId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteImage(@PathVariable("imageId") Integer imageId) {
        try {
            imageRepository.deleteById(imageId);
            return ResponseEntity.ok(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not success");
        }
    }

}
