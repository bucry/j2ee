package com.framework.elasticsearch;

import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * MyTest.java
 * </p>
 * <p>
 * </p>
 * 
 * @author $Author: Geloin $
 * @version $Revision: V5.1 $
 */
public class PaodingTest {

    private static Node node;

    @BeforeClass
    public static void beforeClass() {
        node = NodeBuilder.nodeBuilder().node();
    }

    /**
     * 
     * <p>
     * 创建索引
     * </p>
     * @author Geloin
     * Created [2012-12-29 下午5:38:22]
     * @throws Exception
     */
    @Test
    public void createIndex() throws Exception {

        Client client = node.client();
        try {

            try {
                // 预定义一个索引
                client.admin().indices().prepareCreate("app").execute().actionGet();
                
                // 定义索引字段属性
                XContentBuilder mapping = XContentFactory.jsonBuilder().startObject();
                mapping = mapping.startObject("title")
                                // 创建索引时使用paoding解析
                                .field("indexAnalyzer", "paoding")
                                // 搜索时使用paoding解析
                                .field("searchAnalyzer", "paoding")
                                .field("store", "yes")
                          .endObject();
                mapping = mapping.endObject();

                PutMappingRequest mappingRequest = Requests.putMappingRequest("app").type("article").source(mapping);
                client.admin().indices().putMapping(mappingRequest).actionGet();
            }
            catch (IndexAlreadyExistsException e) {
                System.out.println("索引库已存在");
            }

            // 生成文档
            XContentBuilder doc = XContentFactory.jsonBuilder().startObject();
            doc = doc.field("title", "java附魔大师");
            doc = doc.endObject();

            // 创建索引
            IndexResponse response = client.prepareIndex("app", "article", "1").setSource(doc).execute().actionGet();

            System.out.println(response.getId() + "====" + response.getIndex() + "====" + response.getType());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            client.close();
        }
    }

    /**
     * 
     * <p>
     * 查询
     * </p>
     * @author Geloin
     * Created [2012-12-29 下午5:40:55]
     * @throws Exception
     */
    @Test
    public void search() throws Exception {
        Client client = node.client();
        try {
            QueryBuilder qb = QueryBuilders.termQuery("title", "大师");
            SearchResponse scrollResp = client.prepareSearch("app").setSearchType(SearchType.SCAN).setScroll(
                    new TimeValue(60000)).setQuery(qb).setSize(100).execute().actionGet();

            while (true) {
                scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
                for (SearchHit hit : scrollResp.getHits()) {
                    Map<String, Object> source = hit.getSource();
                    if (!source.isEmpty()) {
                        for (Iterator<Map.Entry<String, Object>> it = source.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<String, Object> entry = it.next();
                            System.out.println(entry.getKey() + "=======" + entry.getValue());

                        }
                    }

                }
                if (scrollResp.getHits().hits().length == 0) {
                    break;
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            client.close();
        }

    }
}