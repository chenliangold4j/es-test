package com.gonghui.estest;

import com.gonghui.estest.dao.HouseRepository;
import com.gonghui.estest.entity.HouseIndexTemplate;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class EsTestApplicationTests {

//    @Autowired
//    private HouseRepository houseRepository;
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//
//    @Test
//    public void testCreate() {
//        // 创建索引，会根据Item类的@Document注解信息来创建
//        elasticsearchTemplate.createIndex(HouseIndexTemplate.class);
//        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
//        elasticsearchTemplate.putMapping(HouseIndexTemplate.class);
//    }
//
//    @Test
//    public void saveHouse(){
//        HouseIndexTemplate template = new HouseIndexTemplate();
//        template.setHouseId(1L);
//        template.setTitle("五星级的房间，特色浴室，开放房顶");
//        template.setName("Tom");
//        template.setPrice(800);
//        houseRepository.save(template);
//    }
//
//    @Test
//    public void saveAllHouse(){
//        List<HouseIndexTemplate> list = new ArrayList<>();
//        HouseIndexTemplate template = new HouseIndexTemplate();
//        template.setHouseId(2L);
//        template.setTitle("五星级的房间，免费按摩，入住抽SSR");
//        template.setName("jack");
//        template.setPrice(800);
//        list.add(template);
//        template = new HouseIndexTemplate();
//        template.setHouseId(3L);
//        template.setTitle("五星级的房间，特色鬼屋，无一人生还");
//        template.setName("lili");
//        template.setPrice(-10000);
//        list.add(template);
//
//        template = new HouseIndexTemplate(4l,"五星级的房间，免费按摩，入住送老婆","name",500);
//        list.add(template);
//        template = new HouseIndexTemplate(5L,"五星级的房间，免费按摩，入住送显卡","name2",600);
//        list.add(template);
//        houseRepository.saveAll(list);
//    }
//
//    @Test
//    public void testDelete() {
//        houseRepository.deleteById(4L);
//    }
//
//    @Test
//    public void testQuery(){
//        Optional<HouseIndexTemplate> optional = houseRepository.findById(2L);
//        System.out.println(optional.get());
//    }
//
//    @Test
//    public void testFind(){
//        // 查询全部，并按照价格降序排序
//        Iterable<HouseIndexTemplate> items = houseRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
//        items.forEach(item-> System.out.println(item));
//    }
//
//
//    @Test
//    public void queryByPriceBetween(){
//        List<HouseIndexTemplate> list = houseRepository.findByPriceBetween(400, 700);
//        for (HouseIndexTemplate item : list) {
//            System.out.println("item = " + item);
//        }
//    }
//
//
//    @Test
//    public void testBaseQuery(){
//        // 词条查询
//        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "入住送老婆");
//        // 执行查询
//        Iterable<HouseIndexTemplate> items = houseRepository.search(queryBuilder);
//        items.forEach(System.out::println);
//    }
//    @Test
//    public void testNativeQuery(){
//        // 构建查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 添加基本的分词查询
////        QueryBuilders.match
//        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "开放房顶"));
////        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "房间"));
//        // 执行搜索，获取结果
//        Page<HouseIndexTemplate> items = houseRepository.search(queryBuilder.build());
//        // 打印总条数
//        System.out.println(items.getTotalElements());
//        // 打印总页数
//        System.out.println(items.getTotalPages());
//        items.forEach(System.out::println);
//    }
//
//    @Test
//    public void testNativeQuery2(){
//        // 构建查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 添加基本的分词查询
////        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "房间").boost(10));
//        BoolQueryBuilder should = QueryBuilders.boolQuery().
//                should(QueryBuilders.matchQuery("title", "生还").boost(10)).
//                should(QueryBuilders.matchQuery("title", "老婆").boost(2));
//        queryBuilder.withQuery(should);
//        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
//        // 初始化分页参数
//        int page = 0;
//        int size = 3;
//        // 设置分页参数
//        queryBuilder.withPageable(PageRequest.of(page, size));
//
//        // 执行搜索，获取结果
//        NativeSearchQuery build = queryBuilder.build();
//        Page<HouseIndexTemplate> items = houseRepository.search(build);
//        // 打印总条数
//        System.out.println(items.getTotalElements());
//        // 打印总页数
//        System.out.println(items.getTotalPages());
//        // 每页大小
//        System.out.println(items.getSize());
//        // 当前页
//        System.out.println(items.getNumber());
//        items.forEach(System.out::println);
//    }
//
//    @Test
//    public void testSort(){
//        // 构建查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 添加基本的分词查询
//        queryBuilder.withQuery(QueryBuilders.termQuery("title", "房间"));
//        // 排序
//        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
//
//        // 执行搜索，获取结果
//        Page<HouseIndexTemplate> items = houseRepository.search(queryBuilder.build());
//        // 打印总条数
//        System.out.println(items.getTotalElements());
//        items.forEach(System.out::println);
//    }

}
