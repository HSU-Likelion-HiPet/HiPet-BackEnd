package com.hipet.global.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.hipet.global.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

	private final AmazonS3 amazonS3;
	private final AmazonConfig amazonConfig;

	public String uploadFile(MultipartFile file){
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());
		String keyName = generateUuid();

		try {
			PutObjectResult putObjectResult = amazonS3.putObject(
					new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
			log.info("result={}", putObjectResult.getContentMd5());
		}catch (IOException e){
			log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
		}


		return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
	}

	private String generateUuid(){
		return UUID.randomUUID().toString();
	}
}
