package com.leyou.service;

import org.springframework.web.multipart.MultipartFile; /**
 * @author XuHao
 * @Title: IUploadService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/414:34
 */
public interface IUploadService {
    String upload(MultipartFile file);
}
