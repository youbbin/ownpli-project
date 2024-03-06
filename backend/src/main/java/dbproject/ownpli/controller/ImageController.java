package dbproject.ownpli.controller;

import dbproject.ownpli.controller.dto.image.ImageRequest;
import dbproject.ownpli.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    public final ImageService imageService;

    @PostMapping
    public ResponseEntity<Resource> getImage(@RequestBody ImageRequest request) {

        Resource resource = imageService.openFile(request.getImageUrls());
        String contentType = "image/webp";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_ENCODING, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
