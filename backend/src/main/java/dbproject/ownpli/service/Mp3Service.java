package dbproject.ownpli.service;

import dbproject.ownpli.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Service
public class Mp3Service {

    private final MusicRepository musicRepository;

    /**
     * 읽어들이는 파일을 컨트롤러를 통해 보내기 위해 적재하는 메소드
     * @param musicId
     * @return LinkedHashMap
     * @throws Exception
     */
    public LinkedHashMap playAudio(String musicId) throws Exception{

        String mp3FileAddress = musicRepository.findMp3FileByMusicId(musicId);

        //D to C
        mp3FileAddress = mp3FileAddress.replaceFirst("D", "C");
        //파일을 파일객체에 넣는다/
        File file = new File(mp3FileAddress);

        //읽은 파일을 아래[fileToString] 메소드를 통해 String으로 변환한다.
        String base64 = fileToString(file);

        //Map형태의 result에 담는다.
        LinkedHashMap result = new LinkedHashMap();
        result.put("audio", base64);

        return result;

    }

    /**
     * 파일 읽는 메소드
     * @param file
     * @return String
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public String fileToString(File file) throws IOException {

        //필요한 객체들을 세팅한다.
        //스트링으로 최종변환한 값을 담는 객체
        String fileString = new String();
        //읽은 파일을 인풋 스트림으로 활용하기 위한 객체
        FileInputStream inputStream =  null;
        //읽은 스트림을 바이트배열로 만들기 위한 객체
        ByteArrayOutputStream byteOutStream = null;

        //파일을 인풋 스트림 객체에 넣는다.
        inputStream = new FileInputStream(file);
        byteOutStream = new ByteArrayOutputStream();
        int len = 0;
        //바이트 배열임시생성
        byte[] buf = new byte[1024];

        //읽어들인 스트림이 False(-1)이 아닐때까지 루프를 돌린다.
        while ((len = inputStream.read(buf)) != -1) {
            //byte배열로 데이터를 입출력하는기 위해 읽어들인다.
            byteOutStream.write(buf, 0, len);

        }

        //바이트배열에 읽은 스트림을 넣는다.
        byte[] fileArray = byteOutStream.toByteArray();


        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoderResult = null;
        //읽어들인 바이트배열을 통신을 위한 base64로 인코딩해서 바이트배열에 넣는다.
        encoderResult = encoder.encode(fileArray);

        //해당 바이트 배열을 스트링으로 변환한다.
        fileString = new String(encoderResult);

        return fileString;

    }
}
