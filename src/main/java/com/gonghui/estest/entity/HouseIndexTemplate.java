package com.gonghui.estest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * Spring Data通过注解来声明字段的映射属性，有下面的三个注解：
 *
 * @Document 作用在类，标记实体类为文档对象，一般有四个属性
 * indexName：对应索引库名称
 * type：对应在索引库中的类型
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 * @Id 作用在成员变量，标记一个字段作为id主键
 * @Field 作用在成员变量，标记为文档的字段，并指定字段映射属性：
 * type：字段类型，取值是枚举：FieldType
 * index：是否索引，布尔类型，默认是true
 * store：是否存储，布尔类型，默认是false
 * analyzer：分词器名称：ik_max_word
 */
@Document(indexName = "houseindex", type = "house")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseIndexTemplate {

    @Id
    private Long houseId;

    // 使用分词器
    /**
     * 1、ik_max_word (常用)
     * ​	会将文本做最细粒度的拆分
     * 2、ik_smart
     * ​	会做最粗粒度的拆分
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Integer)
    private int price;


}