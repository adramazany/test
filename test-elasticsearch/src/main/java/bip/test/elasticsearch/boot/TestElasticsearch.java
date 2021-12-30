package bip.test.elasticsearch.boot;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * Created by ramezani on 6/13/2018.
 */
public class TestElasticsearch {

    static int progress_counter=100;
    static int progress_linefeed=3000;
    /*
    https://stackoverflow.com/questions/16920902/elasticsearch-java-bulk-batch-size

    To finally answer your question: the nice thing about the BulkProcessor is also that it has sensible defaults: 5 MB of size, 1000 actions, 1 concurrent request, no flush interval (which might be useful to set).
     */
    static int batch_size=1000;

    public static void main(String[] args) throws Exception {
/*
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
*/

//        long ms_start = System.currentTimeMillis();
//
//        Settings settings = Settings.builder()
//                /*.put("cluster.name", "cluster1")*/
//                .put("node.name", "node1")
//                /*.put("client.transport.ignore_cluster_name",true)*/
//                .put("client.transport.sniff", true)
//                .build();
//        Client client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//
//        long ms_connection = (System.currentTimeMillis() - ms_start);
//        long ms_add_before = System.currentTimeMillis();
//
//        DecimalFormat df = new DecimalFormat("000000000");
//        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("twitter", "tweet");
//        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
//
//        for (int i = 1; i <= 100000; i++) {
//            XContentBuilder source = jsonBuilder()
//                    .startObject()
//                    .field("user", "test2" + df.format(i))
//                    .field("postDate", new Date())
//                    .field("message", "trying out Elasticsearch")
//                    .endObject();
//            //System.out.println("length=" + source.string().length());
//
//            long d3 = System.currentTimeMillis();


            /********************** step 1: client.prepareIndex inside loop **********************/
/*
            IndexResponse response = client
                    .prepareIndex("twitter", "tweet",df.format(i))
                    .setSource(source)
                    .get();
*/
            /********************** step 2: client.prepareIndex inside loop & using .setId in loop **********************/
/*
            irb.setId(df.format(i))
                    .setSource(source)
                    .get();
*/
            /********************** step 3: using client.prepareBulk **********************/
//            bulkRequestBuilder.add(
//                    client.prepareIndex("twitter", "tweet",df.format(i)).setSource(source)
//                    );
//
//            if(i%batch_size==0){
//                BulkResponse bulkResponse = bulkRequestBuilder.get();
//                if(bulkResponse.hasFailures()){
//                    System.err.println(bulkResponse.buildFailureMessage());
//                }
//            }
//            /********************** ****** **********************/
//
//            if(i%progress_counter==0){
//                System.out.print(i+",");
//            }
//            if(i%progress_linefeed==0){
//                System.out.println();
//            }
//        }
//        bulkRequestBuilder.get();
//        System.out.println();
//
//        long ms_add_after = System.currentTimeMillis();
//
//        client.close();
//
//        long ms_end = System.currentTimeMillis();
//
//        System.out.println("total duration=" + (ms_end - ms_start)
//                + " ,add duration=" + ((ms_add_after - ms_add_before))
//                + " ,connection duration=" + ((ms_add_before - ms_start) - (ms_end - ms_add_after))
//                + " \n,connect duration=" + (ms_add_before - ms_start)
//                + " ,close duration=" + (ms_end - ms_add_after)
//        );

    }
}
