package com.softserve.if072.restservice.repository;

import com.softserve.if072.common.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vitaliy Malisevych
 */
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findAll();

}
