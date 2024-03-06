package dbproject.ownpli.service;

import dbproject.ownpli.exception.OwnPliException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public FileSystemResource openFile(String imageUrl) {
        try {
            String path = "/Users/ahnnayeong/img" + imageUrl;
            System.out.println(path);
            FileSystemResource resource = new FileSystemResource(path);

            if (!resource.exists()) {
                throw new OwnPliException("이미지 파일 없음");
            }

            return resource;
        } catch (Exception e) {
            throw new OwnPliException("이미지 없음");
        }
    }
}
