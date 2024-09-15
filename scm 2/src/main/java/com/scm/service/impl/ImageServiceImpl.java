
package com.scm.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }
    @Override
    public String uploadImage(MultipartFile contactImage,String fileName) {

        // String fileName = UUID.randomUUID().toString();
        
        

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id",fileName));
            
            return this.getUrlFromPublicId(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
            e.printStackTrace();
        }

      return null;
    }
    @Override
    public String getUrlFromPublicId(String publicId) {
       return cloudinary.url().transformation(new Transformation<>().width(300).height(300).crop("fill")).generate(publicId);
    }

    
}