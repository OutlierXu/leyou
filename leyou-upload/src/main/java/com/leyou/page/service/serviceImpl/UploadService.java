package com.leyou.page.service.serviceImpl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.page.controller.UploadController;
import com.leyou.page.service.IUploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author XuHao
 * @Title: UploadService
 * @ProjectName leyou
 * @Description: 上传到图片服务器之后返回图片的地址
 * @date 2018/9/414:36
 */
@Service
public class UploadService implements IUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private static final List<String> CONTENT_TYPE = Arrays.asList("image/png", "image/jpeg");

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String upload(MultipartFile file) {

        try {
            //1.图片校验
            //1.1校验图片格式
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            if(!CONTENT_TYPE.contains(contentType)){

                //图片个格式不正确，返回null
                LOGGER.info("上传失败，文件类型不匹配：{}",contentType);
                return null;
            }

            //1.2校验图片的内容是否为图片（上一步校验不严谨）

            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){

                LOGGER.info("上传失败，文件内容不符合要求");
                return null;
            }

            //2.保存图片
            String suffix = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);

            //3.拼接图片地址
            String url = "http://image.leyou.com/" + storePath.getFullPath();
            LOGGER.info("圖片上传成功，路徑：{}",url);
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
