package com.gonghui.estest.dao;

import com.gonghui.estest.entity.HouseIndexTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends ElasticsearchRepository<HouseIndexTemplate, Long> {

    List<HouseIndexTemplate> findByPriceBetween(double price1, double price2);
}