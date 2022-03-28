package com.macro.mall.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.macro.mall.admin.dto.BucketPolicyConfigDto;
import com.macro.mall.admin.dto.MinioUploadDto;
import com.macro.mall.common.api.CommonResult;
import io.minio.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/21 9:29 上午
 * @desc
 */
@Api(tags = "MinioController", description = "MinIO对象存储管理")
@RestController
@RequestMapping("/minio")
public class MinioController {

    private static Logger log = LoggerFactory.getLogger(MinioController.class);

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        try {
            MinioClient minio = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            boolean isExist = minio.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist == false) {
                minio.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(bucketName);
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minio.setBucketPolicy(setBucketPolicyArgs);
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String objectName = sdf.format(new Date()) + "/" + filename;
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName)
                    .object(objectName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE)
                    .build();
            minio.putObject(putObjectArgs);
            log.info("文件上传成功");

            MinioUploadDto uploadDto = new MinioUploadDto();
            uploadDto.setName(filename);
            uploadDto.setUrl(endpoint + "/" + bucketName + "/" +objectName);
            return CommonResult.success(uploadDto);
        }catch (Exception e) {
            e.printStackTrace();
            log.info("上传文件发生错误：", e.getMessage());
        }
        return CommonResult.failed();
    }

    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::"+bucketName+"/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2012-10-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }

    public CommonResult delete(@RequestParam("objectName") String objectName) {
        MinioClient minio = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        try {
            minio.removeObject(removeObjectArgs);
            return CommonResult.success(null);
        }catch (Exception e) {
            log.info("删除对象文件失败");
            return CommonResult.failed(e.getMessage());
        }
    }

}
